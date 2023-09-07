package com.green.winey_final.main.model;

import com.green.winey_final.search.model.WineVo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Builder
public class WineRes {
    private Long count;
    private List<WineVo> list;
}
