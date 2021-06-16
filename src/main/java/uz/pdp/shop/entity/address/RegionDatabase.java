package uz.pdp.shop.entity.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.base.BaseDatabase;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Entity
public class RegionDatabase extends BaseDatabase {

}
