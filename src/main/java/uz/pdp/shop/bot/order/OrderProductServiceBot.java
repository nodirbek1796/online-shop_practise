package uz.pdp.shop.bot.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.bot.order_details.OrderDetailsServiceBot;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.entity.order_product.OrderProductState;
import uz.pdp.shop.entity.order_product_count.OrderProductCountDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.OrderProductCountRepository;
import uz.pdp.shop.repository.OrderProductRepository;
import uz.pdp.shop.repository.ProductRepository;
import uz.pdp.shop.repository.UserRepository;
import uz.pdp.shop.service.product.ProductService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderProductServiceBot {

    public final ProductService productService;
    public final OrderProductRepository orderProductRepository;
    public final ProductRepository productRepository;
    public final UserRepository userRepository;
    public final OrderProductCountRepository orderProductCountRepository;
    public final OrderDetailsServiceBot orderDetailsServiceBot;

    @Autowired
    public OrderProductServiceBot
            (
            ProductService productService,
            OrderProductRepository orderProductRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderProductCountRepository orderProductCountRepository,
            OrderDetailsServiceBot orderDetailsServiceBot
            )
    {
        this.productService = productService;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderProductCountRepository = orderProductCountRepository;
        this.orderDetailsServiceBot = orderDetailsServiceBot;
    }

    public String addOrderProduct
            (
                    long chatId,
                    int productId,
                    int orderCount
            )
    {
        ProductDatabase productDatabase = productRepository.findById(productId).get();
        UserDatabase userDatabase = userRepository.findByChatId(chatId).get();

        Optional<OrderProductDatabase>
                optionalOrderProductDatabase = orderProductRepository.findByUserDatabaseAndStateId
                (
                        userDatabase, OrderProductState.CREATED.getStateId()
                );

        if (optionalOrderProductDatabase.isPresent())
        {
            OrderProductDatabase orderProductDatabase = optionalOrderProductDatabase.get();
            boolean hasProductInOrderProductDatabase = hasProductInOrderProductDatabase
                    (
                            orderProductDatabase,
                            productId,
                            orderCount
                    );

            // agarda mazkur mahsulot savatchada bo'lmasa uni yaratib database ga saqlaymiz ...
            if (!hasProductInOrderProductDatabase)
                addNewProductToOrderProductCountDatabase(orderProductDatabase, productDatabase, orderCount);
            orderProductRepository.save(orderProductDatabase);
        }
        else
            createNewOrderProductDatabase(productDatabase, orderCount, userDatabase);
        return orderDetailsServiceBot.cartOrders(userDatabase);
    }

    private boolean hasProductInOrderProductDatabase
            (
                    OrderProductDatabase orderProductDatabase,
                    int productId,
                    int orderCount
            ) {
        List<OrderProductCountDatabase>
                orderProductCountDatabases = orderProductDatabase.getOrderProductCountDatabase();

        AtomicBoolean hasProduct = new AtomicBoolean(false);
        orderProductCountDatabases.forEach(
                orderProductCountDatabase -> {
                    if (orderProductCountDatabase.getProductDatabase().getId() == productId) {
                        hasProduct.set(true);
                        orderProductCountDatabase.setOrderProductCount(
                                orderProductCountDatabase.getOrderProductCount() + orderCount
                        );
                        orderProductCountRepository.save(orderProductCountDatabase);
                    }
                }
        );
        return hasProduct.get();
    }

    private void addNewProductToOrderProductCountDatabase
            (
                    OrderProductDatabase orderProductDatabase,
                    ProductDatabase productDatabase,
                    int orderCount
            ) {
        OrderProductCountDatabase orderProductCountDatabase = new OrderProductCountDatabase();
        orderProductCountDatabase.setProductDatabase(productDatabase);
        orderProductCountDatabase.setOrderProductCount(orderCount);
        orderProductCountRepository.save(orderProductCountDatabase);

        // bu yerda product bizning order listimizda yo'q ekan, shuning un listga yangi product va uning countini qo'shdik...
        orderProductDatabase.getOrderProductCountDatabase().add(orderProductCountDatabase);
    }

    private void createNewOrderProductDatabase
            (
                    ProductDatabase productDatabase,
                    int orderCount,
                    UserDatabase userDatabase
            ) {
        OrderProductCountDatabase orderProductCountDatabase = new OrderProductCountDatabase();
        orderProductCountDatabase.setProductDatabase(productDatabase);
        orderProductCountDatabase.setOrderProductCount(orderCount);
        orderProductCountRepository.save(orderProductCountDatabase);

        OrderProductDatabase orderProductDatabase = new OrderProductDatabase();
        orderProductDatabase.setUserDatabase(userDatabase);
        orderProductDatabase.setOrderProductCountDatabase(Collections.singletonList(orderProductCountDatabase));
        orderProductDatabase.setStateId(OrderProductState.CREATED.getStateId());
        orderProductRepository.save(orderProductDatabase);
    }

    private void updateTotalSum
            (
                    OrderProductDatabase orderProductDatabase,
                    ProductDatabase productDatabase,
                    int orderCount
            )
    {
        orderProductDatabase.setTotalSum
                (
                        orderProductDatabase.getTotalSum()
                                .add(productDatabase.getPrice()
                                        .multiply(BigDecimal.valueOf(orderCount)))
                );
    }
}
