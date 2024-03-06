package com.swp391.project.service.impl;


import com.swp391.project.dto.QuoteDetailDTO;
import com.swp391.project.payload.request.QuoteDetailForProductRequest;
import com.swp391.project.payload.request.QuoteDetailForRawRequest;

import java.util.List;

public interface QuoteDetailServiceImp {
    boolean createQuoteForProduct(QuoteDetailForProductRequest quoteDetailRequest);

    boolean createQuoteForRaw(QuoteDetailForRawRequest quoteDetailRequest);

    boolean updateQuoteForProduct(int idQuoteDetail, int idProduct, int quantity);

    boolean updateQuoteForProductByNote(int idQuoteDetail, int idProduct, double priceChange,int quantity);

    boolean updateQuoteForRaw(int idQuoteDetail, int idRawMaterial, double area);

    QuoteDetailDTO findById(int id);

    List<QuoteDetailDTO> findAll();


}