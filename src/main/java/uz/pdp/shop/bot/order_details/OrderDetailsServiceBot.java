package uz.pdp.shop.bot.order_details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.entity.order_product.OrderProductState;
import uz.pdp.shop.entity.order_product_count.OrderProductCountDatabase;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.OrderProductRepository;
import uz.pdp.shop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderDetailsServiceBot {

    public final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderDetailsServiceBot(ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public String cartOrders(UserDatabase userDatabase){

        Optional<OrderProductDatabase> optionalOrderProductDatabase = orderProductRepository
                .findByUserDatabaseAndStateId(userDatabase, OrderProductState.CREATED.getStateId());
        AtomicReference<String> response = new AtomicReference<>();
        if (optionalOrderProductDatabase.isPresent())
        {
            OrderProductDatabase orderProductDatabase = optionalOrderProductDatabase.get();
            List<OrderProductCountDatabase> orderProductCountDatabases =
                    orderProductDatabase.getOrderProductCountDatabase();

            response.set("Savatchadagi siz tanlagan mahsulotlar:\n\n");
            BigDecimal totalSum = BigDecimal.valueOf(0);
            orderProductCountDatabases.forEach(
                    orderProductCountDatabase -> {
                        response
                                .updateAndGet(v -> v +
                                        getOrderNumber(orderProductCountDatabase.getOrderProductCount()) + " ✖️ " +
                                        orderProductCountDatabase.getProductDatabase().getName() + "\n"
                                );
//                        totalSum
//                                .add(orderProductCountDatabase.getProductDatabase()
//                                .getPrice()
//                                .multiply(BigDecimal.valueOf(orderProductCountDatabase.getOrderProductCount())));
                    }
            );
//            response.updateAndGet(s -> "\nJami xarid miqdori: " + totalSum);
        }
        else
            response.set("Xurmatli xaridor, sizda hozircha tanlangan mahsulotlar yo'q!");
        
        return response.get();
    }

    private String getOrderNumber(int number){
        switch (number){
            case 0:
                return "0️⃣";
            case 1:
                return "1️⃣";
            case 2:
                return "2️⃣";
            case 3:
                return "3️⃣";
            case 4:
                return "4️⃣";
            case 5:
                return "5️⃣";
            case 6:
                return "6️⃣";
            case 7:
                return "7️⃣";
            case 8:
                return "8️⃣";
            case 9:
                return "9️⃣";
            default:
                return null;
        }
    }
}
