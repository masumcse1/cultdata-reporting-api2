package com.cultdata.report.api.dataload.service;

import com.cultdata.report.api.base.BaseService;
import com.cultdata.report.api.common.responsedto.BusinessUnitDto;
import com.cultdata.report.api.common.responsedto.ChannelDto;
import com.cultdata.report.api.common.responsedto.CountryDto;
import com.cultdata.report.api.common.responsedto.DistributionManagerDTO;
import com.cultdata.report.api.dataload.restclient.CultDataApiBookingRestClient;
import com.cultdata.report.api.dataload.restclient.CultDataApiClientRestClient;
import com.cultdata.report.api.dataload.transfer.BookingDto;
import com.cultdata.report.api.dataload.transfer.Client3Dto;
import com.cultdata.report.api.model.BookingReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CultDataBookingService  {

    private final CultDataApiBookingRestClient bookingRestClient;

    private final CultDataApiClientRestClient clientRestClient;

    private final BaseService iBaseService;


    public List<BookingReport> prepareBookingData(Integer limit, String fromDate, String toDate){

        List<BookingReport> bookingReportList = new ArrayList<>();

        List<BookingDto> allBookingPages = bookingRestClient.getAllBookingPages(limit, fromDate, toDate);

        List<Client3Dto> allClients      = clientRestClient.getAllClientPages(5000);

        List<CountryDto> countryDtos                         = iBaseService.fetchCountryList();
        List<BusinessUnitDto> businessUnitDtos               = iBaseService.fetchBusinessUnitList();
        List<ChannelDto> channelDtos                         = iBaseService.fetchDistributorChannelList();
        List<DistributionManagerDTO> distributionManagerDTOS = iBaseService.fetchDistributionManagers(true);

        for(BookingDto bookingDto : allBookingPages){

            Client3Dto client = getClientById(allClients, bookingDto.getClient().getId());
            CountryDto country = getCountryById(countryDtos, client.getCountry());
            DistributionManagerDTO distributionManager = getDistributionManager(distributionManagerDTOS, client.getDistributionManagerIdCultSwitch());
            BusinessUnitDto businessUnit = null;
            if (distributionManager != null) {
                businessUnit = getBusinessUnit(businessUnitDtos, distributionManager.getBusinessUnitId());
            }

            BookingReport bookingReport = new BookingReport();
            bookingReport.setClientId(bookingDto.getClient().getId().longValue());
            bookingReport.setClientName(client != null ? client.getName() : null);
            bookingReport.setCountryId(client != null && client.getCountry() != null  ? client.getCountry() : null);
            bookingReport.setCountryName(country != null ? country.getName(): null);
            bookingReport.setDistributionManagerId(distributionManager != null ? distributionManager.getId() : null);
            bookingReport.setDistributionManagerName(distributionManager != null ? distributionManager.getName() : null);
            bookingReport.setBusinessUnitId(businessUnit != null ? businessUnit.getId() : null);
            bookingReport.setBusinessUnitName(businessUnit != null ? businessUnit.getName() : null);

            bookingReport.setCapacity(client != null ? client.getCapacity() : null);
            bookingReport.setForTesting(client != null && client.getIsTest() != null ? client.getIsTest() == 1 : false);

            bookingReport.setChannelId(bookingDto.getChannel().getId());
            bookingReport.setBookingId(bookingDto.getId().longValue());
            bookingReport.setBookingDate(bookingDto.getBookingDate());
            bookingReport.setNumberOfRoomNights(bookingDto.getNumberOfRoomNights());
            bookingReport.setCancellation(bookingDto.getIsCancelled());
            bookingReport.setCancellationDate(bookingDto.getCancellationDate());
            bookingReport.setGbv(bookingDto.getGbv().doubleValue());
            bookingReport.setCurrency(bookingDto.getCurrency());

            bookingReportList.add(bookingReport);

        }

        return bookingReportList;
    }

   private   Client3Dto getClientById(List<Client3Dto> allClients, Integer clientId){
        return allClients.stream()
                .filter(c -> clientId.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }

    private   CountryDto getCountryById(  List<CountryDto> countryDtos, Integer countryId){

        if (countryId == null) {
            return null;
        }

        return countryDtos.stream()
                .filter(c -> countryId.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }

    private   DistributionManagerDTO getDistributionManager(  List<DistributionManagerDTO> distributionManagerDTOS, Integer dmId){

        if (dmId == null) {
            return null;
        }

        return distributionManagerDTOS.stream()
                .filter(c -> dmId.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }

    private   BusinessUnitDto getBusinessUnit(List<BusinessUnitDto> businessUnitDtos, Integer buId){

        if (buId == null) {
            return null;
        }
        return businessUnitDtos.stream()
                .filter(c -> buId.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }

}
