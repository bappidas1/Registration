package com.apiexamples.payload;

import lombok.Data;

import java.util.List;
@Data
public class RegDto<R> {
    private List<RegistrationDTO> dto ;
    private  int pageNo;
    private  int pageSize;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;

}
