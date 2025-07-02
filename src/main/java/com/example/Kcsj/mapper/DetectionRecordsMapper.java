package com.example.Kcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kcsj.entity.DetectionRecords;
import com.example.Kcsj.entity.Drivers;
import com.example.Kcsj.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface DetectionRecordsMapper  extends BaseMapper<DetectionRecords>{
    //根据车牌查询所有违规记录
    @Select("select * from detectionRecords where licensePlate = #{licensePlate}")
    User selectByLicensePlate(String licensePlate);

    /**
     * 统计各年龄段不戴头盔人数
     * @return List<Map>，每个Map包含ageGroup和count
     */
    @Select("SELECT CASE "
            + " WHEN d.age < 20 THEN '20岁以下' "
            + " WHEN d.age BETWEEN 20 AND 29 THEN '20-29' "
            + " WHEN d.age BETWEEN 30 AND 39 THEN '30-39' "
            + " WHEN d.age BETWEEN 40 AND 49 THEN '40-49' "
            + " WHEN d.age >= 50 THEN '50岁及以上' "
            + " ELSE '未知' END AS ageGroup, COUNT(*) AS count "
            + " FROM detection_records r "
            + " LEFT JOIN drivers d ON r.license_plate = d.license_plate "
            + " WHERE r.helmet = 0 "
            + " GROUP BY ageGroup")
    List<Map<String, Object>> countNoHelmetByAge();

    /**
     * 统计男女性别不戴头盔人数
     * @return List<Map>，每个Map包含gender和count
     */
    @Select("SELECT COALESCE(d.sex, '未知') AS gender, COUNT(*) AS count "
            + " FROM detection_records r "
            + " LEFT JOIN drivers d ON r.license_plate = d.license_plate "
            + " WHERE r.helmet = 0 "
            + " GROUP BY gender")
    List<Map<String, Object>> countNoHelmetByGender();

    /**
     * 统计每天不戴头盔人数
     * @return List<Map>，每个Map包含date和count
     */
    @Select("SELECT DATE_FORMAT(r.detection_time, '%Y-%m-%d') AS date, COUNT(*) AS count "
            + " FROM detection_records r "
            + " WHERE r.helmet = 0 "
            + " GROUP BY date ORDER BY date")
    List<Map<String, Object>> countNoHelmetByDay();

    /**
     * 统计不同地区不戴头盔人数
     * @return List<Map>，每个Map包含region和count
     */
    @Select("SELECT COALESCE(r.detection_address, '未知') AS region, COUNT(*) AS count "
            + " FROM detection_records r "
            + " WHERE r.helmet = 0 "
            + " GROUP BY region")
    List<Map<String, Object>> countNoHelmetByRegion();
}
