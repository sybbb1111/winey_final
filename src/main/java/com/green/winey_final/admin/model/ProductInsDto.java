package com.green.winey_final.admin.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class ProductInsDto {
    private int productId; //와인 PK
    private int categoryId; // categoryPK (레드 1 화이트 2 스파클링 3 기타 4 , 적정온도)
    private int featureId; // featurePK (당도 산도 바디)
    private int countryId; // 원산지PK
    private int aromaId; ///아로마PK

    private String nmKor; //한글이름
    private String nmEng; //영어이름
    private int price; //가격
    private String pic; //사진
    private int promotion; //
    private int beginner; //입문자 추천
    private int alcohol; //도수
    private int quantity; //재고

    private int sweety; //당도
    private int acidity; //산도
    private int body; //바디

    private List<String> aroma; //향 // flower, plant fruit spicy earth oak nuts

    private int sale; //할인률
    private int salePrice; //할인가격
    private LocalDate startSale; //할인 시작일
    private LocalDate endSale; //할인 종료일
    private int saleYn;

    //음식 페어링
    private int smallCategoryId; //1~12번까지 있음
}
