package com.roc.demo.modules.system.po;

import com.roc.demo.common.core.base.BasePO;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description UserPO
 * @Author dongp
 * @Date 2022/6/28 0028 16:56
 */
public class UserPO extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;
}
