package com.dwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.constants.SystemConstants;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Article;
import com.dwblog.domain.vo.HotArticleVo;
import com.dwblog.mapper.ArticleMapper;
import com.dwblog.service.ArticleService;
import com.dwblog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只显示10篇文章
        Page<Article> page = new Page(SystemConstants.PAGE_CURRENT_PAGE,SystemConstants.PAGE_SIZE);
        page(page,queryWrapper);

        List<Article> records = page.getRecords();

        //Bean拷贝
//        List<HotArticleVo> articleVol = new ArrayList<>();
//        for (Article articles : records) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(articles,vo);
//            articleVol.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }
}
