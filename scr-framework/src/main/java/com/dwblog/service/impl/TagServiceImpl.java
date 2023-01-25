package com.dwblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwblog.domain.entity.Tag;
import com.dwblog.mapper.TagMapper;
import com.dwblog.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-07-19 22:33:38
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

