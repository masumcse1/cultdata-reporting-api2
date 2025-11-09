package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingAggregateReportDTO;
import com.cultdata.report.api.common.result.BookingReportPage;

import java.util.List;

public interface IBookingReportPageRepository {

    List<BookingAggregateReportDTO> getBookingReportsWithoutPage(BookingReportFilterDto filter);

    BookingReportPage getBookingReportsWitPage(BookingReportFilterDto filter);

    public BookingReportPage getAggregatedBookingReportPage(BookingReportFilterDto filter);
}
