package com.dwblog.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(SgArticleTag)表实体类
 *
 * @author makejava
 * @since 2023-01-26 18:22:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_article_tag")
public class ArticleTag {
    //文章id@TableId
    private Long articleId;
    //标签id@TableId
    private Long tagId;




}
