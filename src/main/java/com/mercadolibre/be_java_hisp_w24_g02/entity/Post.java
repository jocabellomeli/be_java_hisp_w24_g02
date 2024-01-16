package com.mercadolibre.be_java_hisp_w24_g02.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Post {
    private Integer id;
    private Integer userId;
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
}
