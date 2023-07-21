package com.gitbub.copilot.simple.holiday.service;

import com.gitbub.copilot.simple.holiday.vo.HolidayVO;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {
    boolean addHolidays(List<HolidayVO> holidays);
     List<HolidayVO> readDataByCondition(LocalDate beginDate, LocalDate endDate,String countryCode);

     HolidayVO readNextHoliday(LocalDate nowDate, String countryCode);

    List<String> getAllCountryCodes();

    HolidayVO checkHolidayExist(HolidayVO holiday);
}
