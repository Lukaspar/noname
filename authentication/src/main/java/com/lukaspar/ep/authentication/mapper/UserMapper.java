package com.lukaspar.ep.authentication.mapper;

import com.lukaspar.ep.authentication.dto.UserDto;
import com.lukaspar.ep.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);
}
