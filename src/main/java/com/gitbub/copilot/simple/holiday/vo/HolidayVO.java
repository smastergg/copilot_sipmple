package com.gitbub.copilot.simple.holiday.vo;

import lombok.Data;

import javax.naming.ldap.PagedResultsControl;

@Data
public class HolidayVO {

    private String CountryCode;
    private String HolidayName;
    private String HolidayDate;
    private String CountryDesc;
}
