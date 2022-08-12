package com.homecredit.web.dto.mapper;

import com.homecredit.dao.model.view.UserView;
import com.homecredit.web.dto.UserDto;
import liquibase.util.StringUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper
public abstract class UserViewMapper {

    @Mapping(target = "roleTitleList", expression = "java(parseRoles(userView.getRole_List()))")
    public abstract UserDto toDto(UserView userView);

    public abstract List<UserDto> toDtoList(List<UserView> userView);

    List<String> parseRoles(String roles) {
        if (StringUtil.isEmpty(roles)) return Collections.emptyList();
        return Arrays.asList(roles.split(","));
    }
}
