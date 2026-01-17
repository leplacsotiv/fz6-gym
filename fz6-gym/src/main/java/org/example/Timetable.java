package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;


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
        return Optional.ofNullable(timetable.get(dayOfWeek))
                .map(dayMap -> dayMap.get(timeOfDay))
                .orElse(null);
    }





    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach,Integer> trainerSessionsCount = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> days : timetable.values()) {
            for (List<TrainingSession> traningSessions : days.values()) {
                traningSessions.forEach((session) -> {
                    trainerSessionsCount.compute(session.getCoach(), (k, v) -> v == null ? 1 : v + 1);
                });
            }
        }
        return trainerSessionsCount.entrySet()
                .stream()
                .sorted(Map.Entry.<Coach, Integer>comparingByValue(Comparator.reverseOrder()))
                .toList();
    }

}
