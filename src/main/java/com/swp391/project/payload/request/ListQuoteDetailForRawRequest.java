package com.swp391.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListQuoteDetailForRawRequest {
    private List<QuoteDetailForRawRequest> quoteDetailForRawRequests;
}
