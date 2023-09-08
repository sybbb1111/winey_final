package com.green.winey_final.detail2;

import com.green.winey_final.detail2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetailMapper2 {
    WineDetailVo2 selWineDetail(Long productId);
    List<String> selPairing(Long productId);
    String selCount(SelCountVo2 vo);
    SelSale2 selSale(Long productId);
    List<String> selAroma(Long productId);
    SelWineKorNm2 selKorNm(Long productId);

}
