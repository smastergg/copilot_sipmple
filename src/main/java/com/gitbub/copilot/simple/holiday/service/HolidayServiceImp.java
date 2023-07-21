package com.gitbub.copilot.simple.holiday.service;


import com.gitbub.copilot.simple.holiday.constant.HolidayConstants;
import com.gitbub.copilot.simple.holiday.vo.HolidayVO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayServiceImp implements HolidayService{




    @Override
    public boolean addHolidays(List<HolidayVO> holidays) {
        //write the logic to add holidays to the CSV file
        return saveData(holidays);
    }

    /**
     * this method is used to save data into the CSV file
     * @param holidays
     * return boolean
     */
    private boolean saveData(List<HolidayVO> holidays)  {
        //write the logic to save data into the CSV file
        ClassPathResource resource = new ClassPathResource(HolidayConstants.CSV_PATH);
        File CSVFile = null;
        try {
            CSVFile = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileOutputStream out;
        OutputStreamWriter osw;
        try{
            out = new FileOutputStream(CSVFile);
            osw = new OutputStreamWriter(out,"UTF-8");
            osw.write("CountryCode,HolidayName,HolidayDate,CountryDesc");
            osw.write("\r\n");
            for(HolidayVO holiday: holidays){
                osw.write(holiday.getCountryCode()+","+holiday.getHolidayName()+","+holiday.getHolidayDate()+","+holiday.getCountryDesc());
                osw.write("\r\n");
            }
            osw.flush();
            osw.close();
            out.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    /**
     * this method is used to read country codes in the CSV file
     * return List<String>
     */
    public List<String> getAllCountryCodes(){
        //write the logic to read country codes in the CSV file
        List<HolidayVO> holidays = readData();
        List<String> countryCodes = new ArrayList<>();
        for(HolidayVO holiday: holidays){
            if(!countryCodes.contains(holiday.getCountryCode())){
                countryCodes.add(holiday.getCountryCode());
            }
        }
        return countryCodes;
    }

    /**
     * this method is used to read data in the CSV file
     * return List<HolidayVO>
     */
    private List<HolidayVO> readData(){
        //write the logic to read data in the CSV file
        File CSVFile = new File(HolidayConstants.CSV_PATH);
        FileInputStream in;
        InputStreamReader isr;
        BufferedReader br;
        List<HolidayVO> holidays = new ArrayList<>();
        try{
            in = new FileInputStream(CSVFile);
            isr = new InputStreamReader(in,"UTF-8");
            br = new BufferedReader(isr);
            String line = "";
            String[] arrs = null;
            while ((line = br.readLine()) != null){
                arrs = line.split(",");
                HolidayVO holiday = new HolidayVO();
                holiday.setCountryCode(arrs[0]);
                holiday.setHolidayName(arrs[1]);
                holiday.setHolidayDate(arrs[2]);
                holiday.setCountryDesc(arrs[3]);
                holidays.add(holiday);
            }
            br.close();
            isr.close();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return holidays;
    }


    /**
     * this method is used to read specific data in the CSV file
     * @Param beginDate
     * @Param endDate
     * @Param countryCode
     * return List<HolidayVO>
     */
    public List<HolidayVO> readDataByCondition(LocalDate beginDate, LocalDate endDate,String countryCode) {
        //write the logic to read specific data in the CSV file
        List<HolidayVO> holidayVOS = readData();
        for (HolidayVO vo : holidayVOS) {
            try {
                LocalDate date = LocalDate.parse(vo.getHolidayDate());
                if (date.isBefore(beginDate) || date.isAfter(endDate)) {
                    holidayVOS.remove(vo);
                }
                if(countryCode != null && !countryCode.isEmpty() && !countryCode.equals(vo.getCountryCode())){
                    holidayVOS.remove(vo);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return holidayVOS;
    }


    /**
     * this method is used to read next holiday in the CSV file
     * @Param beginDate
     * @Param countryCode
     * return List<HolidayVO>
     */
    public HolidayVO readNextHoliday(LocalDate beginDate,String countryCode) {
        //write the logic to read specific data in the CSV file
        List<HolidayVO> holidayVOS = readData();
        for (HolidayVO vo : holidayVOS) {
            try {
                LocalDate date = LocalDate.parse(vo.getHolidayDate());
                if (date.isBefore(beginDate)) {
                    holidayVOS.remove(vo);
                }
                if(countryCode != null && !countryCode.isEmpty() && !countryCode.equals(vo.getCountryCode())){
                    holidayVOS.remove(vo);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        if (holidayVOS == null || holidayVOS.isEmpty()){
            return null;
        }
        return holidayVOS.get(0);
    }







    /**
     * this method is used to check the holiday is exist or not in CSV file
     * @param holiday
     * return boolean
     */
    public HolidayVO checkHolidayExist(HolidayVO holiday){
        //write the logic to check the holiday is exist or not
        List<HolidayVO> holidays = readData();
        if (holidays == null || holidays.isEmpty()){
            return null;
        }

        if(holidays.contains(holiday))
        {
            return holiday;
        }
        return null;
    }


}
