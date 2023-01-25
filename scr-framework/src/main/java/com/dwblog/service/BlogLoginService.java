package com.dwblog.service;

import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
