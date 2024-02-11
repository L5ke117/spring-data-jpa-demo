package com.luke.springdatajpademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookNameAndDesc {
    private String name;
    private String description;
}
