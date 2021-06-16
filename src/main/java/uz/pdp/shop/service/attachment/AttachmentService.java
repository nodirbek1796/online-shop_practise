package uz.pdp.shop.service.attachment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;
import uz.pdp.shop.entity.attachment_content.AttachmentContentDatabase;
import uz.pdp.shop.repository.AttachmentContentRepository;
import uz.pdp.shop.repository.AttachmentRepository;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }


    public AttachmentDatabase saveFile(
            MultipartFile multipartFile
    ){
        try {

            /*attachment contentni saqlash boshlandi*/

            byte[] bytes = multipartFile.getBytes();
            AttachmentContentDatabase attachmentContentDatabase
                    = new AttachmentContentDatabase();
            attachmentContentDatabase.setBytes(bytes);

            AttachmentContentDatabase savedAttachmentContentDatabase
                    = attachmentContentRepository.save(attachmentContentDatabase);

            /*attachment contentni saqlash tugadi*/

            /*attachment  saqlash boshlandi*/
            if (savedAttachmentContentDatabase.getId() == 0)
                return null;

            AttachmentDatabase attachmentDatabase = new AttachmentDatabase();
            attachmentDatabase.setAttachmentContentDatabase(savedAttachmentContentDatabase);
            attachmentDatabase.setSize(multipartFile.getSize());
            attachmentDatabase.setName(multipartFile.getOriginalFilename());
            attachmentDatabase.setFileContentType(multipartFile.getContentType());
            /*attachment saqlash tugadi*/
            return attachmentRepository.save(attachmentDatabase);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
