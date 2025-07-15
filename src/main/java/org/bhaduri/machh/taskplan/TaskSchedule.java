/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.taskplan;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.naming.NamingException;
import org.bhaduri.machh.services.MasterDataServices;
import org.primefaces.event.DateViewChangeEvent;

/**
 *
 * @author sb
 */
@Named(value = "taskSchedule")
@ViewScoped
public class TaskSchedule implements Serializable {
    private Date selectedDate;
    /**
     * Creates a new instance of TaskSchedule
     */
    public TaskSchedule() {
    }
    public void onMonthChange() {
    // This will be called when user changes the calendar view
    // selectedDate will hold the first displayed day of the new month internally

    Date first = getMonthStart(selectedDate);
    Date last = getMonthEnd(selectedDate);
    }
    private Date getMonthStart(Date inputDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getMonthEnd(Date inputDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
    
    
}
