package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
