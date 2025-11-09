package com.cultdata.report.api.dataload.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClientPage {
    private Integer totalPages;
    private List<Client3Dto> data;
    private Integer totalRecords;
}
