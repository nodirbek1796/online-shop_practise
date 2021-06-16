package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.category.CategoryDatabase;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryDatabase,Integer> {
    List<CategoryDatabase> findAllByParentId(Integer parentId);
}
