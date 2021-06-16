package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<AttachmentDatabase, Integer> {

}
