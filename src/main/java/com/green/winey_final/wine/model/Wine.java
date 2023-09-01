package com.green.winey_final.wine.model;

import lombok.Data;

@Data
public class Wine {
    private int id;
    private String name;
    private int type_id;
    private Taste taste;
}
