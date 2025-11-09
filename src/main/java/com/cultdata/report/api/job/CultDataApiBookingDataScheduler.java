package com.cultdata.report.api.job;

import com.cultdata.report.api.dataload.service.BookingReportWriteService;
import com.cultdata.report.api.dataload.service.CultDataBookingService;
import com.cultdata.report.api.model.BookingReport;
import com.cultdata.report.api.support.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class CultDataApiBookingDataScheduler {

    @Autowired
    private CultDataBookingService cultDataBookingService;

    @Autowired
    private BookingReportWriteService bookingReportWriteService;

    @Value("${bookingDataSchedule.flag}")
    private boolean bookingDataScheduleFlag;

   // @Scheduled(fixedDelayString = "8000")
    public void prepareBookingDataJob() {
        log.info("Starting Scheduled Task for Create Booking Email...");

        if (!bookingDataScheduleFlag) {
            log.info("Booking schedule cron flag is false. Skipping execution.");
            return;
        }

        try {
            syncBookingsFromCultDataApi(1000,null,null);

        } catch (DataNotFoundException e) {
            log.error("Data not found for Booking : {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error in Booking  scheduler: {}", e.getMessage(), e);
        } finally {
            log.info("Booking Scheduled Task finished (success or error).");
        }
    }

    public void syncBookingsFromCultDataApi(int limit ,String startDateString, String endDateString) {
        log.info("Sync Bookings Started...");
        if(startDateString == null || endDateString == null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate startDate = currentDate.minusDays(2);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDateString = startDate.format(formatter);
            endDateString = currentDate.format(formatter);
        }

        List<BookingReport> bookingReportList = cultDataBookingService.prepareBookingData(limit, startDateString, endDateString);
        log.info("Fetched {} booking records.", bookingReportList.size());


        if(bookingReportList != null) {
            bookingReportWriteService.saveBookingReports(bookingReportList);
        }
        log.info("Sync Bookings Completed...");
    }

}