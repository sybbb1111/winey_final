package com.green.winey_final.main;


import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService SERVICE;
    private final MainRepository MAIN_REP;


    @GetMapping
    public ResponseEntity<List<ProductEntity>> getWines(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> products = MAIN_REP.findAll(pageable);

        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/redWines")
    public ResponseEntity<List<ProductEntity>> getRedWines(
            @RequestParam Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ProductEntity> products = MAIN_REP.findAllByCategoryId(categoryId, pageable);

        return ResponseEntity.ok(products);
    }

}