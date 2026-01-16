package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {

        timetable = new HashMap<>();
    }
    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        timetable.computeIfAbsent(trainingSession.getDayOfWeek(),
                k -> new TreeMap<>()).computeIfAbsent(trainingSession.getTimeOfDay(),
                k -> new ArrayList<>()).add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public void getCountByCoaches() {

    }

}
