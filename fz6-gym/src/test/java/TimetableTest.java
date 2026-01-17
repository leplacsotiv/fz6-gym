import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.example.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {
    private Timetable timetable;

    @BeforeEach
    void setUp() {
        timetable = new Timetable();
    }

    public static final Coach VASILEV = new Coach("Васильев", "Николай", "Сергеевич");
    public static final Coach IVANOV = new Coach("Иванов", "Иван", "Иванович");
    public static final Coach PETROV = new Coach("Петров", "Петр", "Петрович");

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        DayOfWeek day = DayOfWeek.MONDAY;
        TrainingSession singleTrainingSession = new TrainingSession(group, VASILEV,
                day, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(day).size());
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    public void testGetTrainingSessionsForDayNullDay() {
        TreeMap<TimeOfDay, List<TrainingSession>> result = timetable.getTrainingSessionsForDay(null);
        assertNull(result);
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeNullTime() {
        assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, null));
    }


    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, VASILEV,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, VASILEV,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, IVANOV,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, PETROV,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
        assertEquals(2,timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        TimeOfDay first = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey();
        TimeOfDay second = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey();
        assertEquals(13, first.getHours());
        assertEquals(20, second.getHours());

    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession singleTrainingSession = new TrainingSession(group, VASILEV,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13,0)).size());
        assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(14,0)));
    }


    @Test
    public void testGetCountByCoachesEmpty() {
        List<Map.Entry<Coach,Integer>> result = timetable.getCountByCoaches();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCountByCoachesOneCoach() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession singleTrainingSession = new TrainingSession(group, VASILEV,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        List<Map.Entry<Coach,Integer>> result = timetable.getCountByCoaches();

        assertEquals(1,result.size());
        assertEquals(1, result.getFirst().getValue());
        assertEquals(VASILEV, result.getFirst().getKey());
    }

    @Test
    public void testGetCountByCoachesSorted() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        timetable.addNewTrainingSession(new TrainingSession(group, VASILEV, DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, VASILEV, DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, VASILEV, DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, IVANOV, DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, IVANOV, DayOfWeek.SUNDAY,
                new TimeOfDay(12, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, PETROV, DayOfWeek.WEDNESDAY,
                new TimeOfDay(15, 0)));

        List<Map.Entry<Coach,Integer>> result = timetable.getCountByCoaches();


        assertEquals(3, result.size());

        assertEquals(3, result.get(0).getValue());
        assertEquals(VASILEV, result.get(0).getKey());
        assertEquals(2, result.get(1).getValue());
        assertEquals(IVANOV, result.get(1).getKey());
        assertEquals(1, result.get(2).getValue());
        assertEquals(PETROV, result.get(2).getKey());

    }

    @Test
    public void testMultipleSessionsSameSlot() {
        Group g1 = new Group("G1", Age.CHILD, 60);
        Group g2 = new Group("G2", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(g1, VASILEV, DayOfWeek.MONDAY,
                new TimeOfDay(13,0)));
        timetable.addNewTrainingSession(new TrainingSession(g2, IVANOV, DayOfWeek.MONDAY,
                new TimeOfDay(13,0)));
        List<Map.Entry<Coach,Integer>> result = timetable.getCountByCoaches();

        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13,0)).size());
        assertEquals(1,result.get(0).getValue());
        assertEquals(1,result.get(1).getValue());
    }
    @Test
    public void testGetCountByCoachesEqualCounts() {
        Group group = new Group("Test", Age.CHILD, 60);
        timetable.addNewTrainingSession(new TrainingSession(group, VASILEV, DayOfWeek.MONDAY, new TimeOfDay(10,0)));
        timetable.addNewTrainingSession(new TrainingSession(group, VASILEV, DayOfWeek.TUESDAY, new TimeOfDay(11,0)));
        timetable.addNewTrainingSession(new TrainingSession(group, IVANOV, DayOfWeek.WEDNESDAY, new TimeOfDay(12,0)));
        timetable.addNewTrainingSession(new TrainingSession(group, IVANOV, DayOfWeek.THURSDAY, new TimeOfDay(13,0)));

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getValue());
        assertEquals(2, result.get(1).getValue());
    }

}
