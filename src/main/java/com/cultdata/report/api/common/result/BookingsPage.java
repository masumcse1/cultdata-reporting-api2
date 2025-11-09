package com.cultdata.report.api.common.result;

import com.cultdata.report.api.model.BookingReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingsPage {
    private int totalPages;
    private int totalRecords;
    private List<BookingReport> content;


    public static BookingsPage from(Page<BookingReport> page) {
        BookingsPage dto = new BookingsPage();
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecords((int) page.getTotalElements());
        dto.setContent(page.getContent());
        return dto;
    }
}
