package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.payload.request.QuoteRequest;

public interface QuoteServiceImp {
    int create (QuoteRequest quoteRequest, String status);

    boolean update (QuoteRequest quoteRequest, int id, String status);


    boolean updateTotal (int projectId);

    ProjectWithAllQuoteDTO findAllQuoteRoomByProject(int projectId, String status);

    String getQrToPay(int projectId, String description);
}
