package com.example.database;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeTableDB {
    public static void addTimeTable(ArrayList<Entry<?>> calenderEntries) {
        String id="";
        String oldId="";

        for (Entry<?> entry : calenderEntries) {
            id=entry.getId();
            if (id.equals(oldId)) continue;
            oldId=id;
            HashMap<String,Date> entriesDateWithSameId = new HashMap<>();
            for (Entry<?> entry1 : calenderEntries) {
                if (entry1.getId().equals(id) && !entry1.equals(entry)){
                    entriesDateWithSameId.put(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(entry1.getStartDate())
                            ,Date.from(entry1.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }
            }
            Document document = new Document("title", entry.getTitle())
                    .append("id", entry.getId())
                    .append("startDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(entry.getStartDate()))
                    .append("endDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(entry.getEndDate()))
                    .append("startTime", entry.getStartTime().toString())
                    .append("endTime", entry.getEndTime().toString())
                    .append("zoneId", entry.getZoneId().toString())
                    .append("fullDay", entry.isFullDay())
                    .append("location", entry.getLocation())
                    .append("recurrenceRule", entry.getRecurrenceRule())
                    .append("calenderName", entry.getCalendar().getName())
                    .append("listOfEntriesWithSameId", new Document(entriesDateWithSameId));
            MongoDB.createDocument(MongoDB.Collections.TimeTable, document);
        }
    }
    public static ArrayList<Entry<?>> retreiveTimeTable(HashMap<String,Calendar> calenders) {
        List<Document> documentsEntries= MongoDB.readDocuments(MongoDB.Collections.TimeTable, null);
        if (documentsEntries==null || documentsEntries.isEmpty()) return null;
        ArrayList<Entry<?>> entries = new ArrayList<>();
        for (Document document : documentsEntries) {
            Entry<?> entry = new Entry<>();
            entry.setTitle(document.getString("title"));
            entry.setId(document.getString("id"));
            entry.changeStartTime(LocalTime.parse(document.getString("startTime")));
            entry.changeEndTime(LocalTime.parse(document.getString("endTime")));
            entry.changeStartDate(LocalDate.parse(document.getString("startDate")));
            entry.changeEndDate(LocalDate.parse(document.getString("endDate")));
            entry.setFullDay(document.getBoolean("fullDay"));
            entry.setLocation(document.getString("location"));
            entry.setRecurrenceRule(document.getString("recurrenceRule"));
            entry.setZoneId(ZoneId.of(document.getString("zoneId")));
            String calenderName=document.getString("calenderName");
            if(calenderName!=null)
                entry.setCalendar(calenders.get(calenderName));

            Document entriesWithSameId = (Document) document.get("listOfEntriesWithSameId");
            if (entriesWithSameId!=null){
                for (Map.Entry<String,Object> entryWithSameId : entriesWithSameId.entrySet()) {
                    LocalDate startDate = LocalDate.parse(entryWithSameId.getKey());
                    LocalDate endDate = ((Date)entryWithSameId.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Entry<?> entry1 = new Entry<>();
                    entry1.changeStartDate(startDate);
                    entry1.changeEndDate(endDate);
                    entry1.setId(entry.getId());
                    entry1.setTitle(entry.getTitle());
                    entry1.changeStartTime(entry.getStartTime());
                    entry1.changeEndTime(entry.getEndTime());
                    entry1.setFullDay(entry.isFullDay());
                    entry1.setLocation(entry.getLocation());
                    entry1.setRecurrenceRule(entry.getRecurrenceRule());
                    entry1.setZoneId(entry.getZoneId());
                    entry1.setCalendar(entry.getCalendar());
                    entries.add(entry1);
                }
            }
            entries.add(entry);
        }
        return entries;
    }
    public static void dropTimeTable() {
        MongoDB.dropCollection(MongoDB.Collections.TimeTable);
    }
}
