package uz.pdp.shop.entity.order_product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.order_product_count.OrderProductCountDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.entity.user.UserDatabase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderProductDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserDatabase userDatabase;

    @OneToMany
    private List<OrderProductCountDatabase> orderProductCountDatabase;

    private int stateId;

    private BigDecimal totalSum = BigDecimal.valueOf(0);

    public String getOrderStatus() {
        if (stateId == OrderProductState.CREATED.getStateId())
            return OrderProductState.CREATED.getMessage();
        else if (stateId == OrderProductState.CHECK.getStateId())
            return OrderProductState.CHECK.getMessage();
        else if (stateId == OrderProductState.IN_PROCESSING.getStateId())
            return OrderProductState.IN_PROCESSING.getMessage();
        else if (stateId == OrderProductState.SUCCESS.getStateId())
            return OrderProductState.SUCCESS.getMessage();
        else
            return OrderProductState.CANCEL.getMessage();
    }

//    public BigDecimal getTotalSum(){ //  umumiy summani hisoblayabmiz
//
//        BigDecimal sum = BigDecimal.valueOf(0);
//        productDatabases.forEach((productDatabase)-> sum.add(
//                productDatabase.getPrice()
//                        .multiply(BigDecimal.valueOf(
//                                productDatabase.getOrderCount()))));
//        return sum;
//    }

}
