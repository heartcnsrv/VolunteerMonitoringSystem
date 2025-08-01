package Model;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
    int schedule_id, service_id, max_volunteers;
    Date scheduled_date;
    Time start_Time, end_Time;
    String location;

    public Schedule(int schedule_id, int service_id, int max_volunteers, Date scheduled_date, Time start_Time, Time end_Time, String location) {
        this.schedule_id = schedule_id;
        this.service_id = service_id;
        this.max_volunteers = max_volunteers;
        this.scheduled_date = scheduled_date;
        this.start_Time = start_Time;
        this.end_Time = end_Time;
        this.location = location;
    }

    public Schedule() {}

    public int getScheduleId() {
        return schedule_id;
    }

    public int getServiceId() {
        return service_id;
    }

    public int getMaxVolunteers() {
        return max_volunteers;
    }

    public Date getScheduledDate() {
        return scheduled_date;
    }

    public Time getStartTime() {
        return start_Time;
    }

    public Time getEndTime() {
        return end_Time;
    }

    public String getLocation() {
        return location;
    }
}
