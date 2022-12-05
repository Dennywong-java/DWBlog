package com.dwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-12-05 12:36:25
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

