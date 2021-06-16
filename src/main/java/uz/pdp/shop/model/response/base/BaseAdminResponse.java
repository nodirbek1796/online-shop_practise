package uz.pdp.shop.model.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseAdminResponse {

    private String message;
    private int status;
    private boolean success;

    private Object data;
}
