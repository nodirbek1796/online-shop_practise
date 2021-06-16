package uz.pdp.shop.bot.product;

import com.google.common.io.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;
import uz.pdp.shop.entity.attachment_content.AttachmentContentDatabase;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.repository.CategoryRepository;
import uz.pdp.shop.repository.ProductRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 Created by
 Sahobiddin Abbosaliyev
 6/10/2021
*/
@Service
public class ProductServiceBot {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceBot(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    public InlineKeyboardMarkup getProductList(int categoryId){

        CategoryDatabase categoryDatabase = categoryRepository.findById(categoryId).get();
        List<ProductDatabase> productDatabases = productRepository.findAllByCategoryDatabase(categoryDatabase);

        if (!productDatabases.isEmpty()){
            int index = 0;
            boolean success = true;
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtons;
            List<List<InlineKeyboardButton>> lists = new ArrayList<>();
            while (success) {
                inlineKeyboardButtons = new ArrayList<>();
                for (int i = 0; i < 3 && index <= productDatabases.size() - 1; i++) {
                    inlineKeyboardButtons
                            .add(new InlineKeyboardButton(
                                    productDatabases.get(index).getName())
                                    .setCallbackData("p" + productDatabases.get(index).getId()));
                    index++;
                }
                lists.add(inlineKeyboardButtons);
                if (index == productDatabases.size())
                    success = false;
            }
            inlineKeyboardMarkup.setKeyboard(lists);
            return inlineKeyboardMarkup;
        }
        return null;
    }

    public SendPhoto getProductInfo(int productId) throws IOException {

        SendPhoto sendPhoto = new SendPhoto();
        ProductDatabase productDatabase = productRepository.findById(productId).get();
        List<AttachmentDatabase> attachmentDatabases = productDatabase.getAttachmentDatabases();

        if (attachmentDatabases.isEmpty())
            return null;

        String productAttribute = "Mahsulot nomi: " + productDatabase.getName() + ",\n" +
                                    "Narxi: " + productDatabase.getPrice() + ",\n" +
                                    "Izoh: " + productDatabase.getParams();

        AttachmentContentDatabase attachmentContentDatabase = attachmentDatabases.get(0).getAttachmentContentDatabase();

        InputStream inputStream = ByteSource.wrap(attachmentContentDatabase.getBytes()).openStream();

        sendPhoto.setPhoto(attachmentDatabases.get(0).getName(), inputStream);
        sendPhoto.setCaption(productAttribute);

        InlineKeyboardMarkup inlineKeyboardMarkup = getProductOrder(productId);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        return sendPhoto;
    }

    private InlineKeyboardMarkup getProductOrder(int productId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            inlineKeyboardButtons.add(
                    new InlineKeyboardButton(
                            String.valueOf(i)
                    ).setCallbackData("o_p" + productId + i)
            );
            if (i % 3 == 0){
                lists.add(inlineKeyboardButtons);
                inlineKeyboardButtons = new ArrayList<>();
            }
        }
        inlineKeyboardMarkup.setKeyboard(lists);
        return inlineKeyboardMarkup;
    }
}
