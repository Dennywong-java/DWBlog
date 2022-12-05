package com.dwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.constants.SystemConstants;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Article;
import com.dwblog.domain.entity.Category;
import com.dwblog.domain.vo.HotArticleVo;
import com.dwblog.domain.vo.ArticleListVo;
import com.dwblog.domain.vo.PageVo;
import com.dwblog.mapper.ArticleMapper;
import com.dwblog.service.ArticleService;
import com.dwblog.service.CategoryService;
import com.dwblog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

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

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // Conditional Query
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // if category ID is exits, check whether it is the same with the parameter
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        // status is normal
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // sort by isTop
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // Pagination
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        // get category name
        List<Article> articles = page.getRecords();
        // use category ID to find the category name

//        for(Article article: articles){
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        articles.stream()
                .map(new Function<Article,Article>() {
                    @Override
                    public Article apply(Article article) {
                        //获取分类Id，查询分类信息，获取分类名称
                        Category category = categoryService.getById(article.getCategoryId());
                        String name = category.getName();
                        article.setCategoryName(name);
                        return article;
                    }
                })
                .collect(Collectors.toList());

        // encapsulated VO
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
