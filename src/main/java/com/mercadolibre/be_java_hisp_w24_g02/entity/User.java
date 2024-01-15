package com.mercadolibre.be_java_hisp_w24_g02.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String name;
    private List<User> followers;
    private List<User> followed;
}
