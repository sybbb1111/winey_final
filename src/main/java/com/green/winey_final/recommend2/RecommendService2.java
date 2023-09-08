package com.green.winey_final.recommend2;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.recommend2.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService2 {
    private final RecommendMapper2 mapper;
    private final AuthenticationFacade facade;

    public List<Long> selRecommend(RecommendRes2 res) {
        Long user = facade.getLoginUserPk();
        List<Long> result = mapper.selRecommend(res);
        UserinfoDto2 dto = new UserinfoDto2();
        dto.setUserId(user);
        dto.setProductId(result);

        if (res.getCategoryId() != null) {
            for (Long categoryId : res.getCategoryId()) {
                UserCategory category = new UserCategory();
                category.setUserId(user);
                category.setCategoryId(categoryId);
                mapper.insUserCategory(category);
            }
        } else {
            UserCategory category = new UserCategory();
            category.setUserId(user);
            mapper.insUserCategory(category);
        }

        if (res.getCountryId() != null) {
            for (Long countryId : res.getCountryId()) {
                UserCountry country = new UserCountry();
                country.setUserId(user);
                country.setCountryId(countryId);
                mapper.insUserCountry(country);
            }
        } else {
            UserCountry country=new UserCountry();
            country.setUserId(user);
            mapper.insUserCountry(country);
        }

        if (res.getPriceRange() != null) {
            for (Long priceRangeId : res.getCountryId()) {
                UserPriceRange priceRange =new UserPriceRange();
                priceRange.setUserId(user);
                priceRange.setPriceRange(priceRangeId);
                mapper.insUserPriceRange(priceRange);
            }
        } else {
            UserPriceRange priceRange =new UserPriceRange();
            priceRange.setUserId(user);
            mapper.insUserPriceRange(priceRange);
        }

        if (res.getSmallCategoryId() != null) {
            for (Long smallCategoryId : res.getSmallCategoryId()) {
                UserSmallCategory smallCategory =new UserSmallCategory();
                smallCategory.setUserId(user);
                smallCategory.setSmallCategoryId(smallCategoryId);
                mapper.insUserSmallCateogry(smallCategory);
            }
        } else {
            UserSmallCategory smallCategory =new UserSmallCategory();
            smallCategory.setUserId(user);
            mapper.insUserSmallCateogry(smallCategory);
        }

        if (res.getAromaCategoryId() != null) {
            for (Long aromaId : res.getAromaCategoryId()) {
                UserAroma aroma =new UserAroma();
                aroma.setUserId(user);
                aroma.setAromaCategoryId(aromaId);
                mapper.insUserAroma(aroma);
            }
        } else {
            UserAroma aroma =new UserAroma();
            aroma.setUserId(user);
            mapper.insUserAroma(aroma);
        }

        mapper.insUserinfo(dto);
        return result;
    }


    public int loginUserPk() {
        UserDto2 dto = new UserDto2();
        dto.setUserId(facade.getLoginUserPk());
        return mapper.loginUserPk(dto);
    }


    public List<Long> selUserinfo() {
        SelRecommendDto2 dto = new SelRecommendDto2();
        dto.setUserId(facade.getLoginUserPk());
        return mapper.selUserinfo(dto);
    }


    public RecommendVo2 selUserRecommend(){
        UserDto2 dto =new UserDto2();
        dto.setUserId(facade.getLoginUserPk());
         List<Long> categoryId=mapper.selUserCategoryId(dto);
         List<Long> countryId=mapper.selUserCountryId(dto);
         List<Long> smallCategoryId=mapper.selUserSmallCategoryId(dto);
         List<Long> priceRange=mapper.selUserPriceRange(dto);
         List<Long> aroma=mapper.selUserAroma(dto);
        return RecommendVo2.builder()
                .categoryId(categoryId)
                .countryId(countryId)
                .smallCategoryId(smallCategoryId)
                .priceRange(priceRange)
                .aromaCategoryId(aroma)
                .build();
    }


    public List<Long> updRecommend(RecommendRes2 res) {
        Long user = facade.getLoginUserPk();
        UserDto2 dto1 =new UserDto2();
        dto1.setUserId(user);
        mapper.delInfo(dto1);
        mapper.delUserCategory(dto1);
        mapper.delUserCountry(dto1);
        mapper.delUserPriceRange(dto1);
        mapper.delUserSmallCategory(dto1);
        mapper.delUserAroma(dto1);
        List<Long> result = mapper.selRecommend(res);
        UserinfoDto2 dto = new UserinfoDto2();
        dto.setUserId(user);
        dto.setProductId(result);

        if (res.getCategoryId() != null) {
            for (Long categoryId : res.getCategoryId()) {
                UserCategory category = new UserCategory();
                category.setUserId(user);
                category.setCategoryId(categoryId);
                mapper.insUserCategory(category);
            }
        } else {
            UserCategory category = new UserCategory();
            category.setUserId(user);
            mapper.insUserCategory(category);
        }

        if (res.getCountryId() != null) {
            for (Long countryId : res.getCountryId()) {
                UserCountry country = new UserCountry();
                country.setUserId(user);
                country.setCountryId(countryId);
                mapper.insUserCountry(country);
            }
        } else {
            UserCountry country=new UserCountry();
            country.setUserId(user);
            mapper.insUserCountry(country);
        }

        if (res.getPriceRange() != null) {
            for (Long priceRangeId : res.getCountryId()) {
                UserPriceRange priceRange =new UserPriceRange();
                priceRange.setUserId(user);
                priceRange.setPriceRange(priceRangeId);
                mapper.insUserPriceRange(priceRange);
            }
        } else {
            UserPriceRange priceRange =new UserPriceRange();
            priceRange.setUserId(user);
            mapper.insUserPriceRange(priceRange);
        }

        if (res.getSmallCategoryId() != null) {
            for (Long smallCategoryId : res.getSmallCategoryId()) {
                UserSmallCategory smallCategory =new UserSmallCategory();
                smallCategory.setUserId(user);
                smallCategory.setSmallCategoryId(smallCategoryId);
                mapper.insUserSmallCateogry(smallCategory);
            }
        } else {
            UserSmallCategory smallCategory =new UserSmallCategory();
            smallCategory.setUserId(user);
            mapper.insUserSmallCateogry(smallCategory);
        }

        if (res.getAromaCategoryId() != null) {
            for (Long aromaId : res.getAromaCategoryId()) {
                UserAroma aroma =new UserAroma();
                aroma.setUserId(user);
                aroma.setAromaCategoryId(aromaId);
                mapper.insUserAroma(aroma);
            }
        } else {
            UserAroma aroma =new UserAroma();
            aroma.setUserId(user);
            mapper.insUserAroma(aroma);
        }

        mapper.insUserinfo(dto);
        return result;
    }


}
