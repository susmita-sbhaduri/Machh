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
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
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
    private String previousMonthText;
    /**
     * Creates a new instance of MonthlyExpense
     */
    public MonthlyExpense() {
    }
    public void fillValues() throws NamingException, ParseException {
        
        LocalDate today = LocalDate.now();
        Month prevMonth = today.minusMonths(1).getMonth();
        // Get full name of the month in English locale
        previousMonthText = prevMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        // Get the first day of previous month
        LocalDate firstDayOfPrevMonth = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());

        // Get the last day of previous month
        LocalDate lastDayOfPrevMonth = today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String firstDayFormatted = firstDayOfPrevMonth.format(formatter);
        String lastDayFormatted = lastDayOfPrevMonth.format(formatter);
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

    public String getPreviousMonthText() {
        return previousMonthText;
    }

    public void setPreviousMonthText(String previousMonthText) {
        this.previousMonthText = previousMonthText;
    }
    
    
}
