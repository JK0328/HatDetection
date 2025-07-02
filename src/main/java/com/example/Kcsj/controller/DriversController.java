package com.example.Kcsj.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kcsj.common.Result;
import com.example.Kcsj.entity.Drivers;
import com.example.Kcsj.entity.User;
import com.example.Kcsj.mapper.DriversMapper;
import com.example.Kcsj.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/drivers")
public class DriversController {
    @Resource
    DriversMapper driversMapper;

    /**
     * 用户分页列表查询，包含表的一对多查询
     * 根据分页参数和搜索关键字查询用户列表，并按车牌降序排列。
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Drivers> wrapper = Wrappers.<Drivers>lambdaQuery();
        wrapper.orderByDesc(Drivers::getId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Drivers::getLicensePlate, search);
        }
        Page<Drivers> UserPage = driversMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(UserPage);
    }
    //根据车牌号查询单个驾驶人信息
    @GetMapping("/search{licensePlate}")
    public Result<?> getByLicensePlate(@PathVariable String licensePlate) {
        System.out.println(licensePlate);
        return Result.success(driversMapper.selectByLicensePlate(licensePlate));
    }
    //查询所有驾驶人信息
    @GetMapping("/all")
    public Result<?> GetAll() {
        return Result.success(driversMapper.selectList(null));
    }
//    更新驾驶人信息
    @PostMapping("/update")
    public Result<?> updates(@RequestBody Drivers drivers) {
        driversMapper.updateById(drivers);
        return Result.success();
    }

    //根据用户ID删除用户信息
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable int id) {
        driversMapper.deleteById(id);
        return Result.success();
    }
//    //根据车牌号删除驾驶人信息
//    @DeleteMapping("/delete{licensePlate}")
//    public Result<?> delete(@PathVariable int licensePlate) {
//        driversMapper.deleteById(licensePlate);
//        return Result.success();
//    }
    //保存新驾驶人信息
    @PostMapping
    public Result<?> save(@RequestBody Drivers drivers) {
        driversMapper.insert(drivers);
        return Result.success();
    }

}
