package com.cultdata.report.api.dataload.restclient;

import com.cultdata.report.api.dataload.transfer.BookingDto;
import com.cultdata.report.api.dataload.transfer.BookingPage;
import com.cultdata.report.api.support.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CultDataApiBookingRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cultdata.api.key}")
    private String apiKey;

    @Value("${cultdata.api.base-url}")
    private String baseUrl;

    Logger logger = LoggerFactory.getLogger(CultDataApiBookingRestClient.class);

    public List<BookingDto> getAllBookingPages(Integer limit, String fromDate, String toDate) throws DataNotFoundException {

        int page = 1;
        BookingPage firstPage = getBookings(limit, page, fromDate, toDate);

        int totalPages = firstPage.getTotalPages() + 1;
        logger.info("Total pages in supplier API (Booking): " + totalPages);

        List<BookingDto> allBookings = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            BookingPage bookingPage = getBookings(limit, i, fromDate, toDate);
            if (bookingPage != null && bookingPage.getData() != null) {
                allBookings.addAll(bookingPage.getData());
            }
        }

        return allBookings;
    }


    public BookingPage getBookings(Integer limit, int page, String date_from, String date_to) {
        String apiUrl = baseUrl + "/api/bookings";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("limit", limit)
                .queryParam("page", page)
                .queryParam("start_booking_date", date_from)
                .queryParam("end_booking_date", date_to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<BookingPage> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    BookingPage.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch bookings from CultDataAPI", e);
        }
    }
}
