CREATE TABLE IF NOT EXISTS public.booking_report
(
    booking_id bigint NOT NULL,
    client_id bigint,
    client_name character varying(255),
    country_id integer,
    country_name character varying(255),
    business_unit_id integer,
    business_unit_name character varying(255),
    distribution_manager_id integer,
    distribution_manager_name character varying(255),
    capacity integer,
    for_testing boolean,
    supplier_id integer,
    supplier_name character varying(255),
    channel_id integer,
    booking_date timestamp without time zone,
    number_of_room_nights integer,
    cancellation boolean,
    cancellation_date timestamp without time zone,
    gbv decimal(10,2),
    currency character varying(10),
    CONSTRAINT booking_report_pkey PRIMARY KEY (booking_id)
);