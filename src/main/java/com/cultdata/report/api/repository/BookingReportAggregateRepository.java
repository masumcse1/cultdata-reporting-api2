package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingAggregateReportDTO;
import com.cultdata.report.api.common.result.BookingReportPage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingReportAggregateRepository implements IBookingReportAggregateRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BookingReportPage getBookingReportData(BookingReportFilterDto filter) {

        String sql = buildNativeQuery(filter);
        Query nativeQuery = entityManager.createNativeQuery(sql, "BookingAggregateMapping");

        setQueryParameters(nativeQuery, filter);

        // ✅ Pagination setup
        int offset = filter.getPage() * filter.getSize();
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(filter.getSize());

        // ✅ Fetch paginated result
        List<BookingAggregateReportDTO> bookingAggregateReportResult = nativeQuery.getResultList();

        // ✅ Get total count using same query (without pagination)
        String countSql = "SELECT COUNT(*) FROM (" + sql + ") AS total_count_query";
        Query countQuery = entityManager.createNativeQuery(countSql);
        setQueryParameters(countQuery, filter);

        // ✅ Handle Long to int conversion safely
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();
        long totalPages = (long) Math.ceil((double) totalRecords / filter.getSize());

        // ✅ Prepare response
        BookingReportPage bookingReportPage = new BookingReportPage();
        bookingReportPage.setContent(bookingAggregateReportResult);
        bookingReportPage.setTotalRecords(totalRecords);
        bookingReportPage.setTotalPages(totalPages);

        return bookingReportPage;
    }


    /*@Override
    public BookingReportPage getBookingReportData(BookingReportFilterDto filter) {

        String sql = buildNativeQuery(filter);
        Query nativeQuery = entityManager.createNativeQuery(sql, "BookingAggregateMapping");

        setQueryParameters(nativeQuery, filter);

        int offset = filter.getPage() * filter.getSize();
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(filter.getSize());

        List<BookingAggregateReportDTO> totalResult =  nativeQuery.getResultList();

        List<BookingAggregateReportDTO> bookingAggregateReportResult = nativeQuery.getResultList();
        BookingReportPage bookingReportPage = new BookingReportPage();
        bookingReportPage.setContent(bookingAggregateReportResult);
        bookingReportPage.setTotalRecords(totalResult.size());
        bookingReportPage.setTotalPages(10);

        return bookingReportPage;
    }*/


    /*@Override
    public List<BookingAggregateReportDTO> getBookingReportData(BookingReportFilterDto filter) {

        String sql = buildNativeQuery(filter);
        Query nativeQuery = entityManager.createNativeQuery(sql, "BookingAggregateMapping");

        setQueryParameters(nativeQuery, filter);

        return nativeQuery.getResultList();
    }*/

    private String buildNativeQuery(BookingReportFilterDto filter) {

        return
                "SELECT " +
                        "    client_id, client_name, country_id, country_name, " +
                        "    business_unit_id, business_unit_name, " +
                        "    distribution_manager_id, distribution_manager_name, " +
                        "    capacity, for_testing, currency, " +
                        "    SUM(distinctChannelCount) as distinctChannelCount, " +
                        "    SUM(bookingNumberOfRoomNights) as bookingNumberOfRoomNights, " +
                        "    SUM(bookingCount) as bookingCount, " +
                        "    SUM(bookingGbv) as bookingGbv, " +
                        "    SUM(cancellationNumberOfRoomNights) as cancellationNumberOfRoomNights, " +
                        "    SUM(cancellationCount) as cancellationCount, " +
                        "    SUM(cancellationGbv) as cancellationGbv " +
                        "FROM ( " +
                        buildBookingSubquery(filter) +
                        "    UNION ALL " +
                        buildCancellationSubquery(filter) +
                        ") combined " +
                        "GROUP BY " +
                        "    client_id, client_name, country_id, country_name, " +
                        "    business_unit_id, business_unit_name, " +
                        "    distribution_manager_id, distribution_manager_name, " +
                        "    capacity, for_testing, currency " +
                        "ORDER BY client_id";
    }

    private String buildBookingSubquery(BookingReportFilterDto filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("br.client_id, br.client_name, br.country_id, br.country_name, ")
                .append("br.business_unit_id, br.business_unit_name, ")
                .append("br.distribution_manager_id, br.distribution_manager_name, ")
                .append("br.capacity, br.for_testing, ")
                .append("COUNT(DISTINCT br.channel_id) as distinctChannelCount, ")
                .append("SUM(br.number_of_room_nights) as bookingNumberOfRoomNights, ")
                .append("COUNT(br.booking_id) as bookingCount, ")
                .append("SUM(br.gbv) as bookingGbv, ")
                .append("0 as cancellationNumberOfRoomNights, 0 as cancellationCount, 0 as cancellationGbv, ")
                .append("br.currency ")
                .append("FROM booking_report br ")
                .append(buildWhereClause(filter, "booking"))
                .append(" GROUP BY ")
                .append("br.client_id, br.client_name, br.country_id, br.country_name, ")
                .append("br.business_unit_id, br.business_unit_name, ")
                .append("br.distribution_manager_id, br.distribution_manager_name, ")
                .append("br.capacity, br.for_testing, br.currency ");
        return sb.toString();
    }

    private String buildCancellationSubquery(BookingReportFilterDto filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("br.client_id, br.client_name, br.country_id, br.country_name, ")
                .append("br.business_unit_id, br.business_unit_name, ")
                .append("br.distribution_manager_id, br.distribution_manager_name, ")
                .append("br.capacity, br.for_testing, ")
                .append("COUNT(DISTINCT br.channel_id) as distinctChannelCount, ")
                .append("0 as bookingNumberOfRoomNights, 0 as bookingCount, 0 as bookingGbv, ")
                .append("SUM(br.number_of_room_nights) as cancellationNumberOfRoomNights, ")
                .append("COUNT(br.booking_id) as cancellationCount, ")
                .append("SUM(br.gbv) as cancellationGbv, ")
                .append("br.currency ")
                .append("FROM booking_report br ")
                .append(buildWhereClause(filter, "cancellation"))
                .append(" GROUP BY ")
                .append("br.client_id, br.client_name, br.country_id, br.country_name, ")
                .append("br.business_unit_id, br.business_unit_name, ")
                .append("br.distribution_manager_id, br.distribution_manager_name, ")
                .append("br.capacity, br.for_testing, br.currency ");
        return sb.toString();
    }

    private String buildWhereClause(BookingReportFilterDto filter, String type) {
        List<String> conditions = new ArrayList<>();

        if ("booking".equals(type) && filter.getStartBookingDate() != null && filter.getEndBookingDate() != null) {
            conditions.add("br.booking_date BETWEEN :startDate AND :endDate");
        } else if ("cancellation".equals(type) && filter.getStartBookingDate() != null && filter.getEndBookingDate() != null) {
            conditions.add("br.cancellation_date BETWEEN :startDate AND :endDate");
        }

        if (conditions.isEmpty()) {
            return ""; // no WHERE clause if no dates provided
        }

        return " WHERE " + String.join(" AND ", conditions);
    }

    private void setQueryParameters(Query query, BookingReportFilterDto filter) {
        if (filter.getStartBookingDate() != null && filter.getEndBookingDate() != null) {
            query.setParameter("startDate", filter.getStartBookingDate());
            query.setParameter("endDate", filter.getEndBookingDate());
        }
    }
}
