package com.homecredit.service;


import com.homecredit.dao.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRolesByTitles(List<String> titles);

    List<String> getTitlesByRoles(List<Role> roles);
}
