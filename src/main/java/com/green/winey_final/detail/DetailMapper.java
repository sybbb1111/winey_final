package com.green.winey_final.detail;

import com.green.winey_final.detail.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetailMapper {
    WineDetailVo selWineDetail(Long productId);
    List<String> selPairing(Long productId);
    String selCount(SelCountVo vo);
    SelSale selSale(Long productId);
    List<String> selAroma(Long productId);
    SelWineKorNm selKorNm(Long productId);





}
