package com.dwblog.controller;

import com.dwblog.domain.ResponseResult;
import com.dwblog.service.LinkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/link")
@Api(value = "友情链接", tags = "友情链接相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
