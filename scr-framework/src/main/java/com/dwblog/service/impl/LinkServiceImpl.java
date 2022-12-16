package com.dwblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.domain.ResponseResult;
import com.dwblog.domain.entity.Link;
import com.dwblog.mapper.LinkMapper;
import com.dwblog.service.LinkService;
import org.springframework.stereotype.Service;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-12-12 12:39:02
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        return null;
    }
}

