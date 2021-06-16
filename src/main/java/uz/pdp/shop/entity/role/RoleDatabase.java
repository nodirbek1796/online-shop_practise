package uz.pdp.shop.entity.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.base.BaseDatabase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class RoleDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UserRole userRole;

}
