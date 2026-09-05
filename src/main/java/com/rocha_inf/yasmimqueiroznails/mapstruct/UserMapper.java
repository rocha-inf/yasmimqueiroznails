package com.rocha_inf.yasmimqueiroznails.mapstruct;

import com.rocha_inf.yasmimqueiroznails.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.entity.User;
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
