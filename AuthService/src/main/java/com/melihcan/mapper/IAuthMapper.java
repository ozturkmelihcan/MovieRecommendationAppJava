package com.melihcan.mapper;

import com.melihcan.dto.request.NewCreateUserRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;
import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.LoginResponseDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth toAuth (final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto (final Auth auth);

    LoginResponseDto toLoginResponseDto (final Auth auth); // id oldugu için Auth'dan dönmek daha mantıklı.

    @Mapping(source = "id",target = "authId")
    NewCreateUserRequestDto toNewCreateUserRequestDto (final Auth auth);

    Auth toAuth(final UpdateByEmailOrUsernameRequestDto dto);
}
