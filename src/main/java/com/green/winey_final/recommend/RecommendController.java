package com.green.winey_final.recommend;

import com.green.winey_final.recommend.model.RecommendVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "사용자맞춤와인추천 3차JPA진행중")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService service;

    @GetMapping("/getuser")
    public List<Long> getUserWine(){
        return service.selUserinfo();
    }

    @GetMapping("/getuserrecommend")
    public RecommendVo getRecommend(){
        return service.selUserRecommend();
    }







}
