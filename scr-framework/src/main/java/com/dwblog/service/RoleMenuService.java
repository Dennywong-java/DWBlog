package com.dwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dwblog.domain.entity.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}