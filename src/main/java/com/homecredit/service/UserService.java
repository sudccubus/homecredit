package com.homecredit.service;

import com.homecredit.web.dto.UserDto;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Integer id);
}
