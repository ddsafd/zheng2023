package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.VideoCollect;
import com.example.springboot.service.IUserService;
import com.example.springboot.service.IVideoCollectService;
import com.example.springboot.service.IVideoService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 竞赛收藏表 前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/videoCollect")
public class VideoCollectController {

    private final String now = DateUtil.now();
    @Autowired
    IUserService userService;
    @Autowired
    private IVideoCollectService collectService;
    @Autowired
    private IVideoService videoService;

    @GetMapping("/repeat/{videoId}/{userId}")
    public Result repeat(@PathVariable Integer videoId, @PathVariable Integer userId) {
        VideoCollect one = collectService.getOne(new LambdaQueryWrapper<VideoCollect>().eq(VideoCollect::getUserId, userId).eq(VideoCollect::getVideoId, videoId));
        if (null != one) {
            return Result.error("202", "false");
        } else {
            return Result.success();
        }
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody VideoCollect collect) {
        VideoCollect one = collectService.getOne(new LambdaQueryWrapper<VideoCollect>().eq(VideoCollect::getUserId, collect.getUserId()).eq(VideoCollect::getVideoId, collect.getVideoId()));
        if (null != one) {
            collectService.remove(new LambdaQueryWrapper<VideoCollect>().eq(VideoCollect::getVideoId, collect.getVideoId()).eq(VideoCollect::getUserId, collect.getUserId()));
            return Result.error("202", "已取消收藏");
        }

        collect.setTime(DateUtil.now());
        collectService.save(collect);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        collectService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collectService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(collectService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(collectService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<VideoCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user_id", currentUser.getId());
        }
        Page<VideoCollect> page = collectService.page(new Page<>(pageNum, pageSize), queryWrapper);
        page.getRecords().stream().forEach(collect -> {
            collect.setVideo(videoService.getById(collect.getVideoId()));
            collect.setVideoName(videoService.getById(collect.getVideoId()).getName());
            collect.setUserName(userService.getById(collect.getUserId()).getNickname());
        });
        return Result.success(page);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<VideoCollect> list = collectService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Collect信息表", "UTF-8");
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
        List<VideoCollect> list = reader.readAll(VideoCollect.class);

        collectService.saveBatch(list);
        return Result.success();
    }

    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

}

