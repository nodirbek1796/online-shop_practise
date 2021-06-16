package uz.pdp.shop.model.response.base;

import uz.pdp.shop.model.response.base.BaseAdminResponse;

public interface ResponseStatus {

    BaseAdminResponse SUCCESS
            = new BaseAdminResponse("jarayon muvaffaqiyatli bajarildi", 0, true, null);

    BaseAdminResponse UNKNOWN_ERROR
            = new BaseAdminResponse("nomalum xatolik yuz berdi", 111, true, null);

    BaseAdminResponse FILE_UPLOAD_ERROR
            = new BaseAdminResponse("file qoshishda xatolik", 112, true, null);


    BaseAdminResponse CATEGORY_NOT_FOUND
            = new BaseAdminResponse("kategoriya topilmadi", 100, false,null);

    BaseAdminResponse PRODUCT_NOT_FOUND
            = new BaseAdminResponse("mahsulot topilmadi", 101, false,null
    );
}
