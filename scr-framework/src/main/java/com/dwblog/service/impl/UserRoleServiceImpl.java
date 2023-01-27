package com.dwblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.domain.entity.UserRole;
import com.dwblog.mapper.UserRoleMapper;
import com.dwblog.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
