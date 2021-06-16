package uz.pdp.shop.bot.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.repository.CategoryRepository;
import uz.pdp.shop.service.category.CategoryService;

import java.util.ArrayList;
import java.util.List;

/*
 Created by
 Sahobiddin Abbosaliyev
 6/10/2021
*/
@Service
public class CategoryServiceBot {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceBot(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    public InlineKeyboardMarkup getCategoryList( int categoryParentId)
    {
        List<CategoryDatabase> categoryDatabases = categoryRepository.findAllByParentId(categoryParentId);

        int index = 0;
        boolean success = true;

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtons;
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();

        while (success) {
            inlineKeyboardButtons = new ArrayList<>();
            for (int i = 0; i < 3 && index <= categoryDatabases.size() - 1; i++) {
                inlineKeyboardButtons
                        .add(new InlineKeyboardButton(
                                categoryDatabases.get(index).getName())
                                .setCallbackData("c" + categoryDatabases.get(index).getId()));
                index++;
            }
            lists.add(inlineKeyboardButtons);
            if (index == categoryDatabases.size())
                success = false;
        }
        inlineKeyboardMarkup.setKeyboard(lists);
        return inlineKeyboardMarkup;
    }
}
