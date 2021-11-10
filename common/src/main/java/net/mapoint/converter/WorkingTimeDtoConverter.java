package net.mapoint.converter;

import java.sql.Time;
import java.util.Date;
import net.mapoint.dao.entity.DaysOfWeek;
import net.mapoint.dao.entity.WorkingTime;
import net.mapoint.model.WorkingTimeDto;
import org.springframework.stereotype.Component;

@Component
public class WorkingTimeDtoConverter {

    WorkingTime toEntity(WorkingTimeDto workingTimeDto) {
        WorkingTime workingTime = new WorkingTime();
        workingTime.setId(workingTimeDto.getId());
        workingTime.setDayNumber(DaysOfWeek.getValue(workingTimeDto.getDayNumber()));
        if (workingTimeDto.getStartTime() != null) {
            workingTime.setStartTime(new Time(workingTimeDto.getStartTime().getTime()));
        }
        if (workingTimeDto.getEndTime() != null) {
            workingTime.setEndTime(new Time(workingTimeDto.getEndTime().getTime()));
        }
        workingTime.setWeekend(workingTimeDto.isWeekend());
        workingTime.setFullTime(workingTimeDto.isFullTime());
        return workingTime;
    }

    WorkingTimeDto toDto(WorkingTime workingTime) {
        WorkingTimeDto workingTimeDto = new WorkingTimeDto();
        workingTimeDto.setId(workingTime.getId());
        workingTimeDto.setDayNumber(workingTime.getDayNumber().getId());
        if (workingTime.getStartTime() != null) {
            workingTimeDto.setStartTime(new Date(workingTime.getStartTime().getTime()));
        }
        if (workingTime.getEndTime() != null) {
            workingTimeDto.setEndTime(new Date(workingTime.getEndTime().getTime()));
        }
        workingTimeDto.setWeekend(workingTime.isWeekend());
        workingTimeDto.setFullTime(workingTime.isFullTime());
        return workingTimeDto;
    }

}
