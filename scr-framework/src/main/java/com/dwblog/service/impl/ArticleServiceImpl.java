package com.dwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.constants.SystemConstants;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Article;
import com.dwblog.domain.entity.Category;
import com.dwblog.domain.vo.ArticleDetailVo;
import com.dwblog.domain.vo.ArticleListVo;
import com.dwblog.domain.vo.HotArticleVo;
import com.dwblog.domain.vo.PageVo;
import com.dwblog.mapper.ArticleMapper;
import com.dwblog.service.ArticleService;
import com.dwblog.service.CategoryService;
import com.dwblog.utils.BeanCopyUtils;
import com.dwblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //从Redis中获取浏览量
        Map<String, Integer> viewCountMap = redisTemplate.opsForHash().entries("article:viewCount");
        //最多只显示10篇文章
        Page<Article> page = new Page(SystemConstants.PAGE_CURRENT_PAGE, SystemConstants.PAGE_SIZE);
        page(page, queryWrapper);
        //赋值新的viewCount
        List<Article> records = page.getRecords();
        for(Article article:records){
            Integer viewCount = viewCountMap.get(article.getId().toString());
            if(viewCount != null) {
                article.setViewCount(viewCount.longValue());
            }
        }
        //按照浏览量进行排序
        records.sort(Comparator.comparing(Article::getViewCount).reversed());
        //Bean拷贝
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // Conditional Query
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // if category ID is exits, check whether it is the same with the parameter
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // status is normal
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // sort by isTop
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // Pagination
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        // get category name， 从redis中获取最新的viewcount
        Map<String, Integer> viewCountMap = redisTemplate.opsForHash().entries("article:viewCount");
        List<Article> articles = page.getRecords();
        for(Article article:articles){
            Integer viewCount = viewCountMap.get(article.getId().toString());
            if(viewCount != null) {
                article.setViewCount(viewCount.longValue());
            }
        }
        // use category ID to find the category name
        articles.stream()
                .map(article -> {
                    //获取分类Id，查询分类信息，获取分类名称
                    Category category = categoryService.getById(article.getCategoryId());
                    String name = category.getName();
                    article.setCategoryName(name);
                    return article;
                })
                .collect(Collectors.toList());

        // encapsulated VO
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // get Article according to the ID
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        // encapsulated VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category byId = categoryService.getById(categoryId);
        if (byId != null) {
            articleDetailVo.setCategoryName(byId.getName());
        }
        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

}
