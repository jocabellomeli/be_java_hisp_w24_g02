package com.mercadolibre.be_java_hisp_w24_g02.dao;

import jakarta.validation.constraints.NotNull;

public record CreatePostDTO(
        @NotNull
        Integer user_id,
        @NotNull
        String date,
        @NotNull
        CreateProductDTO product,
        @NotNull
        Integer category,
        @NotNull
        Double price
) {}
