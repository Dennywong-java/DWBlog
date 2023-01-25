package com.dwblog.job;

import com.dwblog.domain.entity.Article;
import com.dwblog.service.ArticleService;
import com.dwblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleService  articleService;
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        // 从redis中获取浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        // 更新数据库
        articleService.updateBatchById(articleList);
    }
}
