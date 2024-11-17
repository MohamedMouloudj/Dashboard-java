package com.example.controller;

import com.example.database.TimeTableDB;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeTable {
    public static void initCalenderView(VBox calenderContainer, Button saveButton) {
        Logger.getLogger("com.calendarfx.view").setLevel(Level.SEVERE);

    //////////////////////////////////////////////////////////////////////////////
        CalendarView calendarView = new CalendarView();

        Calendar sessions = new Calendar<>("Les séances");
        Calendar additional = new Calendar<>("Les activités supplémentaires");
        sessions.setStyle(Calendar.Style.STYLE2);
        additional.setStyle(Calendar.Style.STYLE3);

        CalendarSource myCalendarSource = new CalendarSource("Emplois du temps");
        myCalendarSource.getCalendars().addAll(sessions, additional);

        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        calendarView.showMonthPage();
        calenderContainer.getChildren().add(calendarView);
    ///////////////////////////////////////////////////////////////////////////

        HashMap<String, Calendar> calenders = new HashMap<>();
        calenders.put("Default",myCalendarSource.getCalendars().get(0));
        calenders.put("Les séances", sessions);
        calenders.put("Les activités supplémentaires", additional);
        TimeTableDB.retreiveTimeTable(calenders);

        calendarView.refreshData();

        saveButton.setOnAction(event -> {
            ArrayList<Entry<?>> calenderEntries = new ArrayList<>();
            for (CalendarSource calendarSource : calendarView.getCalendarSources()) {
                if (!calendarSource.getName().equals("Emplois du temps") && !calendarSource.getName().equals("Default"))
                    continue;
                for (Calendar calendar : calendarSource.getCalendars()) {
                    calenderEntries.addAll(calendar.findEntries(""));
                }
            }
            TimeTableDB.dropTimeTable();
            TimeTableDB.addTimeTable(calenderEntries);
        });

    }
}
