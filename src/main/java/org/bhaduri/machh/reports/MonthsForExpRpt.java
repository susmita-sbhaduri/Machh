/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.reports;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author sb
 */
@Named(value = "monthsForExpRpt")
@ViewScoped
public class MonthsForExpRpt implements Serializable {

    private String selectedMonth;
    private List<String> months;
    
    public MonthsForExpRpt() {
        months = new ArrayList<>();
        for (Month month : Month.values()) {
            // Add month in "MMM" format, e.g. Jan, Feb
            months.add(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
    }

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }
    
}
