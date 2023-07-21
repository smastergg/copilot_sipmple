package com.gitbub.copilot.simple.holiday.controller;

import com.gitbub.copilot.simple.holiday.service.HolidayService;
import com.gitbub.copilot.simple.holiday.vo.HolidayResultVO;
import com.gitbub.copilot.simple.holiday.vo.HolidayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController(value = "/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    //write a restful method that add new holidays to the CVS file
    @PostMapping(value = "addHolidays")
    public boolean addHolidays(List<HolidayVO> holidays){
        // validate the holidays
        validateHolidays(holidays);
        //write the logic to add holidays to the CVS file
        return holidayService.addHolidays(holidays);
    }

    /**
     * This method validate the holidays
     * @param holidays
     * @return
     */
    private boolean validateHolidays(List<HolidayVO> holidays) {
        //write the logic to validate the holidays
        if(holidays == null || holidays.isEmpty()){
            return false;
        }
        for(HolidayVO holiday: holidays){
            if (holiday.getCountryCode() == null || holiday.getCountryCode().isEmpty() ||holiday.getCountryDesc() == null
                    || holiday.getCountryDesc().isEmpty() || holiday.getHolidayDate() == null
                    || holiday.getHolidayDate().isEmpty() || holiday.getHolidayName() == null || holiday.getHolidayName().isEmpty()){
                return false;
            }
            if( !checkDateFormat(holiday.getHolidayDate())){
                return false;
            }
        }
        return true;
    }


        /**
         * this method check the date format
         * @param holidayDate
         * return boolean
         */
        private boolean checkDateFormat(String holidayDate){
            //write the logic to check the date format
            try{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormat.setLenient(false);
                simpleDateFormat.parse(holidayDate);
            }catch (Exception e){
                return false;
            }
            return true;
        }
    /**
     * API1
     * this method is used to select the next years holidays
     * return List<HolidayVO>
     */
    @GetMapping(value = "selectNextYearsHolidays")
    public List<HolidayVO> selectNextYearsHolidays(String countryCode){
        //write the logic to select the next years holidays
        LocalDate nowDate= LocalDate.now();
        return holidayService.readDataByCondition(nowDate,nowDate.plusYears(1),countryCode);
    }

    /**
     * API2
     * this method is used to select the next holiday
     * return List<HolidayVO>
     */
    @GetMapping(value = "selectNextHolidays")
    public HolidayVO selectNextHolidays(String countryCode){
        //write the logic to select the next years holidays
        LocalDate nowDate= LocalDate.now();
        return holidayService.readNextHoliday(nowDate,countryCode);
    }


    /**
     * API3
     * this method is used to check the data is holiday or bot
     * @param date
     * return boolean
     */
    @GetMapping(value = "checkHoliday")
    public List<HolidayResultVO> checkHoliday(String date, String countryCode){
        //write the logic to check the data is holiday or bot
        //read all country in the CSV file
        List<String> countryCodes=holidayService.getAllCountryCodes();
        List<HolidayResultVO> holidayResultVOs = new ArrayList<>();
        for (String code: countryCodes){
            HolidayVO vo=new HolidayVO();
            vo.setHolidayDate(date);
            vo.setCountryCode(code);
            HolidayResultVO resultVO=new HolidayResultVO();
            HolidayVO fullVO=holidayService.checkHolidayExist(vo);
            if (fullVO!=null && fullVO.getHolidayDate()!=null && !fullVO.getHolidayDate().isEmpty()){
                resultVO.setIsHoliday(true);
                resultVO.setHolidayDate(fullVO.getHolidayDate());
                resultVO.setCountryCode(fullVO.getCountryCode());
                resultVO.setHolidayName(fullVO.getHolidayName());
                holidayResultVOs.add(resultVO);
         }
        }
        return holidayResultVOs;
    }
}
