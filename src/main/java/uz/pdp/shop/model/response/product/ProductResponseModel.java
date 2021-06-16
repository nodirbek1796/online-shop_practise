package uz.pdp.shop.model.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductResponseModel {

    @JsonProperty("product_id")
    private int productId;
    private String name;
    private int quantity;
    private BigDecimal price;
    @JsonProperty("category_name")
    private String categoryName;

    private String params;

}
