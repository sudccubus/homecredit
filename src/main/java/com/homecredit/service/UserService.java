package com.homecredit.service;

import com.homecredit.web.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    List<UserDto> getAdminList();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Integer id);

    String setPhoto(Integer id, MultipartFile file);
}
