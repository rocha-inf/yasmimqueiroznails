package com.rocha_inf.yasmimqueiroznails.setting.service;

import com.rocha_inf.yasmimqueiroznails.setting.dto.request.WorkingHourRequest;
import com.rocha_inf.yasmimqueiroznails.setting.dto.response.WorkingHourResponse;
import com.rocha_inf.yasmimqueiroznails.setting.entity.WorkingHour;
import com.rocha_inf.yasmimqueiroznails.setting.exception.InvalidWorkingHourException;
import com.rocha_inf.yasmimqueiroznails.setting.exception.WorkingHourPeriodConflictException;
import com.rocha_inf.yasmimqueiroznails.setting.exception.WorkingHourPeriodTooShortException;
import com.rocha_inf.yasmimqueiroznails.setting.mapper.WorkingHourMapper;
import com.rocha_inf.yasmimqueiroznails.setting.repository.WorkingHourRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkingHourService {

    private final WorkingHourRepository workingHourRepository;
    private final WorkingHourMapper workingHourMapper;

    public WorkingHourService(WorkingHourRepository workingHourRepository, WorkingHourMapper workingHourMapper) {
        this.workingHourRepository = workingHourRepository;
        this.workingHourMapper = workingHourMapper;
    }

    public WorkingHourResponse create(WorkingHourRequest workingHourRequest) {

        validatePeriod(workingHourRequest);
        validateConflict(workingHourRequest);

        WorkingHour workingHour = workingHourMapper.toEntity(workingHourRequest);
        WorkingHour savedWorkingHour = workingHourRepository.save(workingHour);

        return workingHourMapper.toResponse(savedWorkingHour);
    }

    public void validatePeriod(WorkingHourRequest workingHourRequest) {

        if (!workingHourRequest.startsAt().isBefore(workingHourRequest.endsAt())) {
            throw new InvalidWorkingHourException("O horário de início deve ser anterior ao horário de término.");
        }
        if (workingHourRequest.endsAt().isBefore(workingHourRequest.startsAt().plusMinutes(5))) {
            throw new WorkingHourPeriodTooShortException("O horário de funcionamento deve ter duração mínima de 5 minutos");
        }

    }

    public void validateConflict(WorkingHourRequest workingHourRequest) {
        if (workingHourRepository.existOverLapping(workingHourRequest.dayOfWeek(), workingHourRequest.startsAt(), workingHourRequest.endsAt())) {
            throw new WorkingHourPeriodConflictException("Já existe um horário de funcionamento para o período informado.");
        }
    }

}
