package com.dwblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.domain.entity.Article;
import com.dwblog.mapper.ArticleMapper;
import com.dwblog.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
