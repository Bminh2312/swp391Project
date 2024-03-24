package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.payload.request.QuoteRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuoteServiceImp {
    int create (QuoteRequest quoteRequest, String status);

    int createSampleQuote (int projectId, int roomId, double area, String status, MultipartFile imgFile);

    boolean update (QuoteRequest quoteRequest, int id, String status);


    boolean updateTotal (int projectId);

    List<ProjectDTO> findAllQuoteRoomByProject(boolean isSample);

    ProjectWithAllQuoteDTO findQuoteRoomByProjectId(int id);

    String getQrToPay(int projectId, String description);
}
