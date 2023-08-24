package com.green.winey_final.main;


import com.green.winey_final.main.model.WineTotalVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService SERVICE;

    @GetMapping("/redwine")
    public List<WineTotalVo> getRedWines(@PageableDefault(sort="productId", direction = Sort.Direction.DESC, size=9) Pageable pageable){
        return SERVICE.selRedWines(pageable);
    }
}
