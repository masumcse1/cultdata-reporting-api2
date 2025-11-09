package com.cultdata.report.api.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingReportFilterDto {
    private int page;
    private int size ;
    private Integer clientId;
    private List<Integer> countryIds;
    private List<Integer> businessUnitIds;
    private List<Integer> distributionManagerIds;
    private List<Integer> channelIds;
    private List<Integer> supplierIds;
    private LocalDateTime startBookingDate;
    private LocalDateTime endBookingDate;
    private Boolean excludeTestProperties;
}
