package com.melihcan.mapper;

import com.melihcan.dto.request.NewCreateUserRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;

import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.LoginResponseDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.dto.response.RoleResponseDto;
import com.melihcan.rabbitmq.model.NewCreateUserRequestModel;
import com.melihcan.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth  toAuth(final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto(final  Auth auth);

    LoginResponseDto toLoginResponseDto(final Auth auth);

    @Mapping(source = "id",target = "authId")
    NewCreateUserRequestDto toNewCreateUserRequestDto(final Auth auth);

    Auth toAuth(final UpdateByEmailOrUsernameRequestDto dto);

    @Mapping(source = "id",target = "authId")
    NewCreateUserRequestModel toNewCreateUserRequestModel(final Auth auth);

    RoleResponseDto toRoleResponseDto(final Auth auth);

    List<RoleResponseDto>toRoleResponseDtoList(final List<Auth> authList);
}
