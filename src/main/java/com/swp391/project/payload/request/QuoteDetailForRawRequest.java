package com.swp391.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuoteDetailForRawRequest {
    private double area;
    private int quoteId;
    private int rawMaterialId;
}
