package uz.pdp.shop.mvc.admin.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.model.receive.ProductReceiveModel;
import uz.pdp.shop.model.response.base.BaseAdminResponse;
import uz.pdp.shop.model.response.base.ResponseStatus;
import uz.pdp.shop.model.response.product.ProductResponseModel;
import uz.pdp.shop.repository.ProductRepository;
import uz.pdp.shop.service.attachment.AttachmentService;
import uz.pdp.shop.service.category.CategoryService;
import uz.pdp.shop.service.product.ProductService;

import java.util.List;

@Controller
@RequestMapping("/api/admin/product")
public class ProductAdminController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AttachmentService attachmentService;

    @Autowired
    public ProductAdminController(ProductService productService, ProductRepository productRepository, CategoryService categoryService, AttachmentService attachmentService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.attachmentService = attachmentService;
    }

    @ResponseBody
    @PostMapping(
            value = "/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public BaseAdminResponse addProduct(
            @RequestBody ProductReceiveModel productReceiveModel
    ) {
        BaseAdminResponse baseAdminResponse;
        try {
            baseAdminResponse
                    = productService.addProduct(productReceiveModel);
        } catch (Exception e) {
            baseAdminResponse
                    = ResponseStatus.UNKNOWN_ERROR;
        }

        return baseAdminResponse;
    }

    @GetMapping("/list")
    public String getProduct(
            Model model
    ) {
        // todo bu yerda quyidagi metodga nega null qiymati berilgan ???
        List<CategoryDatabase> categoryDatabaseList = categoryService.getAllSubCategories();
        List<ProductResponseModel> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        model.addAttribute("product", new ProductReceiveModel());
        model.addAttribute("categoryList", categoryDatabaseList);
        return "admin/product/list";
    }

    @ResponseBody
    @PostMapping("/attachment/add/{id}")
    public BaseAdminResponse addAttachment(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable("id") int productId,
            Model model
    ) {
        try {
            AttachmentDatabase attachmentDatabase = attachmentService.saveFile(multipartFile);

            if (attachmentDatabase == null)
                return ResponseStatus.FILE_UPLOAD_ERROR;

            ProductDatabase productDatabase = productRepository.getOne(productId);
            List<AttachmentDatabase> attachmentDatabases = productDatabase.getAttachmentDatabases();
            attachmentDatabases.add(attachmentDatabase);
            productRepository.save(productDatabase); // bu ham save qiladi, ham update qiladi
            return ResponseStatus.SUCCESS;
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN_ERROR;
        }
    }

    @GetMapping("/{id}")
    public String fake(
            @PathVariable("id") int productId,
            Model model
    ) {
        model.addAttribute("productId", productId);
        model.addAttribute("product", new ProductReceiveModel());
        return "admin/product/list";
    }
}
