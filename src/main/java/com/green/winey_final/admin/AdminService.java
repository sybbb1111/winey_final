package com.green.winey_final.admin;


import com.green.winey_final.admin.model.*;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.common.utils.MyFileUtils;
import com.green.winey_final.repository.*;
import com.green.winey_final.repository.support.PageCustom;
import com.querydsl.core.QueryResults;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
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

    private final UserRepository userRep;
    private final StoreRepository storeRep;
    private final RegionNmRepository regionNmRep;
    private final SaleRepository saleRep;

    private final AromaRepository aromaRep;
    private final FeatureRepository featureRep;
    private final CountryRepository countryRep;
    private final CategoryRepository categoryRep;
    private final AromaCategoryRepository aromaCategoryRep;
    private final WinePairingRepository winePairingRep;
    private final SmallCategoryRepository smallCategoryRep;
    private final ProductRepository productRep;
    private final OrderRepository orderRep;

    private final EntityManager em;


    private final AdminWorkRepositoryImpl adminWorkRep;

    @Autowired
    public AdminService(AdminMapper MAPPER, @Value("${file.dir}") String FILE_DIR, UserRepository userRep, StoreRepository storeRep, RegionNmRepository regionNmRep, SaleRepository saleRep, AromaRepository aromaRep, FeatureRepository featureRep, CountryRepository countryRep, CategoryRepository categoryRep, AromaCategoryRepository aromaCategoryRep, WinePairingRepository winePairingRep, SmallCategoryRepository smallCategoryRep, ProductRepository productRep, OrderRepository orderRep, EntityManager em, AdminWorkRepositoryImpl adminWorkRep) {
        this.MAPPER = MAPPER;
        this.FILE_DIR = MyFileUtils.getAbsolutePath(FILE_DIR);
        this.userRep = userRep;
        this.storeRep = storeRep;
        this.regionNmRep = regionNmRep;
        this.saleRep = saleRep;
        this.aromaRep = aromaRep;
        this.featureRep = featureRep;
        this.countryRep = countryRep;
        this.categoryRep = categoryRep;
        this.aromaCategoryRep = aromaCategoryRep;
        this.winePairingRep = winePairingRep;
        this.smallCategoryRep = smallCategoryRep;
        this.productRep = productRep;
        this.orderRep = orderRep;
        this.em = em;
        this.adminWorkRep = adminWorkRep;
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
                String targetPath = FILE_DIR + "/wine/"+dto.getProductId()+"/"; //
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
    //jpa
    public int postProduct2(MultipartFile pic, ProductInsParam param) {
        //t_feature 인서트
        FeatureEntity featureEntity = FeatureEntity.builder()
                .acidity((long) param.getAcidity())
                .sweety((long) param.getSweety())
                .body((long) param.getBody())
                .build();
        FeatureEntity featureResult = featureRep.save(featureEntity);

        //t_product
        ProductEntity productEntity = ProductEntity.builder()
                .nmKor(param.getNmKor())
                .nmEng(param.getNmEng())
                .price(param.getPrice())
                .promotion(param.getPromotion())
                .beginner(param.getBeginner())
                .alcohol(param.getAlcohol())
                .quantity(param.getQuantity())
                .featureEntity(featureResult)
                .countryEntity(countryRep.getReferenceById((long) param.getCountry()))
                .categoryEntity(categoryRep.getReferenceById((long) param.getCategory()))
                .build();

        //t_sale
        SaleEntity saleEntity = SaleEntity.builder()
                .sale(param.getSale())
                .salePrice(param.getSalePrice())
                .startSale(param.getStartSale())
                .endSale(param.getEndSale())
                .productEntity(productEntity) // *******
                .build();

        //t_aroma
        AromaEntity aromaEntity = new AromaEntity();

        //페어링음식 t_wine_pairing에 인서트
        WinePairingEntity winePairingEntity = new WinePairingEntity();

        //사진 파일 업로드 로직 1 (사진업로드 하고 상품 등록할 때 실행되는 부분)
        //임시경로에 사진 저장
        if(pic != null) { //만약에 pic가 있다면
            File tempDic = new File(FILE_DIR, "/temp");
            if (!tempDic.exists()) { // /temp 경로에 temp폴더가 존재하지 않는다면 temp폴더를 만든다.
                tempDic.mkdirs();
            }

            String savedFileName = MyFileUtils.makeRandomFileNm(pic.getOriginalFilename());

            File tempFile = new File(tempDic.getPath(), savedFileName);

            try {
                pic.transferTo(tempFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            productEntity.setPic(savedFileName);

            //사진파일 업로드 로직2
            //로직2 안에 t_product 인서트
            //로직2 안에 t_sale 인서트
            //로직2 안에 t_aroma인서트
            //로직2 안에 t_winepairing인서트

            //t_product에 인서트
            ProductEntity productResult = productRep.save(productEntity);
            String dbFilePath = "wine/" +  productResult.getProductId() + "/" + savedFileName; //db에 wine/pk값/파일명 순으로 저장하기 위한 로직
            productResult.setPic(dbFilePath);
            try {
                if (productResult == null) {
                    throw new Exception("상품을 등록할 수 없습니다.");
                }
            } catch (Exception e) {
                tempFile.delete();
                return 0;
            }
            if (productResult != null) {
                String targetPath = FILE_DIR + "wine/" + productResult.getProductId() +"/";
                File targetDic = new File(targetPath);
                if (!targetDic.exists()) {
                    targetDic.mkdirs();
                }
                File targetFile = new File(targetPath, savedFileName);
                tempFile.renameTo(targetFile);
                //t_sale 인서트
                saleRep.save(saleEntity);

                //t_aroma 인서트
                aromaEntity.setProductEntity(productResult);
                for (int i = 0; i < param.getAroma().size(); i++) {
                    AromaCategoryEntity aromaCategoryResult = aromaCategoryRep.getReferenceById(param.getAroma().get(i).longValue());
                    aromaEntity.setAromaCategoryEntity(aromaCategoryResult);
                    aromaRep.save(aromaEntity);
                }
                //페어링음식 t_wine_pairing에 인서트
                winePairingEntity.setProductEntity(productResult);

                for (int i = 0; i < param.getSmallCategoryId().size(); i++) {
                    SmallCategoryEntity smallCategoryResult = smallCategoryRep.getReferenceById(param.getSmallCategoryId().get(i).longValue());
                    winePairingEntity.setSmallCategoryEntity(smallCategoryResult);
                    winePairingRep.save(winePairingEntity);
                }
                return productResult.getProductId().intValue();
            }
        }
        //사진업로드 안하고 상품 등록할 때 실행되는 부분
        ProductEntity productResult = productRep.save(productEntity);
        // 할인율, 할인가격 t_sale에 인서트 (product_id 이용해서) , 할인시작일과 종료일은(3차 때 구현)
        saleRep.save(saleEntity);
        //t_aroma 인서트
        aromaEntity.setProductEntity(productResult);
        for (int i = 0; i < param.getAroma().size(); i++) {
            AromaCategoryEntity aromaCategoryResult = aromaCategoryRep.getReferenceById(param.getAroma().get(i).longValue());
            aromaEntity.setAromaCategoryEntity(aromaCategoryResult);
            aromaRep.save(aromaEntity);
        }
        //페어링음식 t_wine_pairing에 인서트
        winePairingEntity.setProductEntity(productResult);
        for (int i = 0; i < param.getSmallCategoryId().size(); i++) {
            SmallCategoryEntity smallCategoryResult = smallCategoryRep.getReferenceById(param.getSmallCategoryId().get(i).longValue());
            winePairingEntity.setSmallCategoryEntity(smallCategoryResult);
            winePairingRep.save(winePairingEntity);
        }
        return productResult.getProductId().intValue();
    }

    public int putProduct(MultipartFile pic, ProductUpdParam param) {
        ProductUpdDto dto = MAPPER.selProductFk(param.getProductId());
        ProductAromaInsDto aromaDto = new ProductAromaInsDto(); //t_aroma

        dto.setProductId(param.getProductId()); //t_product
        dto.setNmKor(param.getNmKor()); //t_product
        dto.setNmEng(param.getNmEng()); //t_product
        dto.setAlcohol(param.getAlcohol()); //t_product
        dto.setBeginner(param.getBeginner());
        dto.setPromotion(param.getPromotion());

        dto.setPrice(param.getPrice()); //t_product
        dto.setCountryId(param.getCountry()); //t_product
        dto.setCategoryId(param.getCategory()); //t_product
        dto.setQuantity(param.getQuantity()); //t_product

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
            deleteProductPic(dto.getProductId()); //사진 수정시 기존 사진 삭제 로직
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

    public int putProduct2(MultipartFile pic, ProductUpdParam param) {
        //수정할 상품의pk 가져오기
        ProductEntity productEntity = productRep.findById((long) param.getProductId()).get();

        //t_feature에 인서트 후 feature_id(pk)값을 t_product 인서트에 넣기
        FeatureEntity featureEntity = featureRep.findById(productEntity.getFeatureEntity().getFeatureId()).get();
        featureEntity.setAcidity((long) param.getAcidity());
        featureEntity.setSweety((long) param.getSweety());
        featureEntity.setBody((long) param.getBody());

        featureRep.save(featureEntity);

        //t_product
        productEntity = ProductEntity.builder()
                .productId(productRep.findById((long) param.getProductId()).get().getProductId())
                .nmKor(param.getNmKor())
                .nmEng(param.getNmEng())
                .price(param.getPrice())
                .countryEntity(countryRep.getReferenceById((long) param.getCountry()))
                .categoryEntity(categoryRep.getReferenceById((long) param.getCategory()))
                .featureEntity(featureEntity)
                .pic(productRep.findById((long) param.getProductId()).get().getPic())
                .build();

        productRep.save(productEntity);

        //t_sale
        SaleEntity saleEntity = saleRep.findByProductEntity(productRep.findById((long) param.getProductId()).get());
        saleEntity.setSale(param.getSale());
        saleEntity.setSalePrice(param.getSalePrice());
        saleEntity.setSaleYn(param.getSaleYn());

        // 세일시작/종료날짜 로직
        LocalDate parseStartDate = LocalDate.parse(param.getStartSale(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));//String startSale을 LocalDate로 변환
        LocalDate parseEndDate = LocalDate.parse(param.getEndSale(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));//String endSale을 LocalDate로 변환
        LocalDate startSale = parseStartDate.withDayOfMonth(1); //할인시작월의 1일
        LocalDate endSale = parseEndDate.withDayOfMonth(parseEndDate.lengthOfMonth()); //할인종료월의 마지막 일

        //saleDate가 이번 달과 똑같은 달이면 실행되는 로직
        if(parseStartDate.getMonthValue() == LocalDate.now().getMonthValue()) {
            saleEntity.setStartSale(LocalDate.now().plusDays(1).toString());
            saleEntity.setEndSale(endSale.toString());
            saleRep.save(saleEntity);

        } else { //saleDate가 이번 달이 아니면 실행되는 로직
            saleEntity.setStartSale(startSale.toString());
            saleEntity.setEndSale(endSale.toString());
            saleRep.save(saleEntity);
        }

        //t_aroma update
        //삭제
        int aromaEntity = aromaRep.deleteByProductEntity(productEntity);

        //인서트
        for(int i=0;i<param.getAroma().size();i++) {
            aromaRep.save(AromaEntity.builder()
                    .productEntity(productEntity)
                    .aromaCategoryEntity(aromaCategoryRep.getReferenceById(param.getAroma().get(i).longValue()))
                    .build());
        }

        //t_feature 테이블 update 하고 productEntity에 featureId를 set
        productEntity.setFeatureEntity(featureRep.save(featureEntity));

        //t_small_category table update (t_wine_pairing)
        //삭제
        winePairingRep.deleteByProductEntity(productEntity);

        //인서트
        for(int i=0;i<param.getSmallCategoryId().size();i++) {
            WinePairingEntity winePairingEntity = WinePairingEntity.builder()
                    .productEntity(productEntity)
                    .smallCategoryEntity(smallCategoryRep.getReferenceById(param.getSmallCategoryId().get(i).longValue()))
                    .build();
            winePairingRep.save(winePairingEntity);
        }

        //사진 파일 업로드 로직 1
        //임시경로에 사진 저장
        if(pic != null) { //만약에 pic가 있다면
            File tempDic = new File(FILE_DIR, "/temp");
            if(!tempDic.exists()) { // /temp 경로에 temp폴더가 존재하지 않는다면 temp폴더를 만든다.
                tempDic.mkdirs();
            }
            deleteProductPic(param.getProductId()); //기존 사진 파일 삭제
            String savedFileName = MyFileUtils.makeRandomFileNm(pic.getOriginalFilename());

            File tempFile = new File(tempDic.getPath(), savedFileName);

            try{
                pic.transferTo(tempFile);
            } catch(Exception e) {
                e.printStackTrace();
            }
            //dto는 productupddto
            String dbFilePath = "wine/" + param.getProductId() + "/" + savedFileName; //db에 wine/pk값/파일명 순으로 저장하기 위한 로직
            productEntity.setPic(dbFilePath);
            //t_product테이블 update
            //사진 파일 업로드 로직 2
            ProductEntity result = productRep.save(productEntity);

            try {
                if(result == null) {
                    throw new Exception("상품을 등록할 수 없습니다.");
                }
            } catch(Exception e) {
                tempFile.delete();
                return 0;
            }

            if (result != null) {
                String targetPath = FILE_DIR + "/wine/" + productEntity.getProductId();
                File targetDic = new File(targetPath);
                if(!targetDic.exists()) {
                    targetDic.mkdirs();
                }
                File targetFile = new File(targetPath, savedFileName);
                tempFile.renameTo(targetFile);
            }
            return productEntity.getProductId().intValue(); //성공시 상품PK값 리턴
        }
        //수정시 사진파일을 수정하지 않을 경우 (pic = null)
        productEntity.setPic(productRep.findById((long) param.getProductId()).get().getPic());
        ProductEntity result2 = productRep.save(productEntity);
        if(result2 != null) {
            return productEntity.getProductId().intValue();
        }
        return 0; // result2가 0이면 수정에 실패했다는 의미로 0 리턴
    }

    //할인 스케줄러 (t_sale 테이블의 sale_yn을 업데이트)
//    @Scheduled(cron = "0 0 0/1 1/1 * ?") //매시 정각마다 실행
    @Scheduled(cron = "0 0 0 1/1 * ?") //매일 자정마다 실행
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
    public ProductList getProduct2(SelListDto dto) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);
        int maxProduct = MAPPER.productCount(dto);

        PageDto pageDto = new PageDto(maxProduct, dto.getPage(), dto.getRow());

        return ProductList.builder()
                .page(pageDto)
                .productList(MAPPER.selProduct(dto))
                .build();
    }

    //등록 상품 리스트 출력 (전체 상품) JPA
    public PageCustom<ProductVo> getProduct(Pageable pageable, String str) {

        return adminWorkRep.selProductAll(pageable, str);
    }

    //할인 중인 상품 리스트 출력 mybatis
    public ProductSaleList getProductSale(SelListDto dto) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxProductSale = MAPPER.productSaleCount();

        return ProductSaleList.builder()
                .page(new PageDto(maxProductSale, dto.getPage(), dto.getRow()))
                .list(MAPPER.selProductSale(dto))
                .build();
    }

    //할인 중인 상품 리스트 출력 jpa
    public PageCustom<ProductSaleVo> getProductSale2(Pageable pageable) {

        return adminWorkRep.selProductSaleAll(pageable);
    }

    //가입회원 리스트 mybatis
    public UserList getUserList2(SelListDto dto) {
        int startIdx = (dto.getPage()-1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxUser = MAPPER.userCount(dto);

        return UserList.builder()
                .page(new PageDto(maxUser, dto.getPage(), dto.getRow()))
                .list(MAPPER.selUserList(dto))
                .build();
    }
    //가입회원 리스트 jpa
    public PageCustom<UserVo> getUserList(Pageable pageable, String searchType, String str) {
        return adminWorkRep.selUserAll(pageable, searchType, str);
    }

    //가입회원 상세 주문 내역(회원pk별) +페이징 처리
    public UserOrderDetailList2 getUserOrder2(Long userId, SelListDto dto) {
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

        UserInfo user = MAPPER.selUserInfo(userId);


        return UserOrderDetailList2.builder()
                .page(new PageDto(maxUserOrder, dto.getPage(), dto.getRow()))
                .userInfo(user)
                .userOrderList(list)
                .build();
    }

    public UserOrderDetailList getUserOrder(Long userId, Pageable pageable) {
        PageCustom<UserOrderDetailVo> list = adminWorkRep.selUserOrderByUserId(userId, pageable);
        UserInfo user = adminWorkRep.selUserInfoByUserId(userId, pageable);



        return UserOrderDetailList.builder()
                .userOrderList(list)
                .userInfo(user)
                .build();
    }

    //주문 내역 mybaits
    public OrderList getOrder3(SelListDto dto) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxOrder = MAPPER.orderCount();

        //상품명에 외 1 넣는 로직
        List<OrderListVo2> list = MAPPER.selOrder(dto);

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
    //주문 내역 jpa
    public PageCustom<OrderListVo> getOrder(Pageable pageable) {
        PageCustom<OrderListVo> list = adminWorkRep.selOrderAll(pageable);
        QueryResults<OrderListVo> list2 = adminWorkRep.selOrderAll2(pageable);
        list.getPageableCustom().setTotalElements(list2.getTotal());

        return list;
    }

    //상세 주문 내역 리스트 by orderId
    public OrderDetail3 getOrderDetail3(int orderId) {
        List<OrderDetail1> list1 = MAPPER.selOrderDetail1(orderId);

        List<OrderDetailVo> list = MAPPER.selOrderDetail(orderId);

        OrderDetail2 list2 = MAPPER.selOrderDetail2(orderId);

        return OrderDetail3.builder()
                .list1(list1)
                .list2(list2)
                .build();
    }

    //상세 주문 내역 리스트 by orderId
    public OrderDetail3 getOrderDetail(int orderId, Pageable pageable) {

        return OrderDetail3.builder()
                .list1(adminWorkRep.selOrderDetailByOrderId(orderId, pageable))
                .list2(adminWorkRep.selOrderDetailByOrderId2(orderId, pageable))
                .build();
    }

    //환불된 상품과 환불 사유 출력 mybatis
    public List<OrderRefundVo> getOrderRefund(SelListDto dto, Long userId) {
        int startIdx = (dto.getPage() - 1) * dto.getRow();
        dto.setStartIdx(startIdx);

        if(userId == 0L) {
            return MAPPER.selOrderRefund(dto);
        }
        return MAPPER.selOrderRefundById(dto, userId);
    }
    //환불된 상품과 환불 사유 출력 jpa
    public PageCustom<OrderRefundVo> getOrderRefund2(Pageable pageable) {
        return adminWorkRep.selOrderRefund(pageable);
    }

/*
    // 매장 정보 등록 mybatis
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
*/
    // 매장 정보 등록 jpa
    public Long insStore2(StoreInsParam param) {
        StoreEntity storeEntity = new StoreEntity();

        //tel(전화번호) 유효성 검사하기
        String pattern = "(\\d{2,3})-(\\d{3,4})-(\\d{4})"; // (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자)

        if(Pattern.matches(pattern, param.getTel())) {
            storeEntity.setRegionNmEntity(regionNmRep.findById(param.getRegionNmId()).get());
            storeEntity.setNm(param.getNm());
            storeEntity.setAddress(param.getAddress());
            storeEntity.setTel(param.getTel());

            storeRep.save(storeEntity);
            return storeEntity.getStoreId();
        }

        return 0L;
    }

    public StoreList getStore2(SelListDto dto) {
        int startIdx = (dto.getPage()-1) * dto.getRow();
        dto.setStartIdx(startIdx);

        int maxStore = MAPPER.storeCount();
        PageDto page = new PageDto(maxStore, dto.getPage(), dto.getRow());
//        page.setPageSize(3);

        return StoreList.builder()
                .page(page)
                .list(MAPPER.selStore(dto))
                .build();
    }
    //매장 리스트
    public PageCustom<StoreVo> getStore(Pageable pageable, String searchType, String str) {
        return adminWorkRep.selStoreAll(pageable, searchType, str);
    }

    //매장 정보 수정 mybatis
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


    //매장 정보 수정 jpa
    public Long updStore2(StoreInsParam param, Long storeId) {
        //tel(전화번호) 유효성 검사하기
        String pattern = "(\\d{2,3})-(\\d{3,4})-(\\d{4})"; // (2~3자리 숫자)-(3~4자리 숫자)-(4자리 숫자)
        if(Pattern.matches(pattern, param.getTel())) {
            StoreEntity storeEntity = storeRep.findById(storeId).get();
            storeEntity.setNm(param.getNm());
            storeEntity.setTel(param.getTel());
            storeEntity.setAddress(param.getAddress());
            RegionNmEntity regionNmEntity = regionNmRep.getReferenceById(param.getRegionNmId());
            storeEntity.setRegionNmEntity(regionNmEntity);
            storeRep.save(storeEntity);
            return 1L;
        }
        return 0L; //전화번호 유효성 검사 통과 실패

    }
    //매장 삭제 mybatis
    public Long deleteStore(Long storeId) {
        return MAPPER.delStore(storeId);
    }
    //매장 삭제 jpa
    public Long deleteStore2(Long storeId) {
        StoreEntity storeEntity = storeRep.findById(storeId).get();
        storeRep.deleteById(storeEntity.getStoreId());

        return 1L;
    }

    //주문상태 업데이트 mybatis (관리자 페이지용)
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
    //주문 상태 업데이트 jpa(관리자 페이지용)
    public Long updOrderStatus2(OrderStatusDto dto) {
        //order_status 코드 유효성 검사
        String orderStatus = String.valueOf(dto.getOrderStatus()); // int->String 변환
        String pattern = "[1-6]";
        if(!Pattern.matches(pattern, orderStatus)) {
            return 500L; //orderStatus 코드 유효성 검사 실패시 500 리턴
        }
        OrderEntity orderEntity = orderRep.getReferenceById(dto.getOrderId());
        orderEntity.setOrderStatus(dto.getOrderStatus());
        orderRep.save(orderEntity);

        return dto.getOrderId();
    }

    //할인 상태(saleYn) 업데이트 (관리자가 수동으로 On/Off하는 용도) mybatis
    public int putProductSaleYn(ProductSaleYnDto dto) {
        return MAPPER.updSaleYn(dto); // saleYn update 성공시 1, 실패시 0 리턴
    }

    //할인 상태(saleYn) 업데이트 (관리자가 수동으로 On/Off하는 용도) jpa
    public int putProductSaleYn2(ProductSaleYnDto dto) {
        SaleEntity saleEntity = saleRep.findById((long) dto.getProductId()).get();
        saleEntity.setSaleYn(1);
        saleRep.save(saleEntity);

        return 1; // saleYn update 성공시 1, 실패시 0 리턴
    }

/*
    //회원 삭제
    public int putUserDelYn(UserDelYnUpdDto dto){
        return MAPPER.updDelYn(dto);
    }
*/
    //회원 삭제
    public void putUserDelYn2(UserDelYnUpdDto dto){
        UserEntity userEntity = userRep.findById(dto.getUserId()).get();
        userEntity.setDelYn(1L);
        userRep.save(userEntity);
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
//        MAPPER.delProduct(productId);

        return 1;
    }

    public List<UserVo> serchUserList(AdminSerchDto dto) {
        return MAPPER.serchUser(dto);
    }


    public AdminProductDetailVo2 getProductDetail1(int productId) {
        AdminProductDetailVo2 dto = MAPPER.selPutProductInfo1(productId);
        List<Integer> aroma = MAPPER.selPutProductInfo2(productId);
        List<Integer> smallCategoryId = MAPPER.selPutProductInfo3(productId);
        dto.setAroma(aroma);
        dto.setSmallCategoryId(smallCategoryId);
        return dto;
    }

    public AdminProductDetailVo3 getProductDetail(int productId) {

        return AdminProductDetailVo3.builder()
                .product(adminWorkRep.selPutProductInfo1(productId))
                .aroma(adminWorkRep.selPutProductInfo2(productId))
                .smallCategoryId(adminWorkRep.selPutProductInfo3(productId))
                .build();
    }


}
