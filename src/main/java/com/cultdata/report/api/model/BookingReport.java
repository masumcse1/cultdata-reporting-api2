package com.cultdata.report.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_report")
@Data
public class BookingReport {

    @Id
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "business_unit_id")
    private Integer businessUnitId;

    @Column(name = "business_unit_name")
    private String businessUnitName;

    @Column(name = "distribution_manager_id")
    private Integer distributionManagerId;

    @Column(name = "distribution_manager_name")
    private String distributionManagerName;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "for_testing")
    private Boolean forTesting;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "channel_id")
    private Integer channelId;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "number_of_room_nights")
    private Integer numberOfRoomNights;

    @Column(name = "cancellation")
    private Boolean cancellation;

    @Column(name = "cancellation_date")
    private LocalDateTime cancellationDate;

    @Column(name = "gbv")
    private Double gbv;

    @Column(name = "currency")
    private String currency;
}

