/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.taskplan;


import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.TaskPlanDTO;
import org.bhaduri.machh.services.MasterDataServices;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sb
 */
@Named(value = "openSchedule")
@ViewScoped
public class OpenSchedule implements Serializable {
    private ScheduleModel eventModel;
    private List<ScheduleEvent<?>> eventsForSelectedDate;
    /**
     * Creates a new instance of OpenSchedule
     */
    public OpenSchedule() throws NamingException {
        eventModel = new DefaultScheduleModel();
        // On initial load, fill with events for the current month
        fillEventsForMonth(new Date());
    }
    public void fillEventsForMonth(Date anyDateInMonth) throws NamingException {
        eventModel.clear();
        Date[] range = getMonthRange(anyDateInMonth);
        MasterDataServices masterDataService = new MasterDataServices();
        // Suppose you have a method to load your domain events
        List<TaskPlanDTO> entries = masterDataService.getTaskdDetailsBetweenDates(range[0], range[1]);
        Date mysqlDate;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        
        for (TaskPlanDTO entry : entries) {
            LocalDate localDate = LocalDate.parse(entry.getTaskDt());
            LocalDateTime startDateTime = localDate.atStartOfDay();
            ScheduleEvent<?> evt = DefaultScheduleEvent.builder()
                .title(entry.getTaskName())
                .startDate(startDateTime)
                .endDate(startDateTime)
                .description(entry.getTaskName())
                .build();
            eventModel.addEvent(evt);
        }
    }
    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        LocalDateTime selectedDate = selectEvent.getObject();
        
        // Find all events that occur on this date
        eventsForSelectedDate = eventModel.getEvents().stream()
            .filter(evt -> evt.getStartDate().toLocalDate().isEqual(selectedDate.toLocalDate()))
            .collect(Collectors.toList());
        
        // Optionally, add a FacesMessage or do further processing
    }
    // Utility to get first/last date of a month
    private Date[] getMonthRange(Date refDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(refDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date end = cal.getTime();
        return new Date[]{start, end};
    }

       public void onScheduleDateRangeChange() throws NamingException, ParseException {
        String start = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("rangeStart");
        String end = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("rangeEnd");
        
        // Parse string into LocalDate or Date
//        LocalDate startDate = LocalDate.parse(start); // e.g., "2025-07-01"
//        LocalDate endDate = LocalDate.parse(end);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);
        MasterDataServices masterDataService = new MasterDataServices();
        List<TaskPlanDTO> entries = masterDataService.getTaskdDetailsBetweenDates(startDate, endDate);
        // (Now load data as needed for the range)
        eventModel = new DefaultScheduleModel();
        for (TaskPlanDTO entry : entries) {
            LocalDate localDate = LocalDate.parse(entry.getTaskDt());
            LocalDateTime startDateTime = localDate.atStartOfDay();
            ScheduleEvent<?> evt = DefaultScheduleEvent.builder()
                .title(entry.getTaskName())
                .startDate(startDateTime)
                .endDate(startDateTime)
                .description(entry.getTaskName())
                .build();
            eventModel.addEvent(evt);
        }
        
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
    

}
