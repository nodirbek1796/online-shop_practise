package uz.pdp.shop.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.base.BaseDatabase;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.model.receive.CategoryReceiveModel;
import uz.pdp.shop.model.response.base.BaseAdminResponse;
import uz.pdp.shop.model.response.base.ResponseStatus;
import uz.pdp.shop.model.response.category.CategoryResponseModel;
import uz.pdp.shop.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public BaseAdminResponse addCategory(
            CategoryReceiveModel categoryReceiveModel
    ) {
        try {
            CategoryDatabase categoryDatabase = new CategoryDatabase();
            categoryDatabase.setName(categoryReceiveModel.getName());
            categoryDatabase.setParentId(categoryReceiveModel.getParentId());
            categoryRepository.save(categoryDatabase);
            return ResponseStatus.SUCCESS;
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN_ERROR;
        }
    }

    public List<CategoryResponseModel> getCategoryList() {
        List<CategoryResponseModel> list = new ArrayList<>();
        List<CategoryDatabase> categoryDatabaseList = categoryRepository.findAll();

        categoryDatabaseList
                .forEach((category -> {
                    CategoryResponseModel categoryResponseModel
                            = new CategoryResponseModel(
                            category.getId(),
                            category.getName(),
                            this.getCategoryName(categoryDatabaseList, category.getParentId())
                    );
                    list.add(categoryResponseModel);
                }));

        return list;
    }

    private String getCategoryName(
            List<CategoryDatabase> categoryDatabaseList,
            int parentId
    ) {
        Optional<CategoryDatabase> optionalCategoryDatabase = categoryDatabaseList
                .stream()
                .filter((category) -> {
                    return category.getId() == parentId;
                }).findFirst();

        return optionalCategoryDatabase.map(BaseDatabase::getName).orElse(null);
    }

    public List<CategoryDatabase> getSubCategories(
            Integer categoryId
    ){
        // todo bu joyda ham original holatida xatolik bor ...
        return categoryRepository.findAllByParentId(categoryId);
    }

    public List<CategoryDatabase> getAllSubCategories(){
        List<CategoryDatabase> categoryDatabases = categoryRepository.findAll();

        boolean isSub;
        List<CategoryDatabase> categoryDatabaseList = new ArrayList<>();
        for (int i = 0; i < categoryDatabases.size(); i++) {
            isSub = false;
            for (CategoryDatabase categoryDatabase : categoryDatabases) {
                if (categoryDatabases.get(i).getId() == categoryDatabase.getParentId()) {
                    isSub = true;
                    break;
                }
            }
            if (!isSub)
                categoryDatabaseList.add(categoryDatabases.get(i));
        }
        return categoryDatabaseList;
    }
}
