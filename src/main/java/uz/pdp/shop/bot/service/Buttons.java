package uz.pdp.shop.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/*
 Created by
 Sahobiddin Abbosaliyev
 6/10/2021
*/
public class Buttons {


    public static ReplyKeyboardMarkup boshMenyu(SendMessage sendMessage) {

        ReplyKeyboardMarkup boshmenyu = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(boshmenyu);
        boshmenyu.setSelective(true);
        boshmenyu.setResizeKeyboard(true);
        boshmenyu.setOneTimeKeyboard(false);

        List<KeyboardRow> boshmenyusi = new ArrayList<>();

        KeyboardRow boshmenyu1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        boshmenyu1.add(new KeyboardButton("Buy product"));
        row2.add(new KeyboardButton("My Orders"));
        row2.add(new KeyboardButton("Cart"));

        boshmenyusi.add(boshmenyu1);
        boshmenyusi.add(row2);

        boshmenyu.setKeyboard(boshmenyusi);
        return boshmenyu;

    }



}
