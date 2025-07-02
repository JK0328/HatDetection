package com.example.Kcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kcsj.entity.Drivers;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.Kcsj.entity.Drivers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

public interface DriversMapper extends BaseMapper<Drivers>{  //操作Drivers实体
    // 根据车牌查询驾驶人的全部信息
    @Select("select * from drivers where licensePlate = #{licensePlate}")
    Drivers selectByLicensePlate(String licensePlate);
}
