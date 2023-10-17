package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.VideoCollect;
import com.example.springboot.mapper.VideoCollectMapper;
import com.example.springboot.service.IVideoCollectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 竞赛收藏表 服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class VideoCollectServiceImpl extends ServiceImpl<VideoCollectMapper, VideoCollect> implements IVideoCollectService {

}
