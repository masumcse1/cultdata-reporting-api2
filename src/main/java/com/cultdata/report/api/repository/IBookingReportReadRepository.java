package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingAggregateReportDTO;
import com.cultdata.report.api.common.result.BookingReportPage;
import com.cultdata.report.api.common.result.BookingsPage;
import com.cultdata.report.api.dataload.transfer.BookingPage;

import java.util.List;

public interface IBookingReportReadRepository {

    public BookingsPage getBookings(BookingReportFilterDto filter);

    public BookingsPage getCollections(BookingReportFilterDto filter);
}
