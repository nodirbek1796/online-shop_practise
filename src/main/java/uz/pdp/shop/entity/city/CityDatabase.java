package uz.pdp.shop.entity.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.address.RegionDatabase;
import uz.pdp.shop.entity.base.BaseDatabase;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CityDatabase extends BaseDatabase {

    @ManyToOne
    private RegionDatabase regionDatabase; // viloyatga bogladik

}
