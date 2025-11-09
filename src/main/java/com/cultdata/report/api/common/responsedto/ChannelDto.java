package com.cultdata.report.api.common.responsedto;

import lombok.Data;

@Data
public class ChannelDto {
    private Integer id;
    private String name;
    private String channelType;

    public ChannelDto() {
    }
}