package com.cultdata.report.api.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingReportPage {
    private long totalPages;        // Change from int to long
    private long totalRecords;      // Change from int to long
    private List<BookingAggregateReportDTO> content;

    public static BookingReportPage from(Page<BookingAggregateReportDTO> page) {
        BookingReportPage dto = new BookingReportPage();
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecords(page.getTotalElements());  // No cast needed
        dto.setContent(page.getContent());
        return dto;
    }
}