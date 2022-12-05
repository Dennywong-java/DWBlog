package com.dwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.constants.SystemConstants;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Article;
import com.dwblog.domain.entity.Category;
import com.dwblog.domain.vo.CategoryVo;
import com.dwblog.mapper.CategoryMapper;
import com.dwblog.service.ArticleService;
import com.dwblog.service.CategoryService;
import com.dwblog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-12-05 12:43:03
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //query: get the list of articles that is public
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //get the category ID for the articles and remove duplicates
        Set<Long> categoryIds = articleList.stream()
                // transform to Long
                .map(new Function<Article, Long>() {
                    @Override
                    public Long apply(Article article) {
                        return article.getCategoryId();
                    }
                })
                .collect(Collectors.toSet());
        //use the IDs to get the category ID
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(new Predicate<Category>() {
                    @Override
                    public boolean test(Category category) {
                        return SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus());
                    }
                })
                .collect(Collectors.toList());
        //encapsulated into VO
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

