package com.s1.awsS3Ex.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto<T> {

    int status;
    T data;

}
