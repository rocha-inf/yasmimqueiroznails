package com.rocha_inf.yasmimqueiroznails.setting.mapper;

import com.rocha_inf.yasmimqueiroznails.setting.dto.request.WorkingHourRequest;
import com.rocha_inf.yasmimqueiroznails.setting.dto.response.WorkingHourResponse;
import com.rocha_inf.yasmimqueiroznails.setting.entity.WorkingHour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkingHourMapper {

    WorkingHour toEntity(WorkingHourRequest request);
    WorkingHourResponse toResponse(WorkingHour workingHour);

}
