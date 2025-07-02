package com.example.Kcsj.controller;

import com.example.Kcsj.common.Result;
import com.example.Kcsj.mapper.DetectionRecordsMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 驾驶人头盔检测统计分析接口
 * 提供年龄段、性别、日期、地区的不戴头盔统计数据
 */
@RestController
@RequestMapping("/driverdetection")
public class DriverDetectionController {
    @Resource
    private DetectionRecordsMapper detectionRecordsMapper;

    /**
     * 各年龄段不佩戴头盔分布
     * @return [{ageGroup: "20-29", count: 10}, ...]
     */
    @GetMapping("/noHelmetByAge")
    public Result<List<Map<String, Object>>> getNoHelmetByAge() {
        return Result.success(detectionRecordsMapper.countNoHelmetByAge());
    }

    /**
     * 男女性别不戴头盔情况
     * @return [{gender: "男", count: 5}, {gender: "女", count: 3}]
     */
    @GetMapping("/noHelmetByGender")
    public Result<List<Map<String, Object>>> getNoHelmetByGender() {
        return Result.success(detectionRecordsMapper.countNoHelmetByGender());
    }

    /**
     * 每天不戴头盔数目
     * @return [{date: "2024-05-01", count: 2}, ...]
     */
    @GetMapping("/noHelmetByDay")
    public Result<List<Map<String, Object>>> getNoHelmetByDay() {
        return Result.success(detectionRecordsMapper.countNoHelmetByDay());
    }

    /**
     * 不同地区不戴头盔数目
     * @return [{region: "XX区", count: 4}, ...]
     */
    @GetMapping("/noHelmetByRegion")
    public Result<List<Map<String, Object>>> getNoHelmetByRegion() {
        return Result.success(detectionRecordsMapper.countNoHelmetByRegion());
    }


}