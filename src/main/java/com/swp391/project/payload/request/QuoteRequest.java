package com.swp391.project.payload.request;


import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.QuoteDetailDTO;
import com.swp391.project.dto.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRequest {
    private int projectId;
    private int roomId;
    private double area;
}
