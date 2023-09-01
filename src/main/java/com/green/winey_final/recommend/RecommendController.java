package com.green.winey_final.recommend;

import com.green.winey_final.recommend.model.RecommendVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService service;

    @GetMapping("/getuser")
    public List<Long> getUserWine(@RequestParam Long userId){
        return service.selUserinfo(userId);
    }

    @GetMapping("/getuserrecommend")
    public RecommendVo getRecommend(@RequestParam Long userId){
        return service.selUserRecommend(userId);
    }







}
