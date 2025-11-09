package com.cultdata.report.api.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingAggregateReportDTO {
    private Long clientId;                      // matches client_id
    private String clientName;                  // matches client_name
    private Integer countryId;                  // matches country_id
    private String countryName;                 // matches country_name
    private Integer businessUnitId;             // matches business_unit_id
    private String businessUnitName;            // matches business_unit_name
    private Integer distributionManagerId;      // matches distribution_manager_id
    private String distributionManagerName;     // matches distribution_manager_name
    private Integer capacity;                   // matches capacity
    private Boolean forTesting;                 // matches for_testing
    private Long distinctChannelCount;          // matches distinctChannelCount
    private Integer bookingNumberOfRoomNights;  // matches bookingNumberOfRoomNights
    private Long bookingCount;                  // matches bookingCount
    private Double bookingGbv;                  // matches bookingGbv
    private Integer cancellationNumberOfRoomNights; // matches cancellationNumberOfRoomNights
    private Long cancellationCount;             // matches cancellationCount
    private Double cancellationGbv;             // matches cancellationGbv
    private String currency;                    // matches currency
}

