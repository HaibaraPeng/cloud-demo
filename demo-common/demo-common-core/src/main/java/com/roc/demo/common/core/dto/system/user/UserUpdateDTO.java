package com.roc.demo.common.core.dto.system.user;

import com.roc.demo.common.core.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description UserUpdateDTO
 * @Author dongp
 * @Date 2022/6/27 0027 11:43
 */
@Getter
@Setter
public class UserUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

}
