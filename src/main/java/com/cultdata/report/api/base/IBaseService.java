package com.cultdata.report.api.base;



import com.cultdata.report.api.common.responsedto.DistributionManagerDTO;
import com.cultdata.report.api.common.responsedto.BusinessUnitDto;
import com.cultdata.report.api.common.responsedto.ChannelDto;
import com.cultdata.report.api.common.responsedto.CountryDto;
import com.cultdata.report.api.common.responsedto.SupplierDto;

import java.util.List;

public interface IBaseService {
    List<DistributionManagerDTO> fetchDistributionManagers(Boolean onlyMapped);
    public List<CountryDto> fetchCountryList();
    List<BusinessUnitDto> fetchBusinessUnitList();
    public List<ChannelDto> fetchDistributorChannelList();
    List<SupplierDto> fetchSupplierList();
}
