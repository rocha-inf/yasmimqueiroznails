package com.rocha_inf.yasmimqueiroznails.user.mapstruct;

import com.rocha_inf.yasmimqueiroznails.security.auth.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User toEntity(RegisterRequest request, String encodedPassword){
        if(request == null || encodedPassword == null) return null;

        return new User(
                request.firstName(), request.lastName(), request.phoneNumber(),
                request.email(), encodedPassword
        );
    }

    RegisterResponse toRegisterResponse(User user);

}
