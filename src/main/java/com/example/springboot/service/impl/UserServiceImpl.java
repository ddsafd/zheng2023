package com.example.springboot.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constants;
import com.example.springboot.common.RoleEnum;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.controller.dto.UserPasswordDTO;
import com.example.springboot.entity.Menu;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.mapper.RoleMenuMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IMenuService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    // 使用一个简单的map来存储验证码，真实环境下应当使用更加安全和持久的存储方式，例如Redis。
    private final Map<String, String> verificationCodes = new HashMap<>();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtils.copyProperties(one, userDTO);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userDTO.getUsername()));
        if (one == null) {
            one = new User();
            BeanUtils.copyProperties(userDTO, one);
            // 默认一个普通用户的角色
            one.setRole(RoleEnum.ROLE_USER.toString());
            String password = DigestUtil.md5Hex(userDTO.getPassword());
            one.setPassword(password);
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        // 使用MD5加密用户提供的原密码
        String encryptedOldPassword = DigestUtil.md5Hex(userPasswordDTO.getPassword());
        // 确保数据库中存储的密码与加密后的原密码匹配
        User existingUser = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userPasswordDTO.getUsername()).eq(User::getPassword, encryptedOldPassword));
        if (existingUser == null) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
        // 使用MD5加密用户提供的新密码
        userPasswordDTO.setNewPassword(DigestUtil.md5Hex(userPasswordDTO.getNewPassword()));
        // 更新数据库
        userPasswordDTO.setPassword(encryptedOldPassword);  // 使用加密后的旧密码作为更新条件
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "更新密码失败");
        }
    }

    @Override
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return false;
    }

    @Override
    public String hashPassword(String rawPassword) {
        return null;
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        String password = DigestUtil.md5Hex(userDTO.getPassword());
        queryWrapper.eq("password", password);
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     *
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

    @Override
    public void sendVerificationCode(String email) {
        // 确保邮箱对应的用户存在
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
        if (user == null) {
            throw new ServiceException(Constants.CODE_600, "该邮箱尚未注册");
        }

        // 生成验证码并发送到用户的邮箱
        String code = UUID.randomUUID().toString().substring(0, 6);  // 可以使用其他方式生成验证码
        verificationCodes.put(email, code);

        // 实际开发中，请使用真正的邮件服务发送邮件
        System.out.println("发送验证码到 " + email + ": " + code);
    }

    @Override
    public void resetPasswordWithEmail(String email, String verificationCode, String newPassword) {
        String storedCode = verificationCodes.get(email);
        if (storedCode == null || !storedCode.equals(verificationCode)) {
            throw new ServiceException(Constants.CODE_600, "验证码错误或已过期");
        }

        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
        if (user == null) {
            throw new ServiceException(Constants.CODE_600, "用户不存在");
        }

        // 使用MD5加密新密码并更新
        user.setPassword(DigestUtil.md5Hex(newPassword));
        saveOrUpdate(user);
    }

}
