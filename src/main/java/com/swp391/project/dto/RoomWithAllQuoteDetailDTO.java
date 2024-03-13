package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWithAllQuoteDetailDTO {
    private String roomName;
    private String img;
    List<QuoteDetailDTO> quoteDetailDTOS;
    private double total;
}
