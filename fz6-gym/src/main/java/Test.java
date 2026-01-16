import org.example.*;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Test {
    public static void main(String[] args) {
        Coach coach1 = new Coach("петренко","семен", "семенович");
        Group group1 = new Group("box", Age.CHILD, 60);
        TimeOfDay timeOfDay = new TimeOfDay(10,10);
        TrainingSession trainingSession = new TrainingSession(group1,coach1, DayOfWeek.MONDAY,
                timeOfDay);
        Timetable timetable = new Timetable();
        timetable.addNewTrainingSession(trainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> map = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        //System.out.println(Arrays.toString(map.keySet().toArray()));
        //System.out.println(map);
        System.out.println(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,timeOfDay));

        //timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10,10));
    }
}
