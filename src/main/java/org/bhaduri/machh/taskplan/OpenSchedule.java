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
import java.time.ZoneId;
import java.util.Map;
import java.util.stream.Collectors;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sb
 */
@Named(value = "openSchedule")
@ViewScoped
public class OpenSchedule implements Serializable {

    private ScheduleModel taskModel= new DefaultScheduleModel();
    private List<ScheduleEvent<?>> tasksForSelectedDate;
    

    /**
     * Creates a new instance of OpenSchedule
     * @throws javax.naming.NamingException
     */
    public OpenSchedule() throws NamingException {
//        eventModel = 
        // On initial load, fill with events for the current month
        fillTasksForMonth();
    }

    public void fillTasksForMonth() throws NamingException {
        taskModel.clear();
//        Date[] range = getMonthRange(anyDateInMonth);
        MasterDataServices masterDataService = new MasterDataServices();
        // Suppose you have a method to load your domain events
//        List<TaskPlanDTO> entries = masterDataService.getTaskdDetailsBetweenDates(range[0], range[1]);
        List<TaskPlanDTO> entries = masterDataService.getAllTaskPlanList();
       

        for (TaskPlanDTO entry : entries) {
            LocalDate localDate = LocalDate.parse(entry.getTaskDt());
            LocalDateTime startDateTime = localDate.atStartOfDay();
            ScheduleEvent<?> evt = DefaultScheduleEvent.builder()
                    .title(entry.getTaskName())
                    .startDate(startDateTime)
                    .endDate(startDateTime)
                    .description(entry.getTaskType())
                    .id(entry.getTaskId())
                    .data(entry.getAppliedFlag())
                    .allDay(true) 
                    .build();
            taskModel.addEvent(evt);
        }
    }
//    public void onViewChange() throws NamingException {
//            fillTasksForMonth();
//    }
    public Date getStartDateAsDate(ScheduleEvent<?> event) {
        LocalDateTime ldt = event.getStartDate();
        if (ldt == null) {
            return null;
        }
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public Date getEndDateAsDate(ScheduleEvent<?> event) {
        LocalDateTime ldt = event.getEndDate();
        if (ldt == null) {
            return null;
        }
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void onTaskSelect(SelectEvent<ScheduleEvent<?>> selectTask) {
        ScheduleEvent<?> selectedEvent = selectTask.getObject();
// Extract LocalDate from selected event
        LocalDate selectedDate = selectedEvent.getStartDate().toLocalDate();

        // Find all events that occur on this date
        tasksForSelectedDate = taskModel.getEvents().stream()
                .filter(evt -> evt.getStartDate().toLocalDate().isEqual(selectedDate))
                .collect(Collectors.toList());
    }
    public String submitTask(ScheduleEvent<?> event) {
        String taskid = event.getId();
        System.out.printf("Taskid: %s", taskid);
        String redirectUrl = "/secured/taskplan/taskapply?faces-redirect=true&selectedTask=" + event.getId();
        return redirectUrl; 
//        for (ScheduleEvent<?> event : eventsForSelectedDate) {
//            String taskid = event.getId();
//            // process as needed: e.g., save to DB, or just print
//            System.out.printf("Taskid: %s", taskid);
//        }
    }
    
    public void viewTask(ScheduleEvent<?> event) {
        String taskid = event.getId();
        System.out.printf("Taskid: %s", taskid);
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

    public ScheduleModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(ScheduleModel taskModel) {
        this.taskModel = taskModel;
    }        

    public List<ScheduleEvent<?>> getTasksForSelectedDate() {
        return tasksForSelectedDate;
    }

    public void setTasksForSelectedDate(List<ScheduleEvent<?>> tasksForSelectedDate) {
        this.tasksForSelectedDate = tasksForSelectedDate;
    }
    
}
