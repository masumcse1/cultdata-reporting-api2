package com.cultdata.report.api.dataload.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Integer id;
    private Client3Dto client;
    private Channel2 channel;
    private LocalDateTime bookingDate;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private Integer numberOfRoomNights;
    private Boolean isCancelled;
    private LocalDateTime cancellationDate;
    private Number gbv;
    private String currency;
}
