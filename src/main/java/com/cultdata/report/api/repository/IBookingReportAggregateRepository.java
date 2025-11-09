package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingReportPage;


public interface IBookingReportAggregateRepository {
    public BookingReportPage getBookingReportData(BookingReportFilterDto filter);
}
