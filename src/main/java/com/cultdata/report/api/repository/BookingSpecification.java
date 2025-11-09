package com.cultdata.report.api.repository;

import com.cultdata.report.api.common.dto.BookingReportFilterDto;
import com.cultdata.report.api.model.BookingReport;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

public class BookingSpecification {

    public static Specification<BookingReport> buildBookingCriteriaList(BookingReportFilterDto filter) {

        return (Root<BookingReport> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStartBookingDate() != null && filter.getEndBookingDate() != null) {
                predicates.add(builder.between(root.get("bookingDate"),
                        filter.getStartBookingDate(), filter.getEndBookingDate()));
            }

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("clientId"), filter.getClientId()));
            }

            if (!CollectionUtils.isEmpty(filter.getCountryIds())) {
                predicates.add(root.get("countryId").in(filter.getCountryIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getBusinessUnitIds())) {
                predicates.add(root.get("businessUnitId").in(filter.getBusinessUnitIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getDistributionManagerIds())) {
                predicates.add(root.get("distributionManagerId").in(filter.getDistributionManagerIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getChannelIds())) {
                predicates.add(root.get("channelId").in(filter.getChannelIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getSupplierIds())) {
                predicates.add(root.get("supplierId").in(filter.getSupplierIds()));
            }

            if (Boolean.TRUE.equals(filter.getExcludeTestProperties())) {
                predicates.add(builder.isFalse(root.get("forTesting")));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<BookingReport> buildCancelCriteriaList(BookingReportFilterDto filter) {

        return (Root<BookingReport> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStartBookingDate() != null && filter.getEndBookingDate() != null) {
                predicates.add(builder.between(root.get("cancellationDate"),
                        filter.getStartBookingDate(), filter.getEndBookingDate()));
            }

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("clientId"), filter.getClientId()));
            }

            if (!CollectionUtils.isEmpty(filter.getCountryIds())) {
                predicates.add(root.get("countryId").in(filter.getCountryIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getBusinessUnitIds())) {
                predicates.add(root.get("businessUnitId").in(filter.getBusinessUnitIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getDistributionManagerIds())) {
                predicates.add(root.get("distributionManagerId").in(filter.getDistributionManagerIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getChannelIds())) {
                predicates.add(root.get("channelId").in(filter.getChannelIds()));
            }

            if (!CollectionUtils.isEmpty(filter.getSupplierIds())) {
                predicates.add(root.get("supplierId").in(filter.getSupplierIds()));
            }

            if (Boolean.TRUE.equals(filter.getExcludeTestProperties())) {
                predicates.add(builder.isFalse(root.get("forTesting")));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
