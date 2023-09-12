package com.green.winey_final.admin;

import com.green.winey_final.admin.model.*;
import com.green.winey_final.repository.support.PageCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "관리자 페이지")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService SERVICE;

    @Autowired
    public AdminController(AdminService SERVICE) {
        this.SERVICE = SERVICE;
    }

    @Operation(summary = "상품 등록myb", description = "성공시 코드 : 상품PK, 실패시 코드 : 0<br>"
            +"nmKor/nmEng -> String타입<br>"
            +"price/alcohol/quantity -> int타입<br>"
            +"promotion -> 추천상품에 해당할 때 1, 아닐 때 0<br>"
            +"beginner -> 입문자 추천상품일 때 1, 아닐 때 0<br>"
            +"country -> 1(미국), 2(스페인), 3(프랑스), 4(이탈리아), 5(포르투갈), 6(칠레)<br>"
            +"sweety/acidity/body -> 1~5<br>"
            +"category -> 1(레드), 2(화이트), 3(스파클링), 4(기타)<br>"
            +"aroma -> flower(1), plant(2), fruit(3), spicy(4), earth(5), oak(6), nuts(7)<br>"
            +"saleDate -> 2023-08-01(년-월-일), 일자는 01 고정 <br>"
            +"smallCategoryId -> steak(1), chicken(2), 샐러드(salad)(3), pork(4), oyster(5), fish(6), 튀김(fried)(7), 한식(kfood)(8), cheese(9), fruit(10), pizza(11), 디저트(dessert)(12)<br>")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public int postProduct(@RequestPart(required = false) MultipartFile pic, @RequestPart ProductInsParam param) {
        return SERVICE.postProduct(pic, param);
    }

//    @Operation(summary = "상품 등록p", description = "성공시 코드 : 상품PK, 실패시 코드 : 0<br>"
//            +"nmKor/nmEng -> String타입<br>"
//            +"price/alcohol/quantity -> int타입<br>"
//            +"promotion -> 추천상품에 해당할 때 1, 아닐 때 0<br>"
//            +"beginner -> 입문자 추천상품일 때 1, 아닐 때 0<br>"
//            +"country -> 1(미국), 2(스페인), 3(프랑스), 4(이탈리아), 5(포르투갈), 6(칠레)<br>"
//            +"sweety/acidity/body -> 1~5<br>"
//            +"category -> 1(레드), 2(화이트), 3(스파클링), 4(기타)<br>"
//            +"aroma -> flower(1), plant(2), fruit(3), spicy(4), earth(5), oak(6), nuts(7)<br>"
//            +"saleDate -> 2023-08-01(년-월-일), 일자는 01 고정 <br>"
//            +"smallCategoryId -> steak(1), chicken(2), 샐러드(salad)(3), pork(4), oyster(5), fish(6), 튀김(fried)(7), 한식(kfood)(8), cheese(9), fruit(10), pizza(11), 디저트(dessert)(12)<br>")
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public int postProduct(@RequestPart(required = false) MultipartFile pic, @RequestPart ProductInsParam param) {
//        return SERVICE.postProduct2(pic, param);
//    }

    @Operation(summary = "상품 수정myb", description = "성공시 코드 : 상품PK, 실패시 코드 : 0<br>"
            +"nmKor/nmEng -> String타입<br>"
            +"price/alcohol/quantity -> int타입<br>"
            +"promotion -> 추천상품에 해당할 때 1, 아닐 때 0<br>"
            +"beginner -> 입문자 추천상품일 때 1, 아닐 때 0<br>"
            +"country -> 1(미국), 2(스페인), 3(프랑스), 4(이탈리아), 5(포르투갈), 6(칠레)<br>"
            +"sweety/acidity/body -> 1~5<br>"
            +"category -> 1(레드), 2(화이트), 3(스파클링), 4(기타)<br>"
            +"aroma -> flower(1), plant(2), fruit(3), spicy(4), earth(5), oak(6), nuts(7)<br>"
            +"saleDate -> 2023-08-01(년-월-일), 일자는 01 고정 <br>"
            +"smallCategoryId -> steak(1), chicken(2), 샐러드(salad)(3), pork(4), oyster(5), fish(6), 튀김(fried)(7), 한식(kfood)(8), cheese(9), fruit(10), pizza(11), 디저트(dessert)(12)<br>")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public int putProduct(@RequestPart(required = false) MultipartFile pic, @RequestPart ProductUpdParam param) {
        return SERVICE.putProduct(pic, param);
    }


    //jpa
//    @Operation(summary = "상품 수정p", description = "성공시 코드 : 상품PK, 실패시 코드 : 0<br>"
//            +"nmKor/nmEng -> String타입<br>"
//            +"price/alcohol/quantity -> int타입<br>"
//            +"promotion -> 추천상품에 해당할 때 1, 아닐 때 0<br>"
//            +"beginner -> 입문자 추천상품일 때 1, 아닐 때 0<br>"
//            +"country -> 1(미국), 2(스페인), 3(프랑스), 4(이탈리아), 5(포르투갈), 6(칠레)<br>"
//            +"sweety/acidity/body -> 1~5<br>"
//            +"category -> 1(레드), 2(화이트), 3(스파클링), 4(기타)<br>"
//            +"aroma -> flower(1), plant(2), fruit(3), spicy(4), earth(5), oak(6), nuts(7)<br>"
//            +"saleDate -> 2023-08-01(년-월-일), 일자는 01 고정 <br>"
//            +"smallCategoryId -> steak(1), chicken(2), 샐러드(salad)(3), pork(4), oyster(5), fish(6), 튀김(fried)(7), 한식(kfood)(8), cheese(9), fruit(10), pizza(11), 디저트(dessert)(12)<br>")
//    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public int putProduct2(@RequestPart(required = false) MultipartFile pic, @RequestPart ProductUpdParam param) {
//        return SERVICE.putProduct2(pic, param);
//    }
/*
    //등록 상품 리스트 출력 mybatis
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "등록된 상품 리스트 출력+ 등록 상품 검색 myb(피그마: 등록상품리스트 페이지)P", description = "page값 = 1(default), row값 = 20(default)<br>"
            + "default값은 임시로 넣은 것이니 수정이 필요합니다.<br>"
            + "type -> 기본값(0) / 상품번호(productid)/세일가격(saleprice)/할인률(sale)/정상가(price)/추천상품(recommend)/재고수량=품절여부(quantity)<br>"
            + "sort -> 기본값(0) / 오름차순(asc) / 내림차순(desc)<br>"
            + "상품 검색시<br> type2 -> 상품한글이름(searchproductnmkor)<br> str -> (검색어)")
    @GetMapping("/product/list2")
    public ProductList getProduct2(@RequestParam(defaultValue = "1")int page,
                                  @RequestParam(defaultValue = "20")int row,
                                  @RequestParam(required = false) String type,
                                  @RequestParam(required = false) String sort,
                                  @RequestParam(required = false) String type2,
                                  @RequestParam(required = false) String str) {
        SelListDto dto = new SelListDto();
        dto.setRow(row);
        dto.setPage(page);
        dto.setType(type);
        dto.setType2(type2);
        dto.setSort(sort);
        dto.setStr(str);
        return SERVICE.getProduct2(dto);
    }
*/
    //등록 상품 리스트 출력 jpa
    @Operation(summary = "등록된 상품 리스트 출력(피그마: 등록상품리스트 페이지)p", description = "정렬 안한 기본 페이지는 productId,asc 가 기본값입니다.<br>"
            + "page -> 0이 1페이지입니다.<br> size(row) -> 한 페이지 당 보여줄 갯수<br>"
            + "sort ->  입력 예시) productid,asc -> 상품번호 기준으로 오름차순 정렬한다는 의미<br> 정렬기준 ->상품번호(productId)/세일가격(salePrice)/할인률(sale)/정상가(price)/추천상품(recommend)/재고수량=품절여부(quantity)<br>"
            + "정렬 ->  오름차순(asc) / 내림차순(desc)<br>"
            + "str -> 검색어 <br>")
    @GetMapping("/product/list")
    public PageCustom<ProductVo> getProduct(@ParameterObject @PageableDefault(sort="productId", direction = Sort.Direction.ASC, page = 0, size = 20)
                                             Pageable pageable,
                                             @RequestParam(required = false) String str) {
        return SERVICE.getProduct(pageable, str);
    }
/*
    //할인률 등록 상품 리스트 출력 mybatis
    @Operation(summary = "할인률을 입력한 상품 리스트 myb", description = "saleYn = 1은 할인 적용, 0은 미적용<br>"
            +"page (기본값 1), row (기본값 20) 디폴트값 임시로 해놓은거라 수정이 필요합니다.")
    @GetMapping("/product/salelist2")
    public ProductSaleList getProductSale(@RequestParam(defaultValue = "1")int page,
                                          @RequestParam(defaultValue = "20")int row) {
        SelListDto dto = new SelListDto();
        dto.setRow(row);
        dto.setPage(page);
        return SERVICE.getProductSale(dto);
    }
*/
    //할인률 등록 상품 리스트 출력 jpa
    @Operation(summary = "할인률을 입력한 상품 리스트p", description = "saleYn = 1은 할인 적용, 0은 미적용<br>"
            + "page -> 0이 1페이지입니다.<br> size(row) -> 한 페이지 당 보여줄 갯수<br>"
            + "sort ->  입력 예시) productid,asc -> 상품번호 기준으로 오름차순 정렬한다는 의미<br> 정렬기준 -> 상품번호(productId) / 할인여부(saleyn)/ 할인률(salerate)<br>"
            + "정렬 ->  오름차순(asc) / 내림차순(desc)<br>")
    @GetMapping("/product/salelist")
    public PageCustom<ProductSaleVo> getProductSale(@ParameterObject @PageableDefault(sort="productId", direction = Sort.Direction.ASC, page = 0, size = 20)
                                              Pageable pageable) {
        return SERVICE.getProductSale2(pageable);
    }
/*
    //가입 회원 리스트 출력 + 회원 검색
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "가입 회원 리스트 + 회원 검색 myb(피그마: 가입회원리스트 페이지)", description = "page (기본값1), row (기본값15) 임시로 해놓은거라 수정이 필요합니다.<br>"
            + "type -> 기본값(0) / 픽업지역(pickUp) / 회원번호(userId)<br>"
            + "sort -> 기본값(0) / 오름차순(asc) / 내림차순(desc)<br>"
            + "회원 검색시<br> type2 -> 이름(searchUserName) / 이메일(searchUserEmail)<br>"
            + "str -> (검색어) ")
    @GetMapping("/user/list2")
    public UserList getUserList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "15") int row,
                                @RequestParam(required = false) String type,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String type2,
                                @RequestParam(required = false) String str) {
        SelListDto dto = new SelListDto();
        dto.setPage(page);
        dto.setRow(row);
        dto.setType(type);
        dto.setType2(type2);
        dto.setSort(sort);
        dto.setStr(str);
        return SERVICE.getUserList2(dto);
    }
*/
    @Operation(summary = "가입 회원 리스트 (피그마: 가입회원리스트 페이지)p", description = "<br>"
            + "page -> 0이 1페이지입니다.<br> row -> 한 페이지 당 보여줄 갯수<br>"
            + "sort ->  입력 예시) userId,asc <br> - 입력회원번호(userId) / 픽업지역(pickUp) <br> - 오름차순(asc) / 내림차순(desc)<br>"
            + "searchType(검색타입) -> unm(회원이름) / email(이메일) <br>"
            + "str ->  검색어")
    @GetMapping("/user/list")
    public PageCustom<UserVo> getUserList2(@ParameterObject @PageableDefault(sort="userId", direction = Sort.Direction.ASC, page = 0, size = 20)
                                           Pageable pageable,
                                           @RequestParam(required = false) String searchType,
                                           @RequestParam(required = false) String str) {

        return SERVICE.getUserList(pageable, searchType, str);
    }
/*
    //가입 회원별 상세 주문 내역(회원pk별) +페이징 처리 mybatis
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "회원별 상세 주문 내역 myb (피그마: 회원상세내역 페이지)P", description = "page (기본값1), row (기본값15) 임시로 해놓은거라 수정 필요하면 말해주세요.<br>"
            + "구매합산금액(sumOrderPrice) / 구매횟수(orderCount) 추가<br>"
            + "type -> 기본값(0) / 주문날짜(orderDate) / 픽업매장(storeNm) / 주문상태(orderStatus)<br>"
            + "sort -> 기본값(0) / 오름차순(asc) / 내림차순(desc)")
    @GetMapping("/{userId}/order2")
    public UserOrderDetailList2 getUserOrder2(@PathVariable Long userId, @RequestParam(defaultValue = "1")int page,
                                            @RequestParam(defaultValue = "15")int row,
                                            @RequestParam(defaultValue = "0") String type,
                                            @RequestParam(defaultValue = "0") String sort) {
        SelListDto dto = new SelListDto();
        dto.setPage(page);
        dto.setRow(row);
        dto.setType(type);
        dto.setSort(sort);
        return SERVICE.getUserOrder2(userId, dto);
    }
*/
    //가입 회원별 상세 주문 내역(회원pk별) +페이징 처리 jpa
    @Operation(summary = "회원별 상세 주문 내역 (피그마: 회원상세내역 페이지)p", description = "구매합산금액(sumOrderPrice) / 구매횟수(orderCount) 추가<br>"
            + "page -> 0이 1페이지입니다.<br>"
            + "size(row) -> 한 페이지 당 보여줄 갯수<br>"
            + "sort -> 입력 예시) orderDate,asc<br> - 주문번호(orderId) / 주문날짜(orderDate) / 픽업매장(storeNm) / 주문상태(orderStatus)<br>"
            + "- 오름차순(asc) / 내림차순(desc)")
    @GetMapping("/{userId}/order")
    public UserOrderDetailList getUserOrder(@PathVariable Long userId, @ParameterObject @PageableDefault(sort="orderid", direction = Sort.Direction.ASC, page = 0, size = 20)
    Pageable pageable) {
        return SERVICE.getUserOrder(userId, pageable);
    }

    //상품 사진 삭제
    @Operation(summary = "상품 사진 삭제(피그마:상품수정페이지에서 사진 삭제 기능)", description = "상품 수정할 때 기존 등록한 사진 삭제하기 위한 것<br>"
            +"성공시 코드 : 200")
    @DeleteMapping("/product/pic")
    public int deleteProductPic(int productId) {
        return SERVICE.deleteProductPic(productId);
    }

    //주문 내역 mybatis
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "주문 내역 출력 myb(피그마:주문내역관리 페이지)P", description = "<br>"
            +"page (기본값1), row (기본값15) 임시로 해놓은거라 수정 필요하면 말해주세요.<br>"
            +"type -> 기본값(orderdate) / 픽업장소(storeNm) / 픽업배송상태(orderStatus) <br>"
            +"sort ->  기본값(desc) / 오름차순(asc) / 내림차순(desc)")
    @GetMapping("/order2")
    public OrderList getOrder3(@RequestParam(defaultValue = "1")int page,
                              @RequestParam(defaultValue = "15")int size,
                              @RequestParam(defaultValue = "orderdate") String type,
                              @RequestParam(defaultValue = "desc") String sort) {
        SelListDto dto = new SelListDto();
        dto.setPage(page);
        dto.setRow(size);
        dto.setType(type);
        dto.setSort(sort);

        return SERVICE.getOrder3(dto);
    }

    //주문 내역 jpa
    @Operation(summary = "주문 내역 출력(피그마:주문내역관리 페이지)p", description = "<br>"
            + "page -> 0이 1페이지입니다.<br> row -> 한 페이지 당 보여줄 갯수<br>"
            + "sort ->  입력 예시) orderid,desc <br> - 주문날짜(orderdate) / 주문번호(orderid) / 픽업장소(storeNm) / 픽업배송상태(orderStatus) <br> - 오름차순(asc) / 내림차순(desc)<br>")
    @GetMapping("/order")
    public PageCustom<OrderListVo> getOrder(@ParameterObject @PageableDefault(sort="orderdate", direction = Sort.Direction.DESC, page = 0, size = 20)
                                   Pageable pageable) {
        return SERVICE.getOrder(pageable);
    }

/*
    //상세 주문 내역 리스트 by orderId mybatis
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "상세 주문 내역 출력 by orderId myb(피그마:주문상세리스트)")
    @GetMapping("/order3/{orderId}")
    public OrderDetail3 getOrderDetail3(@PathVariable int orderId) {
        return SERVICE.getOrderDetail3(orderId);
    }
*/
    //상세 주문 내역 리스트 by orderId jpa
    @Operation(summary = "상세 주문 내역 출력 by orderId(피그마:주문상세리스트)p")
    @GetMapping("/order/{orderId}")
    public OrderDetail3 getOrderDetail(@PathVariable int orderId, @ParameterObject @PageableDefault(sort="orderid", direction = Sort.Direction.ASC, page = 0, size = 20)
                                        Pageable pageable) {
        return SERVICE.getOrderDetail(orderId, pageable);
    }

/*
    //환불된 상품과 환불 사유 출력 mybatis
    @Operation(summary = "환불 상품 내역과 환불 사유 출력", description = "page (기본값1), row (기본값15) 임시로 해놓은거라 수정 필요하면 말해주세요.<br>"+
            "userId는 입력 안하면 전체 환불 내역과 사유 출력, 입력하면 userId의 환불내역과 사유 출력")
    @GetMapping("/order/refund")
    public List<OrderRefundVo> getOrderRefund(@RequestParam(defaultValue = "1")int page,
                                              @RequestParam(defaultValue = "15")int row,
                                              @RequestParam(defaultValue = "0")Long userId) {
        SelListDto dto = new SelListDto();
        dto.setPage(page);
        dto.setRow(row);

        return SERVICE.getOrderRefund(dto, userId);
    }
*/
    //환불된 상품과 환불 사유 출력 jpa
    @Operation(summary = "환불 상품 내역과 환불 사유 출력p", description = "page (기본값1), row (기본값15) 임시로 해놓은거라 수정 필요하면 말해주세요.<br>"
            + "page -> 0이 1페이지입니다.<br>"
            + "size(row) -> 한 페이지 당 보여줄 갯수<br>"
            + "sort -> 입력 예시) refundid,asc<br> - 환불번호(refundid) / 주문번호(orderId) / 환불여부(refundyn) / 환불날짜(refunddate) <br>"
            + "- 오름차순(asc) / 내림차순(desc)")
    @GetMapping("/order/refund2")
    public PageCustom<OrderRefundVo> getOrderRefund2(@ParameterObject @PageableDefault(sort="refundid", direction = Sort.Direction.ASC, page = 0, size = 20)
                                                   Pageable pageable) {
        return SERVICE.getOrderRefund2(pageable);
    }
/*
    //매장 정보 등록 mybatis
    @Operation(summary = "매장 정보 등록", description = "nm(매장이름)을 기존 등록된 매장이름과 중복된 이름 입력시 등록 안됨<br>"
            +"전화번호 유효성 검사 (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자), 실패시 코드 : 0<br>"
            +"주소 형식 -> ex) 서울 양천구 오목로 299")
    @PostMapping("/store2")
    public Long postStore(@RequestBody StoreInsParam param) {
        return SERVICE.insStore(param);
    }
*/
    //매장 정보 등록 jpa
    @Operation(summary = "매장 정보 등록p", description = "nm(매장이름)을 기존 등록된 매장이름과 중복된 이름 입력시 등록 안됨<br>"
            +"전화번호 유효성 검사 (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자), 실패시 코드 : 0<br>"
            +"주소 형식 -> ex) 서울 양천구 오목로 299")
    @PostMapping("/store")
    public Long postStore2(@RequestBody StoreInsParam param) {

        return SERVICE.insStore2(param);
    }

/*
    //매장 리스트 출력 mybatis
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "매장 리스트 출력 myb", description = "page (기본값1), row (기본값15) 임시로 해놓은거라 수정 필요하면 말해주세요.")
    @GetMapping("/store2")
    public StoreList getStore2(@RequestParam(defaultValue = "1")int page,
                              @RequestParam(defaultValue = "15")int row) {
        SelListDto dto = new SelListDto();
        dto.setPage(page);
        dto.setRow(row);

        return SERVICE.getStore2(dto);
    }
*/
    //매장 리스트 출력 jpa
    @Operation(summary = "매장 리스트 출력P", description = ""
            + "page -> 0이 1페이지입니다.<br> row -> 한 페이지 당 보여줄 갯수<br>"
            + "sort ->  입력 예시) storeid,asc <br> - 매장번호(storeid) / 매장이름(storenm) / 주소(address) / 전화번호(tel) <br> - 오름차순(asc) / 내림차순(desc)<br>"
            + "searchType(검색타입) -> storename(매장명) / storeaddress(매장주소) / storetel(매장전화번호) <br>"
            + "str ->  검색어")
    @GetMapping("/store")
    public PageCustom<StoreVo> getStore(@ParameterObject @PageableDefault(sort="storeid", direction = Sort.Direction.ASC, page = 0, size = 20)
                                         Pageable pageable,
                                         @RequestParam(required = false)String searchType,
                                         @RequestParam(required = false)String str) {
        return SERVICE.getStore(pageable, searchType, str);
    }

    //매장 정보 수정 mybatis
    @Operation(summary = "매장 정보 수정", description = "전화번호 유효성 검사 (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자), 실패시 코드 : 0 ")
    @PutMapping("/store/{storeId}")
    public Long putStore(@RequestBody StoreInsParam param, Long storeId) {
        return SERVICE.updStore(param, storeId);
    }

/*
    //매장 정보 수정 jpa
    @Operation(summary = "매장 정보 수정p", description = "전화번호 유효성 검사 (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자), 실패시 코드 : 0 ")
    @PutMapping("/store/{storeId}")
    public Long putStore2(@RequestBody StoreInsParam param, Long storeId) {
        return SERVICE.updStore2(param, storeId);
    }
*/
/*
    //매장 정보 삭제 mybatis
    @Operation(summary = "매장 정보 삭제", description = "삭제 성공시 코드 : 1, 실패시 코드 : 0")
    @DeleteMapping("/store2/{storeId}")
    public Long deleteStore(Long storeId) {
        return SERVICE.deleteStore(storeId);
    }
*/
    //매장 정보 삭제 jpa
    @Operation(summary = "매장 정보 삭제p", description = "삭제 성공시 코드 : 1, 실패시 코드 : 0")
    @DeleteMapping("/store/{storeId}")
    public Long deleteStore2(Long storeId) {
        return SERVICE.deleteStore2(storeId);
    }

    //주문 상태 업데이트 mybatis(관리자 페이지에서)
    @Operation(summary = "주문 상태 업데이트myb(피그마:주문내역관리 페이지의 배송상태설정 기능)", description = "orderStatus코드 : 1(결제완료), 2(배송중), 3(배송완료), 4(픽업대기), 5(픽업완료), 6(주문취소)<br>"
            +"* orderStatus 코드 유효성 검사 실패시 500 리턴<br>"
            +"* 주문 상태 업데이트 실패시 0 리턴")
    @PutMapping("/order")
    public Long putOrderStatus(@RequestBody OrderStatusDto dto) {
        return SERVICE.updOrderStatus(dto);
    }
/*
    //주문 상태 업데이트 jpa(관리자 페이지에서)
    @Operation(summary = "주문 상태 업데이트p(피그마:주문내역관리 페이지의 배송상태설정 기능)", description = "orderStatus코드 : 1(결제완료), 2(배송중), 3(배송완료), 4(픽업대기), 5(픽업완료), 6(주문취소)<br>"
            +"* orderStatus 코드 유효성 검사 실패시 500 리턴<br>"
            +"* 주문 상태 업데이트 실패시 0 리턴")
    @PutMapping("/order2")
    public Long putOrderStatus2(@RequestBody OrderStatusDto dto) {

        return SERVICE.updOrderStatus2(dto);
    }
*/
    /*
    //할인 상태(saleYn) 업데이트 mybatis (관리자가 수동으로 On/Off하는 용도)
    @Operation(summary = "상품 할인상태(saleYn) 업데이트 (관리자가 수동으로 On/Off하는 용도)", description = "업데이트 <br>"+"* 성공시 코드: 1<br>"+ "* 실패시 코드: 0")
    @PutMapping("/sale2")
    public int putProductSaleYn(ProductSaleYnDto dto) {
        return SERVICE.putProductSaleYn(dto);
    }
*/
    //할인 상태(saleYn) 업데이트 jpa (관리자가 수동으로 On/Off하는 용도)
    @Operation(summary = "상품 할인상태(saleYn) 업데이트p (관리자가 수동으로 On/Off하는 용도)", description = "업데이트 <br>"+"* 성공시 코드: 1<br>"+ "* 실패시 코드: 0")
    @PutMapping("/sale")
    public int putProductSaleYn2(ProductSaleYnDto dto) {
        return SERVICE.putProductSaleYn(dto);
    }

    /*
    //회원 삭제 mybatis
    @Operation(summary = "회원 탈퇴상태(delYn) 업데이트 myb", description = "업데이트 <br>"+"* 성공시 코드: 1<br>"+ "* 실패시 코드: 0")
    @PutMapping("/withdrawal")
    public int putUserDelYn(UserDelYnUpdDto dto){
        return SERVICE.putUserDelYn(dto);
    }
*/
    //회원 삭제 jpa
    @Operation(summary = "회원 탈퇴상태(delYn) 업데이트p", description = "업데이트 <br>"+"* 성공시 코드: 1<br>"+ "* 실패시 코드: 0")
    @PutMapping("/withdrawal")
    public ResponseEntity<Integer> putUserDelYn2(UserDelYnUpdDto dto){
        SERVICE.putUserDelYn2(dto);
        return ResponseEntity.ok(1);
    }

    //상품 삭제
    @Operation(summary = "등록 상품 삭제")
    @DeleteMapping("/product/{productId}")
    public int delProduct(@PathVariable int productId) {
        return SERVICE.delProduct(productId);
    }

    //상품 디테일 관리자용
    @Operation(summary = "등록 상품 디테일(상품 수정용)")
    @GetMapping("/product/detail")
    public AdminProductDetailVo2 getProductDetail1(@RequestParam int productId) {
        return SERVICE.getProductDetail1(productId);
    }

    //상품 디테일 관리자용 jpa
    @Tag(name = "관리자 페이지 별도 API")
    @Operation(summary = "등록 상품 디테일(상품 수정용)p")
    @GetMapping("/product/detail2")
    public AdminProductDetailVo3 getProductDetail(@RequestParam int productId) {
        return SERVICE.getProductDetail(productId);
    }
}

