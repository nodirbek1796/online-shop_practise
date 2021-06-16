package uz.pdp.shop.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;
import uz.pdp.shop.entity.base.BaseDatabase;
import uz.pdp.shop.entity.category.CategoryDatabase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@NamedEntityGraph(
        name = "attachmentDatabases",
        attributeNodes = { @NamedAttributeNode("attachmentDatabases") }
)
public class ProductDatabase extends BaseDatabase {

    @Column(nullable = false)
    private int quantity;       // skladda borligi bu kiritilishi shart
    @Column(nullable = false)
    private BigDecimal price;
    private String params;

//    private int orderCount;  // user sotib oladigan bu optional boladi

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private CategoryDatabase categoryDatabase; // category_database_id bb saqlanadi


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttachmentDatabase> attachmentDatabases;
    // one directional bo'lgani un alohida tablitsa yaratadi


}
