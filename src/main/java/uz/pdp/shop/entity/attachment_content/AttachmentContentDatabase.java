package uz.pdp.shop.entity.attachment_content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AttachmentContentDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private byte[] bytes;

}
