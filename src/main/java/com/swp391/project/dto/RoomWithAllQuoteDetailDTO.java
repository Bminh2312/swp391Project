package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWithAllQuoteDetailDTO {
    private int quoteId;
    private String roomName;
    private String img;
    private double area;
    List<QuoteDetailDTO> quoteDetailDTOS;
    private double total;
}
