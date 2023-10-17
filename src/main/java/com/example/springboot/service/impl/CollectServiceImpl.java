package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Collect;
import com.example.springboot.mapper.CollectMapper;
import com.example.springboot.service.ICollectService;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

}
