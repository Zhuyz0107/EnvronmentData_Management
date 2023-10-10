package com.briup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 环境存储实体类,包括环境种类(温度,湿度,二氧化碳,光照强度)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Environment implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 环境种类名称
     */
    private String name;

    /**
     * 发送端id
     */
    private String srcId;

    /**
     * 树莓派系统id
     */
    private String desId;

    /**
     * 实验箱区域模块id(1-8)
     */
    private String devId;

    /**
     * 模块上传感器地址
     */
    private String sensorAddress;

    /**
     * 传感器个数
     */
    private int count;

    /**
     * 发送指令标号 3表示接收数据 16表示发送命令
     */
    private String cmd;

    /**
     * 状态 默认1表示成功
     */
    private int status;

    /**
     * 环境值
     */
    private float data;

    /**
     * 采集时间
     */
    private Timestamp gatherDate;

    public Environment( String srcId, String desId, String devId,
                        String sensorAddress, int count, String cmd, int status,Timestamp gatherDate) {

        this.srcId = srcId;
        this.desId = desId;
        this.devId = devId;
        this.sensorAddress = sensorAddress;
        this.count = count;
        this.cmd = cmd;
        this.status = status;
        this.gatherDate = gatherDate;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Environment clone = (Environment) super.clone();
        return clone;
    }
}





