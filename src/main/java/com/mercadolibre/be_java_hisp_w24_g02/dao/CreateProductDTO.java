package com.mercadolibre.be_java_hisp_w24_g02.dao;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductDTO(
        @NotNull
        Integer product_id,
        @NotNull
        @NotBlank
        String product_name,
        @NotNull
        @NotBlank
        String type,
        @NotNull
        @NotBlank
        String brand,
        @NotNull
        @NotBlank
        String color,
        @NotNull
        @NotBlank
        String notes
) {}
