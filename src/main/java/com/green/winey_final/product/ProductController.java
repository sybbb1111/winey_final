package com.green.winey_final.product;

import com.green.jpaentitytest.product.model.WineTotalVo;
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
public class ProductController {

    private final ProductService SERVICE;

    @GetMapping("/wines")
    public List<WineTotalVo> selWine(@PageableDefault(sort="ifeed", direction = Sort.Direction.DESC, size=9) Pageable pageable){
        return SERVICE.selWine(pageable);
    }
}
