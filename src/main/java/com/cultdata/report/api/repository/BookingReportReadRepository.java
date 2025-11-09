package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingsPage;
import com.cultdata.report.api.model.BookingReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookingReportReadRepository implements IBookingReportReadRepository {

    private final BookingReportRepository bookingReportRepository;

    @Override
    public BookingsPage getBookings(BookingReportFilterDto filter) {

        log.info("Fetching getBookings with Specification filters...");

        Specification<BookingReport> spec = BookingSpecification.buildBookingCriteriaList(filter);
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("bookingDate").descending());
        Page<BookingReport> pageResults = bookingReportRepository.findAll(spec, pageable);

        BookingsPage  bookingPage = new BookingsPage();
        bookingPage.setTotalPages(pageResults.getTotalPages());
        bookingPage.setTotalRecords((int)pageResults.getTotalElements());
        bookingPage.setContent(pageResults.getContent());

        return bookingPage;
    }

    @Override
    public BookingsPage getCollections(BookingReportFilterDto filter) {

        log.info("Fetching getBookings with Specification filters...");

        Specification<BookingReport> spec = BookingSpecification.buildCancelCriteriaList(filter);
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("bookingDate").descending());
        Page<BookingReport> pageResults = bookingReportRepository.findAll(spec, pageable);

        BookingsPage  bookingPage = new BookingsPage();
        bookingPage.setTotalPages(pageResults.getTotalPages());
        bookingPage.setTotalRecords((int)pageResults.getTotalElements());
        bookingPage.setContent(pageResults.getContent());

        return bookingPage;
    }
}
