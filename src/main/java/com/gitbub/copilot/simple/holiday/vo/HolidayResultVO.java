package com.gitbub.copilot.simple.holiday.vo;

import lombok.Data;

@Data
public class HolidayResultVO {

    private String CountryCode;
    private String HolidayName;
    private String HolidayDate;
    private String CountryDesc;
    private Boolean isHoliday;
}
