package com.example.Kcsj.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kcsj.common.Result;
import com.example.Kcsj.entity.User;
import com.example.Kcsj.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;

    /**
     * 请求方式： GET查 POST增 PUT改 DELETE删
     * 用户分页列表查询，包含表的一对多查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.orderByDesc(User::getId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUsername, search);
        }
        Page<User> UserPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(UserPage);
    }
    //根据用户名查询单个用户信息
    @GetMapping("/{username}")
    public Result<?> getByUsername(@PathVariable String username) {
        System.out.println(username);
        return Result.success(userMapper.selectByUsername(username));
    }
    //查询所有用户信息
    @GetMapping("/all")
    public Result<?> GetAll() {
        return Result.success(userMapper.selectList(null));
    }
    //登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody User userParam) {
        System.out.println(userParam);
        try{
            User userPwd = userMapper.selectByName(userParam.getUsername());
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", userParam.getUsername());
            queryWrapper.eq("password", userPwd.getPassword());
            User res = userMapper.selectOne(queryWrapper);
            //  判断密码是否正确
            if (!Objects.equals(userParam.getPassword(), userPwd.getPassword())) {
                return Result.error("-1", "密码错误！");
            } else {
                return Result.success(res);
            }
        }catch (Exception e){
            return Result.error("-1", "用户名不存在！");
        }
    }
    //注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (res != null) {
            return Result.error("-1", "用户名重复");
        }

        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setName("");
        user1.setSex("男");
        user1.setRole("common");
        user1.setEmail("");
        user1.setTime(new Date());
        user1.setTel("");
        user1.setAvatar("");
        userMapper.insert(user1);
        return Result.success();
    }
    //更新用户信息
    @PostMapping("/update")
    public Result<?> updates(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }
    //根据用户ID删除用户信息
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable int id) {
        userMapper.deleteById(id);
        return Result.success();
    }
    //保存新用户信息
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        userMapper.insert(user);
        return Result.success();
    }
}
