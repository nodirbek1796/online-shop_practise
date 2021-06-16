package uz.pdp.shop.model.receive.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.role.UserRole;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReceiveModel {

    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;
    @JsonProperty("city_id")
    private int cityId;

    private String password;

    @JsonProperty("user_role")
    private UserRole userRole;

    private int age;
}
