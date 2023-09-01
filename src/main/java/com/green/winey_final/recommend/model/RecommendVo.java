package com.green.winey_final.recommend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RecommendVo {
   private List<Long> categoryId;
   private List<Long> countryId;
   private List<Long> smallCategoryId;
   private List<Long> priceRange;
   private List<Long> aromaCategoryId;;
}
