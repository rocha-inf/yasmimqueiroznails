package com.rocha_inf.yasmimqueiroznails.user.mapstruct;

import com.rocha_inf.yasmimqueiroznails.security.auth.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "encodedPassword")
    User toEntity(RegisterRequest request, String encodedPassword);

    RegisterResponse toRegisterResponse(User user);
    WelcomeEmailMessage toWelcomeEmailMessage(User user);

}
