package com.dwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Link;
import com.dwblog.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-12-12 12:39:02
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}

