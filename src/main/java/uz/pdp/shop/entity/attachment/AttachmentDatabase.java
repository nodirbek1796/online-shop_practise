package uz.pdp.shop.entity.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.shop.entity.attachment_content.AttachmentContentDatabase;
import uz.pdp.shop.entity.base.BaseDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AttachmentDatabase extends BaseDatabase {

    @Column(name = "content_type")
    private String fileContentType;
    private Long size;

    @OneToOne
    @JoinColumn(name = "attachment_content_id")
    private AttachmentContentDatabase attachmentContentDatabase;
    // bu asosiy file ogriligini saqlab turadi


}
