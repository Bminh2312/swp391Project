package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWithAllQuoteDTO {
    private ProjectDTO projectDTO;
    private double constructionPriceType;
    private  double constructionPriceDesign;
    private  double totalPrice;
    private List<RoomWithAllQuoteDetailDTO> withAllQuoteDetailDTOList;
}
