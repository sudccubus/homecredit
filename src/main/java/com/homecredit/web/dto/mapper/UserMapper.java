package com.homecredit.web.dto.mapper;

import com.homecredit.dao.model.User;
import com.homecredit.service.RoleService;
import com.homecredit.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = RoleService.class)
@RequiredArgsConstructor
public abstract class UserMapper {

    @Autowired
    RoleService roleService;

    @Mapping(target = "roles", expression = "java(roleService.getRolesByTitles(dto.getRoleTitleList()))")
    public abstract User toEntity(UserDto dto);

    @Mapping(target = "roleTitleList", expression = "java(roleService.getTitlesByRoles(user.getRoles()))")
    public abstract UserDto toDto(User user);
}
