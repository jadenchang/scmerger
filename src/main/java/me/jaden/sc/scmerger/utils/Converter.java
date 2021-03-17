package me.jaden.sc.scmerger.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public LocalDate strToDate(String str){
        if(StringUtils.isBlank(str)) return null;
        return LocalDate.parse(str, dateFormatter);
    }

    public Long strToLong(String str){
        if(StringUtils.isBlank(str)) return null;
        else return Long.valueOf(str);
    }


}
