package com.homecredit.service;

import com.homecredit.dao.model.Role;
import com.homecredit.dao.repository.RoleRepository;
import com.homecredit.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRolesByTitles(List<String> titles) {
        if (titles.isEmpty()) return Collections.emptyList();

        return titles.stream()
                .map(title ->
                        roleRepository.findByTitle(title)
                                .orElseThrow(() -> new NotFoundException("'%s' role does not exist".formatted(title))))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTitlesByRoles(List<Role> roles) {
        return roles.stream()
                .map(Role::getTitle)
                .collect(Collectors.toList());
    }
}
