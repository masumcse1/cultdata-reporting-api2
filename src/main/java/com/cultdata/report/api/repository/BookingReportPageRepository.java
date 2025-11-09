package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.common.result.BookingAggregateReportDTO;
import com.cultdata.report.api.common.result.BookingReportPage;
import com.cultdata.report.api.model.BookingReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingReportPageRepository implements IBookingReportPageRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookingReportPage getAggregatedBookingReportPage(BookingReportFilterDto filter) {

        return null;
    }


    public BookingReportPage getBookingReportsWitPage(BookingReportFilterDto filter) {
        Specification<BookingReport> spec = BookingSpecification.buildBookingCriteriaList(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        // 1️⃣ Main Query
        CriteriaQuery<BookingAggregateReportDTO> query = builder.createQuery(BookingAggregateReportDTO.class);
        Root<BookingReport> root = query.from(BookingReport.class);
        Predicate predicates = spec.toPredicate(root, query, builder);

        List<Expression<?>> groupByFields = buildGroupByFields(root);

        query.multiselect(
                        root.get("clientId"),
                        root.get("clientName"),
                        root.get("countryId"),
                        root.get("countryName"),
                        root.get("businessUnitId"),
                        root.get("businessUnitName"),
                        root.get("distributionManagerId"),
                        root.get("distributionManagerName"),
                        root.get("capacity"),
                        root.get("forTesting"),
                        builder.countDistinct(root.get("channelId")),
                        builder.sum(root.get("numberOfRoomNights")),
                        builder.count(root.get("bookingId")),
                        builder.sum(root.get("gbv")),
                        root.get("currency")
                )
                .where(predicates)
                .groupBy(groupByFields)
                .orderBy(builder.asc(root.get("clientId")));

        // 2️⃣ Count Query (same predicate + groupBy)
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<BookingReport> countRoot = countQuery.from(BookingReport.class);
        Predicate countPredicate = spec.toPredicate(countRoot, countQuery, builder);

        countQuery.select(builder.countDistinct(countRoot.get("clientId")))
                .where(countPredicate)
                .groupBy(buildGroupByFields(countRoot));

        Long totalRecords = (long) entityManager.createQuery(countQuery).getResultList().size();
        int totalPages = (int) Math.ceil((double) totalRecords / filter.getSize());

        List<BookingAggregateReportDTO> content = entityManager.createQuery(query)
                .setFirstResult(filter.getPage() * filter.getSize())
                .setMaxResults(filter.getSize())
                .getResultList();

        BookingReportPage dto = new BookingReportPage();
        dto.setTotalPages(totalPages);
        dto.setTotalRecords(totalRecords.intValue());
        dto.setContent(content);

        return dto;
    }

    private List<Expression<?>> buildGroupByFields(Root<BookingReport> root) {
        return List.of(
                root.get("clientId"),
                root.get("clientName"),
                root.get("countryId"),
                root.get("countryName"),
                root.get("businessUnitId"),
                root.get("businessUnitName"),
                root.get("distributionManagerId"),
                root.get("distributionManagerName"),
                root.get("capacity"),
                root.get("forTesting"),
                root.get("currency")
        );
    }



    @Override
    public List<BookingAggregateReportDTO> getBookingReportsWithoutPage(BookingReportFilterDto filter) {

        Specification<BookingReport> spec = BookingSpecification.buildBookingCriteriaList(filter);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookingAggregateReportDTO> query = builder.createQuery(BookingAggregateReportDTO.class);
        Root<BookingReport> root = query.from(BookingReport.class);
        Predicate predicates = spec.toPredicate(root, query, builder);

        List<Selection<?>> selections = List.of(
                root.get("clientId"),
                root.get("clientName"),
                root.get("countryId"),
                root.get("countryName"),
                root.get("businessUnitId"),
                root.get("businessUnitName"),
                root.get("distributionManagerId"),
                root.get("distributionManagerName"),
                root.get("capacity"),
                root.get("forTesting"),
                builder.countDistinct(root.get("channelId")),
                builder.sum(root.get("numberOfRoomNights")),
                builder.count(root.get("bookingId")),
                builder.sum(root.get("gbv")),
                root.get("currency")
        );

        query.multiselect(selections.toArray(new Selection[0]))
                .where(predicates)
                .groupBy(
                        root.get("clientId"), root.get("clientName"),
                        root.get("countryId"), root.get("countryName"),
                        root.get("businessUnitId"), root.get("businessUnitName"),
                        root.get("distributionManagerId"), root.get("distributionManagerName"),
                        root.get("capacity"),
                        root.get("forTesting"),
                        root.get("currency")
                )
                .orderBy(builder.asc(root.get("clientId")));

        return entityManager.createQuery(query).getResultList();
    }

}