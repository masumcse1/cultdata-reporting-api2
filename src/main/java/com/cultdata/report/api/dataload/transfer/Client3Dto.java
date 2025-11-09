package com.cultdata.report.api.dataload.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client3Dto {
    private Integer id;
    private String name;
    private String street;
    private String zipCode;
    private String place;
    private Integer country;
    private Integer capacity;
    private String contractSignedDate;
    private String contractTerminationDate;
    private Integer distributionManagerIdCultSwitch;
    private String cultSwitchDMFromDate;
    private Integer isTest;

}
