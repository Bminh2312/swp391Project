package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.payload.request.QuoteRequest;

import java.util.List;

public interface QuoteServiceImp {
    int create (QuoteRequest quoteRequest, String status);

    boolean update (QuoteRequest quoteRequest, int id, String status);


    boolean updateTotal (int projectId);

    List<ProjectWithAllQuoteDTO> findAllQuoteRoomByProject(boolean isSample);

    ProjectWithAllQuoteDTO findQuoteRoomByProjectId(int id);

    String getQrToPay(int projectId, String description);
}
