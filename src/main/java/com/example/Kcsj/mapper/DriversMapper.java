package com.example.Kcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kcsj.entity.Drivers;
import com.example.Kcsj.entity.User;
import org.apache.ibatis.annotations.Select;
public interface DriversMapper extends BaseMapper<Drivers>{  //操作Drivers实体
    // 根据车牌查询驾驶人的全部信息
    @Select("select * from drivers where licensePlate = #{licensePlate}")
    User selectByLicensePlate(String licensePlate);
}
