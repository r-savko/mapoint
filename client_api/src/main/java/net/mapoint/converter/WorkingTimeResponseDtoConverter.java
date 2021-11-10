package net.mapoint.converter;

import java.util.Date;
import net.mapoint.model.WorkingTimeDto;
import net.mapoint.model.WorkingTimeResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkingTimeResponseDtoConverter implements Converter<WorkingTimeDto, WorkingTimeResponse> {

    @Override
    public WorkingTimeResponse convert(WorkingTimeDto source) {
        WorkingTimeResponse workingTimeResponse = new WorkingTimeResponse();
        workingTimeResponse.setId(source.getId());
        workingTimeResponse.setDayNumber(source.getDayNumber());
        if (source.getStartTime() != null) {
            workingTimeResponse.setStartTime(new Date(source.getStartTime().getTime()));
        }
        if (source.getEndTime() != null) {
            workingTimeResponse.setEndTime(new Date(source.getEndTime().getTime()));
        }
        workingTimeResponse.setWeekend(source.isWeekend());
        workingTimeResponse.setFullTime(source.isFullTime());
        return workingTimeResponse;
    }
}
