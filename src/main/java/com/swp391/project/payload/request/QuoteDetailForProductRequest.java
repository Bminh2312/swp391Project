package com.swp391.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDetailForProductRequest {
    private int quantity;
    private String note;
    private int quoteId;
    private int productId;
}
