package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.BlogComment;
import com.example.springboot.mapper.BlogCommentMapper;
import com.example.springboot.service.IBlogCommentService;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {

}