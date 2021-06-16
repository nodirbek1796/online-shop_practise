package uz.pdp.shop.bot.base;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/*
 Created by
 Sahobiddin Abbosaliyev
 6/12/2021
*/
public interface BaseBotService {

    default boolean isCategory(String inputText){
        return inputText.startsWith("c");
    }

    default boolean isProduct(String inputText){
        return inputText.startsWith("p");
    }

    default boolean isOrderProduct(String inputText){
        return inputText.startsWith("o_p");
    }

    default ReplyKeyboardMarkup makeReplyMarkup(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        return replyKeyboardMarkup;
    }
}
