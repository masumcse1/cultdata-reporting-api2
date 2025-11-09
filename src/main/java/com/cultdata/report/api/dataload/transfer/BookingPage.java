package com.cultdata.report.api.dataload.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPage {
    private int totalPages;
    private int totalRecords;
    private List<BookingDto> data;
}
