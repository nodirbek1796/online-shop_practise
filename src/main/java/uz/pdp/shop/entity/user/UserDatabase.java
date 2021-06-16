package uz.pdp.shop.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.base.BaseDatabase;
import uz.pdp.shop.entity.role.RoleDatabase;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class UserDatabase extends BaseDatabase {

    private String phoneNumber; // it is as username

    private int cityId;

    private String homeStreet;
    private String homeNumber;

    private String password;

    private String email;

    private int age;

    private long chatId;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleDatabase> roles; // bu yerda 3- table yaratdik, va
    // table name user_role, 2 ta field boldi, 1-user_id, 2-role_id

}
