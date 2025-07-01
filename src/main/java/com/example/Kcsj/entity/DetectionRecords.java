package com.example.Kcsj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@TableName("detection_records")  //映射数据库表detection_records
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetectionRecords {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String licensePlate;

    private Boolean helmet;  // 0表示未戴头盔，1表示戴头盔

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date detectionTime;

    private String detectionAddress;

    public String getDetectionAddress() {
        return detectionAddress;
    }

    public void setDetectionAddress(String detectionAddress) {
        this.detectionAddress = detectionAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Boolean getHelmet() {
        return helmet;
    }

    public void setHelmet(Boolean helmet) {
        this.helmet = helmet;
    }

    public Date getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(Date detectionTime) {
        this.detectionTime = detectionTime;
    }
}
