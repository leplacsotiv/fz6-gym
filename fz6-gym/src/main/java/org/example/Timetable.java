package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;


    public Timetable() {

        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.computeIfAbsent(trainingSession.getDayOfWeek(),
                k -> new TreeMap<>()).computeIfAbsent(trainingSession.getTimeOfDay(),
                k -> new ArrayList<>()).add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        var dayMap = timetable.get(dayOfWeek);
        var sessions = dayMap == null ? null : dayMap.get(timeOfDay);
        return sessions != null ? sessions : Collections.emptyList();
    }


    public List<CoachStats> getCountByCoaches() {
        return timetable.values().stream()
                .flatMap(dayMap -> dayMap.values().stream())
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                        TrainingSession::getCoach,
                        Collectors.summingInt(s -> 1)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Coach, Integer>comparingByValue(Comparator.reverseOrder()))
                .map(e -> new CoachStats(e.getKey(), e.getValue()))
                .toList();
    }


}
