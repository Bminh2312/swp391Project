package com.swp391.project.payload.respone;

import lombok.Data;

@Data
public class BaseResponse {
    private int statusCode = 200;
    private String messsage = "";
    private Object data;
}
