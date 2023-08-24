package com.green.winey_final.product;

import com.green.jpaentitytest.product.model.WineSelDto;
import com.green.jpaentitytest.product.model.WineTotalVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<WineTotalVo> selWine(WineSelDto dto);
}
