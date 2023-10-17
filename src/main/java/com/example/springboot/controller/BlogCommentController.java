package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Blog;
import com.example.springboot.entity.BlogComment;
import com.example.springboot.entity.User;
import com.example.springboot.service.IBlogCommentService;
import com.example.springboot.service.IBlogService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogComment")
public class BlogCommentController {
    private final String now = DateUtil.now();
    @Autowired
    IUserService userService;
    @Autowired
    private IBlogCommentService commentService;
    @Autowired
    private IBlogService BlogService;

    /**
     * 文章评论树结构接口
     *
     * @param foreignId 文章id
     * @return
     */
    @GetMapping("/tree")
    public Result list(@RequestParam Integer foreignId) {
        List<BlogComment> list = commentService.list(new LambdaQueryWrapper<BlogComment>().eq(BlogComment::getBlogId, foreignId));
        // 给评论赋值用户相关属性
        list.stream().forEach(comment -> {
            if (null != comment.getPid()) {
                comment.setParentName(userService.getById(comment.getUserId()).getNickname());
            }
            comment.setUsername(userService.getById(comment.getUserId()).getNickname());
            comment.setImg(userService.getById(comment.getUserId()).getAvatarUrl());
        });

        // 一级评论
        List<BlogComment> first = list.stream().filter(comment -> comment.getPid() == null).collect(Collectors.toList());
        for (BlogComment comment : first) {
            Integer pid = comment.getId();
            // 二级评论
            List<BlogComment> second = list.stream().filter(comment1 -> Objects.equals(pid, comment1.getPid())).collect(Collectors.toList());
            //将子评论放到children集合里
            comment.setChildren(second);

        }
        return Result.success(first);
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody BlogComment comment) {
        if (comment.getId() == null) {
            comment.setTime(DateUtil.now());
            //comment.setUser(TokenUtils.getCurrentUser().getNickname());
            //comment.setUserid(TokenUtils.getCurrentUser().getId());
        }
        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(commentService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }

        Page<BlogComment> page = commentService.page(new Page<>(pageNum, pageSize), queryWrapper);

        page.getRecords().stream().forEach(comment -> {
            User user = userService.getById(comment.getUserId());
            if (user != null) {
                comment.setUsername(user.getNickname());
            } else {
                comment.setUsername("帖子已删除");
            }

            Blog blog = BlogService.getById(comment.getBlogId());
            if (blog != null) {
                comment.setBlogName(blog.getName());
            } else {
                comment.setBlogName("帖子已删除");
            }
        });

        return Result.success(page);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<BlogComment> list = commentService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("VideoComment信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<BlogComment> list = reader.readAll(BlogComment.class);

        commentService.saveBatch(list);
        return Result.success();
    }

    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

}


