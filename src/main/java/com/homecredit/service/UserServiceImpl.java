package com.homecredit.service;

import com.homecredit.dao.model.User;
import com.homecredit.dao.repository.UserRepository;
import com.homecredit.web.dto.UserDto;
import com.homecredit.web.dto.mapper.UserMapper;
import com.homecredit.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userRepository.save(userMapper.toEntity(userDto));

        return userMapper.toDto(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(
                userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("There is no user with id = %s".formatted(id)))
        );
    }
}
