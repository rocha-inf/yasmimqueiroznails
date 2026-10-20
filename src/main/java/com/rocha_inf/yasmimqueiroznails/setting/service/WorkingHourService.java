package com.rocha_inf.yasmimqueiroznails.setting.service;

import com.rocha_inf.yasmimqueiroznails.setting.dto.request.WorkingHourRequest;
import com.rocha_inf.yasmimqueiroznails.setting.dto.response.WorkingHourResponse;
import com.rocha_inf.yasmimqueiroznails.setting.entity.WorkingHour;
import com.rocha_inf.yasmimqueiroznails.setting.exception.InvalidWorkingHourException;
import com.rocha_inf.yasmimqueiroznails.setting.exception.WorkingHourPeriodConflictException;
import com.rocha_inf.yasmimqueiroznails.setting.exception.WorkingHourPeriodTooShortException;
import com.rocha_inf.yasmimqueiroznails.setting.mapper.WorkingHourMapper;
import com.rocha_inf.yasmimqueiroznails.setting.repository.WorkingHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkingHourService {

    private final WorkingHourRepository repository;
    private final WorkingHourMapper mapper;


    public WorkingHourResponse create(WorkingHourRequest workingHourRequest) {

        validate(workingHourRequest);

        WorkingHour workingHour = mapper.toEntity(workingHourRequest);
        WorkingHour savedWorkingHour = repository.save(workingHour);

        return mapper.toResponse(savedWorkingHour);
    }


    private void validate(WorkingHourRequest workingHourRequest){

        if (!workingHourRequest.startsAt().isBefore(workingHourRequest.endsAt())) {
            throw new InvalidWorkingHourException("O horário de início deve ser anterior ao horário de término.");
        }

        if (workingHourRequest.endsAt().isBefore(workingHourRequest.startsAt().plusMinutes(5))) {
            throw new WorkingHourPeriodTooShortException("O horário de funcionamento deve ter duração mínima de 5 minutos");
        }

        if (repository.existsOverlapping(workingHourRequest.dayOfWeek(), workingHourRequest.startsAt(), workingHourRequest.endsAt())) {
            throw new WorkingHourPeriodConflictException("Já existe um horário de funcionamento para o período informado.");
        }

    }

}
