package com.westlakefinancial.phoenix.rmkt_transport.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ywang1 on 8/2/2014.
 *
 * Use to convert format of currency, date, phone numbers, etc.
 */

@ManagedBean(name="formatConverter")
@SessionScoped
public class FormatConverter {
    public String convertCurrency(String amount){
        if(!amount.matches("[0-9.]+")){
            return amount;
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(new BigDecimal(amount));
    }

    public String convertPhoneNumber(String num){
        if(num == null || num.equals("")){
            return "";
        }

        StringBuffer temp = new StringBuffer("");
        StringBuffer result = new StringBuffer("");
        for(char c : num.toCharArray()){
            switch(c){
                case '0': {
                    temp.append(c);
                    continue;
                }
                case '1': {
                    temp.append(c);
                    continue;
                }
                case '2': {
                    temp.append(c);
                    continue;
                }
                case '3': {
                    temp.append(c);
                    continue;
                }
                case '4': {
                    temp.append(c);
                    continue;
                }
                case '5': {
                    temp.append(c);
                    continue;
                }
                case '6': {
                    temp.append(c);
                    continue;
                }
                case '7': {
                    temp.append(c);
                    continue;
                }
                case '8': {
                    temp.append(c);
                    continue;
                }
                case '9': {
                    temp.append(c);
                    continue;
                }
                default:{
                    continue;
                }
            }
        }

        //check if the phone number is in right digits.
        if(temp.toString().equals("")){
            return num;
        }
        else if(temp.length() != 10){
            return temp.toString();
        }

        result.append("(");
        result.append(temp.substring(0, 3));
        result.append(") ");
        result.append(temp.substring(3, 6));
        result.append("-");
        result.append(temp.substring(6, 10));

        return result.toString();
    }

    public String convertSSN(String ssn) {
        StringBuffer result = new StringBuffer("");
        if(ssn.equals("NA") || ssn.equals("N/A") || ssn.equals("") || ssn == null){
            return ssn;
        }
        int lack = 9 - ssn.length();
        StringBuffer temp = new StringBuffer();
        for(int i = 0; i < lack; i++){
            temp.append("0");
        }
        temp.append(ssn);
        result.append(temp.substring(0, 3));
        result.append("-");
        result.append(temp.substring(3, 5));
        result.append("-");
        result.append(temp.substring(5));
        return result.toString();
    }

    // convert date from Date object.
    public String convertDate(Date originalDate, String resultFormat){
        if(originalDate == null) return "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(resultFormat);
        String result = dateFormat.format(originalDate);
        return result;
    }

    // convert date from Date object.
    public String convertContractDate(String contractDate)  throws Exception{
        if ((contractDate == null || contractDate.length() == 0)) {
            return "";
        }

        String[] parts = contractDate.split(" ");
        String result = parts[0];

        return dateFormatConversion(result);
    }

    public static String dateFormatConversion(String s) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(s);
        sdf.applyPattern("MM/dd/yyyy");
        return sdf.format(d);
    }

    // convert date from a string, which could be like different kind of date format.
    public String convertDate(String originalDate,String originalFormat, String resultFormat) throws Exception{
        if(originalDate == null || originalDate.equals("NA") || originalDate.equals("N/A") || originalDate.equals("")){
            return originalDate;
        }
        DateFormat formatter = new SimpleDateFormat(originalFormat);
        Date tempDate = formatter.parse(originalDate);
        return convertDate(tempDate, "M/d/yyyy");
    }

    public String daysFromToday(String dateString) {
        String days = "NA";
        if (dateString != null && !dateString.equals("NA") && !dateString.trim().equals("")) {
            try {
                Date date1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateString);
                Date today = new Date();
                days = Integer.toString(Days.daysBetween((new DateTime(today)).toLocalDate(), (new DateTime(date1)).toLocalDate()).getDays());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return days;
    }

    // Convert string to proper case
    public static String toDisplayCase(String s) {

        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }

    // Unescape a string that has special characters (such as apostrophes ( e.g. "&#039;" ==> "'" ))
    public String unescapeStringHtml(String str) {
        return StringEscapeUtils.unescapeHtml(str);
    }

    // Extract the last N characters from a string
    public String extractLastNCharacters(String str, int n) {
        return str.substring(Math.max(0, str.length() - n));
    }
    
    public String substring(String column, int beginIndex, int endIndex){
        return StringUtils.substring(column, beginIndex, endIndex);
    }

    public boolean filterContainsIgnoreCase(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toUpperCase();
        String valueText = (value == null) ? null : value.toString().trim().toUpperCase();

        if(filterText == null||filterText.equals("")) {
            return true;
        }

        if(valueText == null||valueText.equals("")) {
            return false;
        }

        return valueText.contains(filterText);
    }
}
