package uz.pdp.shop.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.shop.bot.base.BaseBotService;
import uz.pdp.shop.bot.category.CategoryServiceBot;
import uz.pdp.shop.bot.order.OrderProductServiceBot;
import uz.pdp.shop.bot.order_details.OrderDetailsServiceBot;
import uz.pdp.shop.bot.product.ProductServiceBot;
import uz.pdp.shop.bot.user.UserServiceBot;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.service.category.CategoryService;
import uz.pdp.shop.service.product.ProductService;

import java.util.ArrayList;
import java.util.List;

public class Main extends TelegramLongPollingBot implements BaseBotService {


    private Long userChatId;
    private String userMessage;

    CategoryService categoryService;
    ProductService productService;
    CategoryServiceBot categoryServiceBot;
    ProductServiceBot productServiceBot;
    OrderProductServiceBot orderProductServiceBot;
    UserServiceBot userServiceBot;
    OrderDetailsServiceBot orderDetailsServiceBot;

    public Main(
            CategoryService categoryService,
            ProductService productService,
            CategoryServiceBot categoryServiceBot,
            ProductServiceBot productServiceBot,
            OrderProductServiceBot orderProductServiceBot,
            UserServiceBot userServiceBot,
            OrderDetailsServiceBot orderDetailsServiceBot
    ){
        this.categoryService = categoryService;
        this.productService = productService;
        this.categoryServiceBot = categoryServiceBot;
        this.productServiceBot = productServiceBot;
        this.orderProductServiceBot = orderProductServiceBot;
        this.userServiceBot = userServiceBot;
        this.orderDetailsServiceBot = orderDetailsServiceBot;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update)
    {
        String inputText = getUserMessage(update);
        userChatId = getUserChatId(update);

        if (update.hasCallbackQuery())
        {
            InlineKeyboardMarkup inlineKeyboardMarkup;
            if (isCategory(inputText))
            {
                List<CategoryDatabase> subCategories =
                        categoryService
                                .getSubCategories(
                                        Integer.valueOf(
                                                inputText.substring(1)
                                        ));
                if (!subCategories.isEmpty()) {
                    inlineKeyboardMarkup = categoryServiceBot
                            .getCategoryList(
                                    Integer.parseInt(
                                            inputText.substring(1)
                                    ));
                    userMessage = "Kerakli kategoryani tanlang!";
                }
                else {
                    inlineKeyboardMarkup = productServiceBot
                            .getProductList(
                                    Integer.parseInt(
                                            inputText.substring(1)
                                    ));
                    userMessage = "Kerakli mahsulotni tanlang!";
                }
                execute(null, inlineKeyboardMarkup);
            }
            else if (isProduct(inputText))
            {
                SendPhoto sendPhoto =
                        productServiceBot
                                .getProductInfo(
                                        Integer.parseInt(inputText.substring(1)
                                        ));
                sendPhoto.setChatId(userChatId);
                execute(sendPhoto);
            }
            else if(isOrderProduct(inputText))
            {
                userMessage = orderProductServiceBot.addOrderProduct(
                        userChatId,
                        Integer.parseInt(inputText.substring(3, inputText.length() - 1)),
                        Integer.parseInt(inputText.substring(inputText.length() - 1))
                );
                execute(null, null);
            }
        }
        else
        {
            switch (inputText) {
                case "/start":
                    userMessage = "Tegishli bo'limni tanlang!";
                    userServiceBot.addUser(userChatId);
                    menu();
                    break;
                case "By product":
                    InlineKeyboardMarkup inlineKeyboardMarkup =
                            categoryServiceBot.getCategoryList(0);
                    userMessage = "Kerakli katalogni tanlang!";
                    execute(null, inlineKeyboardMarkup);
                    break;
            }
        }
    }

    @Override
    public String getBotUsername()
    {
        return "@ulugbek_abdurahimov_bot";
    }

    @Override
    public String getBotToken()
    {
        return "1636811932:AAGaCQd9hilWVu6TBH3Qif2VZmmOJ0BXUB4";
    }

    private void menu()
    {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Main menu");
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add("My cart");
        keyboardRow.add("By product");
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        execute(replyKeyboardMarkup, null);
    }

    private void execute(
            ReplyKeyboardMarkup replyKeyboardMarkup,
            InlineKeyboardMarkup inlineKeyboardMarkup
    )
    {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(this.userChatId);
        sendMessage.setText(this.userMessage);

        if (replyKeyboardMarkup != null)
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        else if (inlineKeyboardMarkup != null)
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Long getUserChatId(Update update)
    {
        if (update.hasMessage())
            return update.getMessage().getChatId();
        return update.getCallbackQuery().getMessage().getChatId();
    }

    private String getUserMessage(Update update)
    {
        if (update.hasMessage())
            return update.getMessage().getText();
        return update.getCallbackQuery().getData();
    }
}
