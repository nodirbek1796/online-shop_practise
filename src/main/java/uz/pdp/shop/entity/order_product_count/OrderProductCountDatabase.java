package uz.pdp.shop.entity.order_product_count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.product.ProductDatabase;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderProductCountDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private ProductDatabase productDatabase;

    private int orderProductCount;
}
