package com.green.winey_final.admin;


import com.green.winey_final.admin.model.*;
import com.green.winey_final.common.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class AdminService {

    private final AdminMapper MAPPER;
    private final String FILE_DIR;

    @Autowired
    public AdminService(AdminMapper MAPPER, @Value("${file.dir}") String FILE_DIR) {
        this.MAPPER = MAPPER;
        this.FILE_DIR = MyFileUtils.getAbsolutePath(FILE_DIR);
    }

    public int postProduct(MultipartFile pic, ProductInsParam param) {
        ProductInsDto dto = new ProductInsDto();

        ProductAromaInsDto aromaDto = new ProductAromaInsDto(); //t_aroma

        dto.setSweety(param.getSweety()); //t_feature
        dto.setAcidity(param.getAcidity()); //t_feature
        dto.setBody(param.getBody()); //t_feature

        dto.setCategoryId(param.getCategory()); //t_product
        dto.setCountryId(param.getCountry()); //t_product

        dto.setNmKor(param.getNmKor()); //t_product
        dto.setNmEng(param.getNmEng()); //t_product
        dto.setPrice(param.getPrice()); //t_product
        dto.setPromotion(param.getPromotion()); //t_product
        dto.setBeginner(param.getBeginner()); //t_product
        dto.setAlcohol(param.getAlcohol()); //t_product
        dto.setQuantity(param.getQuantity()); //t_product

        //t_sale 로직
        dto.setSale(param.getSale()); // t_sale
        dto.setSalePrice(param.getSalePrice()); // t_sale
        dto.setSaleYn(param.getSaleYn());

        LocalDate parseStartDate = LocalDate.parse(param.getStartSale(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));//String startSale을 LocalDate로 변환
        LocalDate parseEndDate = LocalDate.parse(param.getEndSale(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));//String endSale을 LocalDate로 변환
        LocalDate startSale = parseStartDate.withDayOfMonth(1); //할인시작월의 1일
        LocalDate endSale = parseEndDate.withDayOfMonth(parseEndDate.lengthOfMonth()); //할인종료월의 마지막 일

//        dto.setSmallCategoryId(param.getSmallCategoryId());// t_wine_pairing 테이블에 인서트 아래에서

        // 피쳐 인서트 하기
        MAPPER.insFeature(dto); //t_feature 인서트 후 pk값 productInsDto에 들어감

        //사진 파일 업로드 로직 1 (사진업로드 하고 상품 등록할 때 실행되는 부분)
        //임시경로에 사진 저장
        if(pic != null) { //만약에 pic가 있다면
            File tempDic = new File(FILE_DIR, "/temp");
            if(!tempDic.exists()) { // /temp 경로에 temp폴더가 존재하지 않는다면 temp폴더를 만든다.
                tempDic.mkdirs();
            }

            String savedFileName = MyFileUtils.makeRandomFileNm(pic.getOriginalFilename());

            File tempFile = new File(tempDic.getPath(), savedFileName);

            try{
                pic.transferTo(tempFile);
            } catch(Exception e) {
                e.printStackTrace();
            }

//            dto.setPic(savedFileName);

            //t_product에 인서트
            //사진 파일 업로드 로직 2
            int result = MAPPER.insProduct(dto); //t_product 인서트 후 pk값 productInsDto에 들어감
            String dbFilePath = "wine/" + dto.getProductId() + "/" + savedFileName; //db에 wine/pk값/파일명 순으로 저장하기 위한 로직
            dto.setPic(dbFilePath); //db에 wine/pk값/파일명 순으로 저장하기 위한 로직
            MAPPER.updProductPic(dto); //db에 wine/pk값/파일명 순으로 저장하기 위한 로직
            try {
                if(result == 0) {
                    throw new Exception("상품을 등록할 수 없습니다.");
                }
            } catch(Exception e) {
                tempFile.delete();
                return 0;
            }
            if (result == 1) {
                String targetPath = FILE_DIR + "/wine/"+dto.getProductId()+"/"; //수정예정?
//                String targetPath = FILE_DIR + "/winey/product/" + dto.getProductId();
                File targetDic = new File(targetPath);
                if(!targetDic.exists()) {
                    targetDic.mkdirs();
                }
                File targetFile = new File(targetPath, savedFileName);
//                String dbFilePath = "wine/" + dto.getProductId() + "/" + savedFileName; //
                tempFile.renameTo(targetFile);

                //t_sale 인서트
                //startSale이 이번 달과 똑같은 달이면 실행되는 로직
                if(parseStartDate.getMonthValue() == LocalDate.now().getMonthValue()) {
                    dto.setStartSale(LocalDate.now().plusDays(1));
                    dto.setEndSale(endSale);
                    MAPPER.insSale(dto);
                } else { //startSale이 이번 달이 아니면 실행되는 로직
                    dto.setStartSale(startSale);
                    dto.setEndSale(endSale);
                    MAPPER.insSale(dto);
                }
                //t_aroma 인서트
                for(int i=0;i<param.getAroma().size();i++) {
                    aromaDto.setProductId(dto.getProductId());
                    aromaDto.setAromaCategoryId(param.getAroma().get(i));
                    MAPPER.insAroma(aromaDto);
                }
                //페어링음식 t_wine_pairing에 인서트
                for(int i=0;i<param.getSmallCategoryId().size();i++) {
                    dto.setSmallCategoryId(param.getSmallCategoryId().get(i));
                    MAPPER.insWinePairing(dto);
                }
                return dto.getProductId();
            }
        }
        //사진업로드 안하고 상품 등록할 때 실행되는 부분
        MAPPER.insProduct(dto);
        // 할인율, 할인가격 t_sale에 인서트 (product_id 이용해서) , 할인시작일과 종료일은(3차 때 구현)
        //saleDate가 이번 달과 똑같은 달이면 실행되는 로직
        if(parseStartDate.getMonthValue() == LocalDate.now().getMonthValue()) {
            dto.setStartSale(LocalDate.now().plusDays(1));
            dto.setEndSale(endSale);
            MAPPER.insSale(dto);
        } else { //saleDate가 이번 달이 아니면 실행되는 로직
            dto.setStartSale(startSale);
            dto.setEndSale(endSale);
            MAPPER.insSale(dto);
        }
        //t_aroma 인서트
        for(int i=0;i<param.getAroma().size();i++) {
            aromaDto.setProductId(dto.getProductId());
            aromaDto.setAromaCategoryId(param.getAroma().get(i));
            MAPPER.insAroma(aromaDto);
        }

        //페어링음식 t_wine_pairing에 인서트
        for(int i=0;i<param.getSmallCategoryId().size();i++) {
            dto.setSmallCategoryId(param.getSmallCategoryId().get(i));
            MAPPER.insWinePairing(dto);
        }
        return dto.getProductId();
    }

    public int putProduct(MultipartFile pic, ProductUpdParam param) {
        ProductUpdDto dto = MAPPER.selProductFk(param.getProductId());
        ProductAromaInsDto aromaDto = new ProductAromaInsDto(); //t_aroma

        dto.setProductId(param.getProductId()); //t_product
        dto.setNmKor(param.getNmKor()); //t_product
        dto.setNmEng(param.getNmEng()); //t_product

        dto.setPrice(param.getPrice()); //t_product
        dto.setCountryId(param.getCountry()); //t_product
        dto.setCategoryId(param.getCategory()); //t_product

        dto.setSale(param.getSale()); // t_sale
        dto.setSalePrice(param.getSalePrice()); // t_sale
        dto.setSaleYn(param.getSaleYn());

        LocalDate parseStartDate = LocalDate.parse(param.getStartSale(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));//String startSale을 LocalDate로 변환
        LocalDate parseEndDate = LocalDate.parse(param.getEndSale(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));//String endSale을 LocalDate로 변환
        LocalDate startSale = parseStartDate.withDayOfMonth(1); //할인시작월의 1일
        LocalDate endSale = parseEndDate.withDayOfMonth(parseEndDate.lengthOfMonth()); //할인종료월의 마지막 일

        dto.setSweety(param.getSweety()); //t_feature
        dto.setAcidity(param.getAcidity()); //t_feature
        dto.setBody(param.getBody()); //t_feature

//        aromaDto.setProductId(param.getProductId()); //t_aroma
//        aromaDto.setFlower(param.getAroma().getFlower()); //t_aroma
//        aromaDto.setPlant(param.getAroma().getPlant()); //t_aroma
//        aromaDto.setFruit(param.getAroma().getFruit()); //t_aroma
//        aromaDto.setSpicy(param.getAroma().getSpicy()); //t_aroma
//        aromaDto.setEarth(param.getAroma().getEarth()); //t_aroma
//        aromaDto.setOak(param.getAroma().getOak()); //t_aroma
//        aromaDto.setNuts(param.getAroma().getNuts()); //t_aroma

        //t_aroma 테이블 update
        //삭제
        MAPPER.delAroma(param.getProductId());
        //인서트
        for(int i=0;i<param.getAroma().size();i++) {
            aromaDto.setProductId(dto.getProductId());
            aromaDto.setAromaCategoryId(param.getAroma().get(i));
            MAPPER.insAroma(aromaDto);
        }

        //t_sale 테이블 update
        //saleDate가 이번 달과 똑같은 달이면 실행되는 로직
        if(parseStartDate.getMonthValue() == LocalDate.now().getMonthValue()) {
            dto.setStartSale(LocalDate.now().plusDays(1));
            dto.setEndSale(endSale);
            MAPPER.updSale(dto);
        } else { //saleDate가 이번 달이 아니면 실행되는 로직
            dto.setStartSale(startSale);
            dto.setEndSale(endSale);
            MAPPER.updSale(dto);
        }
        //t_feature 테이블 update
        MAPPER.updFeature(dto);

        //t_small_category table update
        MAPPER.delWinePairing(dto);

        ProductInsDto pairingDto = new ProductInsDto();
        pairingDto.setProductId(param.getProductId());
        pairingDto.setSmallCategoryId(param.getCategory());

        for(int i=0;i<param.getSmallCategoryId().size();i++) {
            pairingDto.setSmallCategoryId(param.getSmallCategoryId().get(i));
            MAPPER.insWinePairing(pairingDto);
        }

        //사진 파일 업로드 로직 1
        //임시경로에 사진 저장
        if(pic != null) { //만약에 pic가 있다면
            File tempDic = new File(FILE_DIR, "/temp");
            if(!tempDic.exists()) { // /temp 경로에 temp폴더가 존재하지 않는다면 temp폴더를 만든다.
                tempDic.mkdirs();
            }

            String savedFileName = MyFileUtils.makeRandomFileNm(pic.getOriginalFilename());

            File tempFile = new File(tempDic.getPath(), savedFileName);

            try{
                pic.transferTo(tempFile);
            } catch(Exception e) {
                e.printStackTrace();
            }
            String dbFilePath = "wine/" + param.getProductId() + "/" + savedFileName; //db에 wine/pk값/파일명 순으로 저장하기 위한 로직
            dto.setPic(dbFilePath);

            //t_product테이블 update
            //사진 파일 업로드 로직 2
            int result = MAPPER.updProduct(dto); //t_product 인서트 후 pk값 productInsDto에 들어감
            try {
                if(result == 0) {
                    throw new Exception("상품을 등록할 수 없습니다.");
                }
            } catch(Exception e) {
                tempFile.delete();
                return 0;
            }
            if (result == 1) {
                String targetPath = FILE_DIR + "/wine/" + dto.getProductId();
                File targetDic = new File(targetPath);
                if(!targetDic.exists()) {
                    targetDic.mkdirs();
                }
                File targetFile = new File(targetPath, savedFileName);
                tempFile.renameTo(targetFile);
            }
            return dto.getProductId(); //성공시 상품PK값 리턴
        }

        //수정시 사진파일을 수정하지 않을 경우 (pic = null)
        int result2 = MAPPER.updProduct(dto);
        if(result2 == 1) {
            return dto.getProductId();
        }
        return 0; // result2가 0이면 수정에 실패했다는 의미로 0 리턴
    }

    //할인 스케줄러 (t_sale 테이블의 sale_yn을 업데이트)
    @Scheduled(cron = "0 0 0/1 1/1 * ?") //매시 정각마다 실행
    public void updSaleDateTime() {
        ProductUpdDto dto = new ProductUpdDto();
//        dto.setStartSale(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
//        dto.setEndSale(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setStartSale(LocalDate.now());
        dto.setEndSale(LocalDate.now());
        //t_sale테이블의 startSale(할인시작날짜)과 현재시간이 같으면 saleYn을 1로 update
        MAPPER.updSaleYnOn(dto);
        //t_sale테이블의 endSale(할인종료날짜)과 현재시간이 같으면 saleYn을 0으로 update
        MAPPER.updSaleYnOff(dto);
        log.info("매시 정각마다 할인 스케줄러(admin) 실행");
    }

    //상품 사진 삭제
    public int deleteProductPic(int productId) {
        MyFileUtils.delFolder(FILE_DIR+"/wine/"+productId);

        return 200; //성공시 200 리턴
    }

    //등록 상품 리스트 출력 (전체 상품)
    public ProductList getProduct(SelListDto dto) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);
        int maxProduct = MAPPER.productCount();

        PageDto pageDto = new PageDto(maxProduct, dto.getPage(), dto.getRow());

        return ProductList.builder()
                .page(pageDto)
                .productList(MAPPER.selProduct(dto))
                .build();
    }

    //할인 중인 상품 리스트 출력
    public ProductSaleList getProductSale(SelListDto dto) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxProductSale = MAPPER.productSaleCount();

        return ProductSaleList.builder()
                .page(new PageDto(maxProductSale, dto.getPage(), dto.getRow()))
                .list(MAPPER.selProductSale(dto))
                .build();
    }

    //가입회원 리스트
    public UserList getUserList(SelListDto dto) {
        int startIdx = (dto.getPage()-1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxUser = MAPPER.userCount();

        return UserList.builder()
                .page(new PageDto(maxUser, dto.getPage(), dto.getRow()))
                .list(MAPPER.selUserList(dto))
                .build();
    }

    //가입회원 상세 주문 내역(회원pk별) +페이징 처리
    public UserOrderDetailList getUserOrder(Long userId, SelListDto dto) {
        UserOrderDetailDto detailDto = new UserOrderDetailDto();
        detailDto.setUserId(userId);
        detailDto.setRow(dto.getRow());
        detailDto.setType(dto.getType());
        detailDto.setSort(dto.getSort());

        int startIdx = (dto.getPage() - 1) * dto.getRow();
        detailDto.setStartIdx(startIdx);

        //페이징 처리
        int maxUserOrder = MAPPER.userOrderCount(userId);

        List<UserOrderDetailVo> list = MAPPER.selUserOrder(detailDto);

        for(int i=0;i<list.size();i++) {
            if(list.get(i).getCount()>1) {
                list.get(i).setNmKor(list.get(i).getNmKor()+" 외 "+(list.get(i).getCount()-1));
            }
        }
        UserInfo user = MAPPER.selUserInfo(userId);

        //환불 금액, 횟수 구하는 로직

//        UserRefundVo refundVo = MAPPER.selUserRefundInfo(userId);
//        if(MAPPER.selUserRefundInfo(userId) != null) {
//            user.setSumOrderPrice(user.getSumOrderPrice()-refundVo.getSumOrderPrice());
//            user.setOrderCount(user.getOrderCount()-refundVo.getOrderCount());
//        }

//        System.out.println("11111111111111");
//        UserRefundVo refundVo1 = MAPPER.selUserRefundInfo1(userId);
////        System.out.println(refundVo1);
//        UserRefundVo refundVo2 = MAPPER.selUserRefundInfo2(userId);
//        System.out.println("22222222222222");
//        if(refundVo1 != null) {
//            user.setSumOrderPrice(user.getSumOrderPrice() - refundVo1.getSumOrderPrice());
//        }
//        if(refundVo2 != null) {
//            user.setOrderCount(user.getOrderCount() - refundVo2.getOrderCount());
//        }

        return UserOrderDetailList.builder()
                .page(new PageDto(maxUserOrder, dto.getPage(), dto.getRow()))
                .userInfo(user)
                .userOrderList(list)
                .build();
    }
    //주문 내역
    public OrderList getOrder(SelListDto dto) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxOrder = MAPPER.orderCount();

        //상품명에 외 1 넣는 로직
        List<OrderListVo> list = MAPPER.selOrder(dto);

        for(int i=0;i<list.size();i++) {
            if(list.get(i).getCount()>1) {
                list.get(i).setNmKor(list.get(i).getNmKor()+" 외 "+(list.get(i).getCount()-1));
            }
        }

        return OrderList.builder()
                .page(new PageDto(maxOrder, dto.getPage(), dto.getRow()))
                .list(list)
                .build();
    }

    //상세 주문 내역 리스트 by orderId
    public OrderDetail3 getOrderDetail(int orderId) {
        List<OrderDetail1> list1 = MAPPER.selOrderDetail1(orderId);

        List<OrderDetailVo> list = MAPPER.selOrderDetail(orderId);

        List<OrderDetail2> list2 = MAPPER.selOrderDetail2(orderId);

        return OrderDetail3.builder()
                .list1(list1)
                .list2(list2)
                .build();
    }

    //환불된 상품과 환불 사유 출력
    public List<OrderRefundVo> getOrderRefund(SelListDto dto, Long userId) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        if(userId == 0L) {
            return MAPPER.selOrderRefund(dto);
        }
        return MAPPER.selOrderRefundById(dto, userId);
    }

    // 매장 정보 등록
    public Long insStore(StoreInsParam param) {
        StoreInsDto dto = new StoreInsDto();
        dto.setRegionNmId(param.getRegionNmId());
        dto.setNm(param.getNm());
        dto.setTel(param.getTel());
        dto.setAddress(param.getAddress());

        //tel(전화번호) 유효성 검사하기
        String pattern = "(\\d{2,3})-(\\d{3,4})-(\\d{4})"; // (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자)

        if(Pattern.matches(pattern, param.getTel())) {
            MAPPER.insStore(dto);
            return dto.getStoreId(); //pk값 리턴
        }
        return 0L; //전화번호 유효성 검사 통과 실패
    }
    public StoreList getStore(SelListDto dto) {
        int startIdx = (dto.getPage()-1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxStore = MAPPER.storeCount();
        PageDto page = new PageDto(maxStore, dto.getPage(), dto.getRow());
        page.setPageSize(3);

        return StoreList.builder()
                .page(page)
                .list(MAPPER.selStore(dto))
                .build();
    }
    public Long updStore(StoreInsParam param, Long storeId) {
        StoreInsDto dto = new StoreInsDto();
        dto.setStoreId(storeId);
        dto.setRegionNmId(param.getRegionNmId());
        dto.setNm(param.getNm());
        dto.setTel(param.getTel());
        dto.setAddress(param.getAddress());

        //tel(전화번호) 유효성 검사하기
        String pattern = "(\\d{2,3})-(\\d{3,4})-(\\d{4})"; // (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자)

        if(Pattern.matches(pattern, param.getTel())) {
            MAPPER.updStore(dto);
            return dto.getStoreId(); //pk값 리턴
        }
        return 0L; //전화번호 유효성 검사 통과 실패
    }
    //매장 삭제
    public Long deleteStore(Long storeId) {
        return MAPPER.delStore(storeId);
    }

    //주문상태 업데이트 (관리자 페이지용)
    public Long updOrderStatus(OrderStatusDto dto) {
        //order_status 코드 유효성 검사
        String orderStatus = String.valueOf(dto.getOrderStatus()); // int -> String 변환
        String pattern = "[1-6]";
        if(!Pattern.matches(pattern, orderStatus)) {
            return 500L; //orderStatus 코드 유효성 검사 실패시 500 리턴
        }

        int result = MAPPER.updOrderStatus(dto);
        if(result == 1) {
            return dto.getOrderId();
        }
        return 0L; //매장 정보 수정 실패했다는 의미
    }

    //할인 상태(saleYn) 업데이트 (관리자가 수동으로 On/Off하는 용도)
    public int putProductSaleYn(ProductSaleYnDto dto) {
        return MAPPER.updSaleYn(dto); // saleYn update 성공시 1, 실패시 0 리턴
    }

    //회원 삭제
    public int putUserDelYn(UserDelYnUpdDto dto){
        return MAPPER.updDelYn(dto);
    }

    //등록 상품 삭제
    public int delProduct(int productId) {
        //selProductFk 이용해서 featureId 가져오기
        //featureid 삭제
        ProductUpdDto dto = MAPPER.selProductFk(productId);
        MAPPER.delFeature(dto);

        //t_sale 삭제 (productId로)
        MAPPER.delSale(productId);

        //aroma 삭제 (productId로)
        MAPPER.delAroma(productId);

        //winePairing 삭제 (productId로)
        MAPPER.delWinePairing(dto);

        //product 삭제
        MAPPER.delProduct(productId);

        return 1;
    }

    public List<UserVo> serchUserList(AdminSerchDto dto) {
        return MAPPER.serchUser(dto);
    }

    public List<ProductVo> serchProduct(AdminSerchDto dto) {
        return MAPPER.serchProduct(dto);
    }

    public AdminProductDetailVo getProductDetail(int productId) {
        AdminProductDetailVo dto = MAPPER.selPutProductInfo1(productId);
        List<Integer> aroma = MAPPER.selPutProductInfo2(productId);
        List<Integer> smallCategoryId = MAPPER.selPutProductInfo3(productId);
        dto.setAroma(aroma);
        dto.setSmallCategoryId(smallCategoryId);
        return dto;
    }
}
