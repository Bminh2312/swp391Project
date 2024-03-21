package com.swp391.project.payload.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListQuoteDetailForProductRequest {
    private List<QuoteDetailForProductRequest> quoteDetailForProductRequests;
}
