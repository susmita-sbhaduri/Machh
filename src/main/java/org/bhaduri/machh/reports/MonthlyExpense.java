/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.reports;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.AllExpenseReportDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "monthlyExpense")
@ViewScoped
public class MonthlyExpense implements Serializable {
    List<AllExpenseReportDTO> expdetails;
    private String monthText;
    private String selectedMonth;
    private String selectedYear;
    /**
     * Creates a new instance of MonthlyExpense
     */
    public MonthlyExpense() {
    }
    public void fillValues() throws NamingException, ParseException {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(selectedMonth);
        int monthNumber = accessor.get(java.time.temporal.ChronoField.MONTH_OF_YEAR); 
        Month monthEnum = Month.of(monthNumber);
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(selectedYear), monthEnum);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        monthText = monthEnum.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//        LocalDate today = LocalDate.now();
//        Month prevMonth = today.minusMonths(1).getMonth();
//        // Get full name of the month in English locale
//        previousMonthText = prevMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//        // Get the first day of previous month
//        LocalDate firstDayOfPrevMonth = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
//
//        // Get the last day of previous month
//        LocalDate lastDayOfPrevMonth = today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String firstDayFormatted = firstDayOfMonth.format(formatter);
        String lastDayFormatted = lastDayOfMonth.format(formatter);
        MasterDataServices masterDataService = new MasterDataServices();
        expdetails = masterDataService.getRescropExpRpt(firstDayFormatted,
                lastDayFormatted);
    }

    public List<AllExpenseReportDTO> getExpdetails() {
        return expdetails;
    }

    public void setExpdetails(List<AllExpenseReportDTO> expdetails) {
        this.expdetails = expdetails;
    }

    public String getMonthText() {
        return monthText;
    }

    public void setMonthText(String monthText) {
        this.monthText = monthText;
    }    

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }
    
    
}
