package com.roc.demo.common.core.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description BasePO
 * @Author dongp
 * @Date 2022/6/27 0027 14:00
 */
@Setter
@Getter
public class BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时操作
     */
    public void preCreate(String userId) {
        this.setCreateBy(userId);
        this.setCreateTime(LocalDateTime.now());
    }

    /**
     * 创建时操作 TODO 获取当前操作人
     */
    public void preCreate() {
        this.preCreate("1");
    }

    /**
     * 更新时操作
     */
    public void preUpdate(String userId) {
        this.setUpdateBy(userId);
        this.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 更新时操作 TODO 获取当前操作人
     */
    public void preUpdate() {
        this.setUpdateBy("1");
        this.setUpdateTime(LocalDateTime.now());
    }

}
