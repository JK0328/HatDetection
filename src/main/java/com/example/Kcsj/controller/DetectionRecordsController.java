package com.example.Kcsj.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kcsj.common.Result;
import com.example.Kcsj.entity.Drivers;
import com.example.Kcsj.entity.DetectionRecords;
import com.example.Kcsj.entity.User;
import com.example.Kcsj.mapper.DetectionRecordsMapper;
import com.example.Kcsj.mapper.DriversMapper;
import com.example.Kcsj.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/detectionRecords")
public class DetectionRecordsController {
    @Resource
    DetectionRecordsMapper detectionRecordsMapper;

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
        LambdaQueryWrapper<DetectionRecords> wrapper = Wrappers.<DetectionRecords>lambdaQuery();
        wrapper.orderByDesc(DetectionRecords::getLicensePlate);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(DetectionRecords::getLicensePlate, search);
        }
        Page<DetectionRecords> UserPage = detectionRecordsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(UserPage);
    }
    //根据车牌号查询检测结果
    @GetMapping("/search{licensePlate}")
    public Result<?> getByLicensePlate(@PathVariable String licensePlate) {
        System.out.println(licensePlate);
        return Result.success(detectionRecordsMapper.selectByLicensePlate(licensePlate));
    }
    //查询所有检测结果
    @GetMapping("/all")
    public Result<?> GetAll() {
        return Result.success(detectionRecordsMapper.selectList(null));
    }
    //更新检测结果
    @PostMapping("/update")
    public Result<?> updates(@RequestBody DetectionRecords detectionRecords) {
        detectionRecordsMapper.updateById(detectionRecords);
        return Result.success();
    }
    //根据车牌号删除检测结果
    @DeleteMapping("/delete{licensePlate}")
    public Result<?> delete(@PathVariable int licensePlate) {
        detectionRecordsMapper.deleteById(licensePlate);
        return Result.success();
    }
    //保存新检测结果
    @PostMapping
    public Result<?> save(@RequestBody DetectionRecords detectionRecords) {
        detectionRecordsMapper.insert(detectionRecords);
        return Result.success();
    }
}
