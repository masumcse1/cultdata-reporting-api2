package com.cultdata.report.api.dataload.service;

import com.cultdata.report.api.model.BookingReport;
import com.cultdata.report.api.repository.BookingReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingReportWriteService {

    private final BookingReportRepository bookingReportRepository;

    /**
     * - Saves or updates a batch of booking reports:
     * - Updates entities if bookingId already exists.
     * - Inserts new entities otherwise.
     */
    @Transactional
    public void saveBookingReports(List<BookingReport> reports) {
        int insertCount = 0;
        int updateCount = 0;

        for (BookingReport report : reports) {
            Optional<BookingReport> existing = bookingReportRepository.findById(report.getBookingId());

            if (existing.isPresent()) {
                BookingReport existingReport = existing.get();
                updateFromReport(existingReport, report);
                bookingReportRepository.save(existingReport);
                updateCount++;
            } else {
                bookingReportRepository.save(report);
                insertCount++;
            }
        }

        log.info("Booking reports upsert completed. Inserted: {}, Updated: {}", insertCount, updateCount);
    }

    /**
     * Copies fields from new report to existing entity.
     */
    private void updateFromReport(BookingReport existing, BookingReport report) {
        existing.setClientId(report.getClientId());
        existing.setClientName(report.getClientName());
        existing.setCountryId(report.getCountryId());
        existing.setCountryName(report.getCountryName());
        existing.setBusinessUnitId(report.getBusinessUnitId());
        existing.setBusinessUnitName(report.getBusinessUnitName());
        existing.setDistributionManagerId(report.getDistributionManagerId());
        existing.setDistributionManagerName(report.getDistributionManagerName());
        existing.setCapacity(report.getCapacity());
        existing.setForTesting(report.getForTesting());
        existing.setSupplierId(report.getSupplierId());
        existing.setSupplierName(report.getSupplierName());
        existing.setChannelId(report.getChannelId());
        existing.setBookingDate(report.getBookingDate());
        existing.setNumberOfRoomNights(report.getNumberOfRoomNights());
        existing.setCancellation(report.getCancellation());
        existing.setCancellationDate(report.getCancellationDate());
        existing.setGbv(report.getGbv());
        existing.setCurrency(report.getCurrency());
    }
}
