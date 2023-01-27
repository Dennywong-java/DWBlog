package com.dwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-01-26 11:30:38
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Role> selectRoleAll();

    void updateRole(Role role);

    void insertRole(Role role);

    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);

    List<Long> selectRoleIdByUserId(Long userId);
}

