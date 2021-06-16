package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductDatabase,Integer> {
    List<ProductDatabase> findAllByCategoryDatabase(CategoryDatabase categoryDatabase);

}
