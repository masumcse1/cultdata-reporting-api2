package com.cultdata.report.api.service;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingAggregateReportDTO;
import com.cultdata.report.api.common.result.BookingReportPage;
import com.cultdata.report.api.common.result.BookingsPage;
import com.cultdata.report.api.dataload.transfer.BookingPage;
import com.cultdata.report.api.repository.IBookingReportAggregateRepository;
import com.cultdata.report.api.repository.IBookingReportPageRepository;
import com.cultdata.report.api.repository.IBookingReportReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class BookingReportReadService {

    @Autowired
    private IBookingReportReadRepository iBookingReportReadRepository;

    @Autowired
    private IBookingReportPageRepository iBookingReportPageRepository;

    @Autowired
    private IBookingReportAggregateRepository iBookingReportAggregateRepository;


    public BookingReportPage getAggregatedBookingReportPage(BookingReportFilterDto filter) {
        return   iBookingReportAggregateRepository.getBookingReportData(filter);
    }

    public BookingReportPage getBookingReportsWitPage(BookingReportFilterDto filter) {
        return iBookingReportPageRepository.getBookingReportsWitPage(filter);
    }

    public List<BookingAggregateReportDTO> getAggregatedBookingReport(BookingReportFilterDto filter) {
        return iBookingReportPageRepository.getBookingReportsWithoutPage(filter);
    }

    public BookingsPage getBookingsInformation(BookingReportFilterDto filter) {
        log.info("Fetching getBookingsInformation with  filters...");

        return iBookingReportReadRepository.getBookings(filter);
    }

    public BookingsPage getCancelInformation(BookingReportFilterDto filter) {
        log.info("Fetching getBookingsInformation with  filters...");

        return iBookingReportReadRepository.getCollections(filter);
    }

}
