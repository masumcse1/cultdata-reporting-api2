package com.cultdata.report.api.common.responsedto;

import lombok.Data;

import java.util.Set;

@Data
public class SupplierDto {

    private Long id;
    private String name;
    private String cultSwitchPartnerID;
    private Long channelId;
    private Set<String> partnersTypes;

    public SupplierDto() {
    }
}