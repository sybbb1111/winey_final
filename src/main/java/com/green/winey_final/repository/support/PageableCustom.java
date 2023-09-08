package com.green.winey_final.repository.support;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.*;

@Getter
@Setter
public class PageableCustom {

    //    private boolean first;
//    private boolean last;
//    private boolean hasNext;
//    private int totalPages;
    private long totalElements; // totalRecordCount totalElements
    private int page;
    private int row; //size

    public PageableCustom() {
    }

    public PageableCustom(PageImpl page) {
//        this.first = page.isFirst();
//        this.last = page.isLast();
//        this.hasNext = page.hasNext();
//        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.page = page.getNumber() + 1;
        this.row = page.getSize();
    }

    public PageableCustom(Slice slice) {
//        this.first = slice.isFirst();
//        this.last = slice.isLast();
//        this.hasNext = slice.hasNext();
        this.page = slice.getNumber() + 1;
        this.row = slice.getSize();
    }
}

