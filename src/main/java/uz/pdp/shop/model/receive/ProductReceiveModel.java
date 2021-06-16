package uz.pdp.shop.model.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductReceiveModel {

    private String name;
    private int quantity;
    private BigDecimal price;
    @JsonProperty("category_id")
    private int categoryId;

    private String params;

}
