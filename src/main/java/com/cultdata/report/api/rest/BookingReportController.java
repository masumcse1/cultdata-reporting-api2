package com.cultdata.report.api.rest;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingAggregateReportDTO;
import com.cultdata.report.api.common.result.BookingReportPage;
import com.cultdata.report.api.common.result.BookingsPage;
import com.cultdata.report.api.service.BookingReportReadService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Booking Report", description = "The Booking Report API.")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BookingReportController {

    @Autowired
    private BookingReportReadService service;


    @Operation(summary = "Retrieve Paginated Booking Report", description = "A X-API-KEY is required to access this API...")
    @GetMapping(value = "/booking-report", produces = "application/json")
    public ResponseEntity<BookingReportPage> getAggregateBookingReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBookingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBookingDate,
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) List<Integer> countryIds,
            @RequestParam(required = false) List<Integer> businessUnitIds,
            @RequestParam(required = false) List<Integer> distributionManagerIds,
            @RequestParam(required = false) Boolean excludeTestProperties,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        log.info("GET /api/v1/booking-report called");

        BookingReportFilterDto filter = new BookingReportFilterDto();
        filter.setStartBookingDate(startBookingDate.atStartOfDay());
        filter.setEndBookingDate(endBookingDate.atTime(23, 59, 59));
        filter.setClientId(clientId);
        filter.setCountryIds(countryIds);
        filter.setBusinessUnitIds(businessUnitIds);
        filter.setDistributionManagerIds(distributionManagerIds);
        filter.setExcludeTestProperties(excludeTestProperties);
        filter.setPage(page);
        filter.setSize(size);

        BookingReportPage result = service.getAggregatedBookingReportPage(filter);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Retrieve Paginated Booking Report Data", description = "A X-API-KEY is required to access this API...")
    @GetMapping(value = "/booking-report-page", produces = "application/json")
    public ResponseEntity<BookingReportPage> getBookingReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBookingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBookingDate,
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) List<Integer> countryIds,
            @RequestParam(required = false) List<Integer> businessUnitIds,
            @RequestParam(required = false) List<Integer> distributionManagerIds,
            @RequestParam(required = false) Boolean excludeTestProperties,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        log.info("GET /api/v1/booking-report called");

        BookingReportFilterDto filter = new BookingReportFilterDto();
        filter.setStartBookingDate(startBookingDate.atStartOfDay());
        filter.setEndBookingDate(endBookingDate.atTime(23, 59, 59));
        filter.setClientId(clientId);
        filter.setCountryIds(countryIds);
        filter.setBusinessUnitIds(businessUnitIds);
        filter.setDistributionManagerIds(distributionManagerIds);
        filter.setExcludeTestProperties(excludeTestProperties);
        filter.setPage(page);
        filter.setSize(size);

        BookingReportPage result = service.getBookingReportsWitPage(filter);
        return ResponseEntity.ok(result);
    }


    // Equivalent with Booking Report api with all page data .
    @Hidden
    @Operation(summary = "Retrieve Booking Report Data", description = "A X-API-KEY is required to access this API...")
    @GetMapping(value = "/bookingReportData", produces = "application/json")
    public ResponseEntity<List<BookingAggregateReportDTO>> getAggregatedBookingReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBookingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBookingDate,
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) List<Integer> countryIds,
            @RequestParam(required = false) List<Integer> businessUnitIds,
            @RequestParam(required = false) List<Integer> distributionManagerIds,
            @RequestParam(required = false) Boolean excludeTestProperties) {

        BookingReportFilterDto filter = new BookingReportFilterDto();

        filter.setStartBookingDate(startBookingDate.atStartOfDay());
        filter.setEndBookingDate(endBookingDate.atTime(23, 59, 59));
        filter.setClientId(clientId);
        filter.setCountryIds(countryIds);
        filter.setBusinessUnitIds(businessUnitIds);
        filter.setDistributionManagerIds(distributionManagerIds);
        filter.setExcludeTestProperties(excludeTestProperties);

        List<BookingAggregateReportDTO> result = service.getAggregatedBookingReport(filter);
        return ResponseEntity.ok(result);
    }


    // Equivalent with CultData Booking api
    @Hidden
    @GetMapping(path = "/bookings", produces = "application/json")
    public ResponseEntity<BookingsPage> getBookingsInformation(
            @RequestParam(name = "limit", defaultValue = "100", required = false) Integer limit,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "client_id", required = false) Integer clientId,
            @RequestParam(name = "country_id", required = false) List<Integer> countryIds,
            @RequestParam(name = "business_unit_id", required = false) List<Integer> businessUnitIds,
            @RequestParam(name = "distribution_manager_id", required = false) List<Integer> distributionManagerIds,
            @RequestParam(name = "channel_id", required = false) List<Integer> channelIds,
            @RequestParam(name = "supplier_id", required = false) List<Integer> supplierIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBookingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBookingDate,
            @RequestParam(name = "exclude_test_properties", required = false, defaultValue = "true") Boolean excludeTestProperties
    )  {

        log.info("GET /booking-report called with page={}, limit={}, clientId={}", page, limit, clientId);

        BookingReportFilterDto filter = new BookingReportFilterDto();
        filter.setClientId(clientId);
        filter.setCountryIds(countryIds);
        filter.setBusinessUnitIds(businessUnitIds);
        filter.setDistributionManagerIds(distributionManagerIds);
        filter.setChannelIds(channelIds);
        filter.setSupplierIds(supplierIds);
        filter.setExcludeTestProperties(excludeTestProperties);

        filter.setStartBookingDate(startBookingDate.atStartOfDay());
        filter.setEndBookingDate(endBookingDate.atTime(23, 59, 59));
        filter.setSize(limit);
        filter.setPage(page);

        BookingsPage bookingsInformation = service.getBookingsInformation(filter);
        return ResponseEntity.ok(bookingsInformation);
    }

    // Equivalent with CultData cancellation api
    @Hidden
    @GetMapping(path = "/cancellations", produces = "application/json")
    public ResponseEntity<BookingsPage> getCancellationsInformation(
            @RequestParam(name = "limit", defaultValue = "100", required = false) Integer limit,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "client_id", required = false) Integer clientId,
            @RequestParam(name = "country_id", required = false) List<Integer> countryIds,
            @RequestParam(name = "business_unit_id", required = false) List<Integer> businessUnitIds,
            @RequestParam(name = "distribution_manager_id", required = false) List<Integer> distributionManagerIds,
            @RequestParam(name = "channel_id", required = false) List<Integer> channelIds,
            @RequestParam(name = "supplier_id", required = false) List<Integer> supplierIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBookingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBookingDate,
            @RequestParam(name = "exclude_test_properties", required = false, defaultValue = "true") Boolean excludeTestProperties
    )  {

        log.info("GET /booking-report called with page={}, limit={}, clientId={}", page, limit, clientId);

        BookingReportFilterDto filter = new BookingReportFilterDto();
        filter.setClientId(clientId);
        filter.setCountryIds(countryIds);
        filter.setBusinessUnitIds(businessUnitIds);
        filter.setDistributionManagerIds(distributionManagerIds);
        filter.setChannelIds(channelIds);
        filter.setSupplierIds(supplierIds);
        filter.setExcludeTestProperties(excludeTestProperties);

        filter.setStartBookingDate(startBookingDate.atStartOfDay());
        filter.setEndBookingDate(endBookingDate.atTime(23, 59, 59));
        filter.setSize(limit);
        filter.setPage(page);

        BookingsPage reportPage = service.getCancelInformation(filter);
        return ResponseEntity.ok(reportPage);
    }

}

//http://localhost:8084/api/v1/booking-report?startBookingDate=2025-11-03&endBookingDate=2025-11-09&page=0&size=100

//http://localhost:8084/api/v1/booking-report-page?startBookingDate=2025-11-01&endBookingDate=2025-11-09&page=0&size=100

//http://localhost:8084/api/v1/bookingReportData?startBookingDate=2025-11-03&endBookingDate=2025-11-09

//http://localhost:8084/api/v1/bookings?startBookingDate=2025-11-03&endBookingDate=2025-11-07&page=0&limit=8000

//http://localhost:8084/api/v1/cancellations?startBookingDate=2025-11-03&endBookingDate=2025-11-07&page=0&limit=8000

//http://localhost:8084/swagger-ui/index.html