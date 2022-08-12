package com.homecredit.web.dto.mapper;

import com.homecredit.dao.model.User;
import com.homecredit.service.RoleService;
import com.homecredit.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(uses = RoleService.class)
@RequiredArgsConstructor
public abstract class UserMapper {

    @Autowired
    RoleService roleService;

    @Mapping(target = "roles", expression = "java(roleService.getRolesByTitles(userDto.getRoleTitleList()))")
    public abstract User toEntity(UserDto userDto);

    @Mapping(target = "roleTitleList", expression = "java(roleService.getTitlesByRoles(user.getRoles()))")
    public abstract UserDto toDto(User user);

    public abstract List<UserDto> toListDto(List<User> userList);

    @Mapping(target = "roles", expression = "java(roleService.getRolesByTitles(userDto.getRoleTitleList()))")
    public abstract void updateFromDto(UserDto userDto, @MappingTarget User user);
}
