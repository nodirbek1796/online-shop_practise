package uz.pdp.shop.entity.order_product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderProductState {

    CREATED(1,"buyurtma qabul qilindi"),
    CHECK(2,"buyurtma ko'rib chiqilmoqda"),
    IN_PROCESSING(3,"buyurtma yetkazib berish jarayonida"),
    SUCCESS(4,"buyurtma muvafaqqiyatli yetkazib berildi"),
    CANCEL(5,"buyurtma bekor qilindi");

    private int stateId;
    private String message;


}
