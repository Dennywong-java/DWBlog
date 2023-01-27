package com.dwblog.service;

import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}