package uz.pdp.shop.service.product;

import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.model.receive.ProductReceiveModel;
import uz.pdp.shop.model.response.base.BaseAdminResponse;
import uz.pdp.shop.model.response.base.ResponseStatus;
import uz.pdp.shop.model.response.product.ProductResponseModel;
import uz.pdp.shop.repository.CategoryRepository;
import uz.pdp.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public BaseAdminResponse addProduct(
            ProductReceiveModel productReceiveModel
    ) {
        try {
            Optional<CategoryDatabase> optionalCategoryDatabase
                    = categoryRepository.findById(productReceiveModel.getCategoryId());
            if (optionalCategoryDatabase.isEmpty())
                return ResponseStatus.CATEGORY_NOT_FOUND;

            ProductDatabase productDatabase = new ProductDatabase();
            productDatabase.setName(productReceiveModel.getName());
            productDatabase.setQuantity(productReceiveModel.getQuantity());
            productDatabase.setPrice(productReceiveModel.getPrice());
            productDatabase.setCategoryDatabase(optionalCategoryDatabase.get());
            productDatabase.setParams(productReceiveModel.getParams());

            ProductDatabase savedProductDatabase = productRepository.save(productDatabase);
            BaseAdminResponse success = ResponseStatus.SUCCESS;
            success.setData(savedProductDatabase.getId());
            return success;
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN_ERROR;
        }
    }

    public List<ProductResponseModel> getProductList() {
        List<ProductResponseModel> list = new ArrayList<>();
        List<ProductDatabase> productDatabaseList = productRepository.findAll();
        productDatabaseList
                .forEach((product -> {
                    ProductResponseModel productResponseModel
                            = new ProductResponseModel(
                            product.getId(),
                            product.getName(),
                            product.getQuantity(),
                            product.getPrice(),
                            product.getCategoryDatabase().getName(),
                            product.getParams()
                    );
                    list.add(productResponseModel);
                }));
        return list;
    }

    public BaseAdminResponse addAttachment(
            AttachmentDatabase attachmentDatabase,
            int productId
    ){

        if (attachmentDatabase == null)
            return ResponseStatus.UNKNOWN_ERROR;

        Optional<ProductDatabase> optionalProductDatabase
                = productRepository.findById(productId);

        if (optionalProductDatabase.isEmpty())
            return ResponseStatus.PRODUCT_NOT_FOUND;

        ProductDatabase productDatabase = optionalProductDatabase.get();

        List<AttachmentDatabase> attachmentDatabases
                = productDatabase.getAttachmentDatabases();

        attachmentDatabases.add(attachmentDatabase);

        productRepository.save(productDatabase);

        return ResponseStatus.SUCCESS;

    }
}
