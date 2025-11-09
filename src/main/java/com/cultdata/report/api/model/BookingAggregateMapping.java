package com.cultdata.report.api.model;

import jakarta.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "BookingAggregateMapping",
        classes = @ConstructorResult(
                targetClass = com.cultdata.report.api.common.result.BookingAggregateReportDTO.class,
                columns = {
                        @ColumnResult(name = "client_id", type = Long.class),
                        @ColumnResult(name = "client_name", type = String.class),
                        @ColumnResult(name = "country_id", type = Integer.class),
                        @ColumnResult(name = "country_name", type = String.class),
                        @ColumnResult(name = "business_unit_id", type = Integer.class),
                        @ColumnResult(name = "business_unit_name", type = String.class),
                        @ColumnResult(name = "distribution_manager_id", type = Integer.class),
                        @ColumnResult(name = "distribution_manager_name", type = String.class),
                        @ColumnResult(name = "capacity", type = Integer.class),
                        @ColumnResult(name = "for_testing", type = Boolean.class),
                        @ColumnResult(name = "distinctChannelCount", type = Long.class),
                        @ColumnResult(name = "bookingNumberOfRoomNights", type = Integer.class),
                        @ColumnResult(name = "bookingCount", type = Long.class),
                        @ColumnResult(name = "bookingGbv", type = Double.class),
                        @ColumnResult(name = "cancellationNumberOfRoomNights", type = Integer.class),
                        @ColumnResult(name = "cancellationCount", type = Long.class),
                        @ColumnResult(name = "cancellationGbv", type = Double.class),
                        @ColumnResult(name = "currency", type = String.class)
                }
        )
)
public class BookingAggregateMapping {
    @Id
    private Long id;

    // This class is only for holding the @SqlResultSetMapping annotation
    // No additional fields or methods needed
}
