package com.example.Kcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kcsj.entity.DetectionRecords;
import com.example.Kcsj.entity.Drivers;
import com.example.Kcsj.entity.User;
import org.apache.ibatis.annotations.Select;
public interface DetectionRecordsMapper  extends BaseMapper<DetectionRecords>{
    @Select("select * from detectionRecords where licensePlate = #{licensePlate}")
    User selectByLicensePlate(String licensePlate);
}
