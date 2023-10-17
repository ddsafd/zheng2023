package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.BlogCollect;
import com.example.springboot.mapper.BlogCollectMapper;
import com.example.springboot.service.IBlogCollectService;
import org.springframework.stereotype.Service;

@Service
public class BlogCollectServiceImpl extends ServiceImpl<BlogCollectMapper, BlogCollect> implements IBlogCollectService {

}
