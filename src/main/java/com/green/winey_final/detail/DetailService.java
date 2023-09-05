package com.green.winey_final.detail;

//import com.green.winey_final.common.entity.ProductEntity;
//import com.green.winey_final.detail.model.SelWineKorNm;
//import com.green.winey_final.repository.ProductKorRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class DetailService {
//
//    private final ProductKorRepository productKorRepository;
//
//
//    public SelWineKorNm selKorNm(Long productId) {
//        Optional<ProductEntity> optEntity=productKorRepository.findById(productId);
//        ProductEntity entity =optEntity.get();
//
//
//        return null;
////        return SelWineKorNm.builder()
////                .productId(entity.getProductId())
////                .nmKor(entity.getNmKor())
////                .build();
//    }
//
//
//}
