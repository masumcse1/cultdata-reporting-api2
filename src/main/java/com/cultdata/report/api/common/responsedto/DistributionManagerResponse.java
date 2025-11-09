package com.cultdata.report.api.common.responsedto;

import lombok.Data;

import java.util.List;

@Data
public class DistributionManagerResponse {
    private List<DistributionManagerDTO> data;

}