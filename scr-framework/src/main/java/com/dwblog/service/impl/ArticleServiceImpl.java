package com.dwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Article;
import com.dwblog.mapper.ArticleMapper;
import com.dwblog.service.ArticleService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus,0);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只显示10篇文章
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);
        List<Article> records = page.getRecords();
        return ResponseResult.okResult(records);
    }
}
