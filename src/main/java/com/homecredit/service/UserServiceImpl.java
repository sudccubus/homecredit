package com.homecredit.service;

import com.homecredit.dao.model.User;
import com.homecredit.dao.repository.UserRepository;
import com.homecredit.service.sub.ImageLoader;
import com.homecredit.web.dto.UserDto;
import com.homecredit.web.dto.mapper.UserMapper;
import com.homecredit.web.dto.mapper.UserViewMapper;
import com.homecredit.web.exception.EntityNotFoundException;
import com.homecredit.web.exception.IncorrectDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserViewMapper userViewMapper;

    private final UserRepository userRepository;

    private final ImageLoader imageLoader;

    @Override
    public List<UserDto> getAll() {
        return userViewMapper.toDtoList(userRepository.findAllWithRoles());
    }

    @Override
    public List<UserDto> getAdminList() {
        return userMapper.toListDto(userRepository.findByRolesTitle("ADMIN"));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userRepository.save(userMapper.toEntity(userDto));

        return userMapper.toDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Integer id = userDto.getId();

        if (isNull(id)) throw new IncorrectDataException("Id field must be filled");

        User user = findById(id);
        userMapper.updateFromDto(userDto, user);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(findById(id));
    }

    @Override
    public String setPhoto(Integer id, MultipartFile file) {
        User user = findById(id);

        String photoTitle = imageLoader.uploadImage(file, user.getPhotoTitle());
        user.setPhotoTitle(photoTitle);
        userRepository.save(user);

        return photoTitle;
    }

    private User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with id = %s".formatted(id)));
    }
}
