package com.roc.demo.common.core.vo.system.user;

import com.roc.demo.common.core.base.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Description DbListVO
 * @Author dongp
 * @Date 2022/6/27 0027 14:38
 */
@Getter
@Setter
public class UserPageVO extends BaseVO {

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
