package com.cultdata.report.api.base;

import com.cultdata.report.api.common.responsedto.DistributionManagerDTO;
import com.cultdata.report.api.common.responsedto.BusinessUnitDto;
import com.cultdata.report.api.common.responsedto.ChannelDto;
import com.cultdata.report.api.common.responsedto.CountryDto;
import com.cultdata.report.api.common.responsedto.SupplierDto;
import com.cultdata.report.api.integration.CultDataRestClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BaseService implements IBaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    private final CultDataRestClient cultDataClient;

    public List<DistributionManagerDTO> fetchDistributionManagers(Boolean onlyMapped) {
        List<DistributionManagerDTO> managers = cultDataClient.getCacheDistributionManagers(onlyMapped);
        logger.info("fetchDistributionManagers retrieved: count = {}", managers.size());
        return managers;
    }

    public List<CountryDto> fetchCountryList() {
        List<CountryDto> countryList = cultDataClient.getCountryList();
        logger.info("fetchCountryList retrieved: count = {}", countryList.size());
        return countryList;
    }

    public List<BusinessUnitDto> fetchBusinessUnitList() {
        List<BusinessUnitDto> businessUnitList = cultDataClient.getBusinessUnitList();
        logger.info("fetchCountryList retrieved: count = {}", businessUnitList.size());
        return businessUnitList;
    }

    public List<ChannelDto> fetchDistributorChannelList() {
        List<ChannelDto> distributorChannelList = cultDataClient.getDistributorChannelList();
        logger.info("fetchDistributorChannelList retrieved: count = {}", distributorChannelList.size());
        return distributorChannelList;
    }

    public List<SupplierDto> fetchSupplierList() {
        String partnerType="SUPPLIER";
        List<SupplierDto> supplierList = cultDataClient.getSupplierList(partnerType);

        if (supplierList != null) {
            supplierList.forEach(supplier -> {
                if (supplier.getCultSwitchPartnerID() != null) {
                    supplier.setId(Long.valueOf(supplier.getCultSwitchPartnerID()));
                }
            });
        }
        logger.info("fetchSupplierList retrieved: count = {}", supplierList.size());
        return supplierList;
    }

}
