package uz.pdp.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import uz.pdp.shop.bot.Main;
import uz.pdp.shop.bot.category.CategoryServiceBot;
import uz.pdp.shop.bot.order.OrderProductServiceBot;
import uz.pdp.shop.bot.order_details.OrderDetailsServiceBot;
import uz.pdp.shop.bot.product.ProductServiceBot;
import uz.pdp.shop.bot.user.UserServiceBot;
import uz.pdp.shop.service.category.CategoryService;
import uz.pdp.shop.service.product.ProductService;

@SpringBootApplication
public class ShopApplication implements WebMvcConfigurer, CommandLineRunner {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryServiceBot categoryServiceBot;

    @Autowired
    ProductServiceBot productServiceBot;

    @Autowired
    OrderProductServiceBot orderProductServiceBot;

    @Autowired
    UserServiceBot userServiceBot;

    @Autowired
    OrderDetailsServiceBot orderDetailsServiceBot;


    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(
                new Main
                        (
                                categoryService,
                                productService,
                                categoryServiceBot,
                                productServiceBot,
                                orderProductServiceBot,
                                userServiceBot,
                                orderDetailsServiceBot
                        )
        );

    }
}