package com.green.winey_final.repository.support;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.io.Serializable;
import java.util.List;

@Getter
public class PageCustom<T> implements Serializable {

    private List<T> content;

    private PageableCustom pageableCustom;
//쿼리
    public PageCustom(List<T> content, Pageable pageable, long total) {
        this.content = content;
        this.pageableCustom = new PageableCustom(new PageImpl(content, pageable, total));
    }

    public PageCustom(List<T> content, Pageable pageable, boolean hasNext) {
        this.content = content;
        this.pageableCustom = new PageableCustom(new SliceImpl(content, pageable, hasNext));
    }

}