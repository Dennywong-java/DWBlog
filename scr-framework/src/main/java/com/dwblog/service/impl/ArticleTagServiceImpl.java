package com.dwblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.domain.entity.ArticleTag;
import com.dwblog.mapper.ArticleTagMapper;
import com.dwblog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(SgArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-26 18:22:04
 */
@Service("sgArticleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

