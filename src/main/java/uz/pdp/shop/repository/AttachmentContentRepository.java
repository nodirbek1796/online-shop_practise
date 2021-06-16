package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.attachment_content.AttachmentContentDatabase;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContentDatabase,Integer> {
}
