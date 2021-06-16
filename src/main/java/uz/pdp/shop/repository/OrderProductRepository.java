package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.entity.user.UserDatabase;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProductDatabase, Integer> {
    Optional<OrderProductDatabase> findByUserDatabaseAndStateId(UserDatabase userDatabase, int stateId);
}
