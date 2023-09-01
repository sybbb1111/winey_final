package com.green.winey_final.admin.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductUpdDto {
    private int productId;
    private int categoryId; // 레드 화이트 스파클링 기타 , 적정온도
    private int featureId; // 당도 산도 바디
    private int countryId; // 원산지
    private int aromaId;

    private String nmKor;
    private String nmEng;
    private int price; //가격
    private String pic;
    private int promotion; //
    private int beginner; //입문자 추천
    private int alcohol; //도수
    private int quantity; //재고

    private int sweety; //당도
    private int acidity; //산도
    private int body; //바디

    private ProductAromaParam aroma; // flower, plant fruit spicy earth oak nuts

    private int sale; //할인률
    private int salePrice; //할인가격
    private LocalDate startSale; //할인 시작일
    private LocalDate endSale; //할인 종료일
    private int saleYn; //할인여부

    //음식 페어링
    private int smallCategoryId; //1~12번까지 있음
}
