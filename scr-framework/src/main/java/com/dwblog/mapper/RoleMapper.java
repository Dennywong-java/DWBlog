package com.dwblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwblog.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-26 11:30:37
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Long> selectRoleIdByUserId(Long userId);
}

