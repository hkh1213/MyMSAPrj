package com.example.catalogservice.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDTO implements Serializable {

    private String productId;
    private Integer qty;
    private Integer stock;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
