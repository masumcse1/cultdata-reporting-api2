package com.cultdata.report.api.repository;

import com.cultdata.report.api.model.BookingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


@Repository
public interface BookingReportRepository extends JpaRepository<BookingReport, Long>,
        JpaSpecificationExecutor<BookingReport> {
}
