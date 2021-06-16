package uz.pdp.shop.model.response.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponseModel {

    @JsonProperty("category_id")
    public int categoryId;
    private String name;
    @JsonProperty("parent_name")
    private String parentName;

}
