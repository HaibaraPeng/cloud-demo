package com.roc.demo.modules.system.controller;

import com.alibaba.nacos.common.utils.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roc.demo.common.core.api.CommonResult;
import com.roc.demo.common.core.dto.system.user.UserCreateDTO;
import com.roc.demo.common.core.dto.system.user.UserPageDTO;
import com.roc.demo.common.core.dto.system.user.UserUpdateDTO;
import com.roc.demo.common.core.vo.system.user.UserDetailVO;
import com.roc.demo.common.core.vo.system.user.UserPageVO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 用户信息
 * @Author dongp
 * @Date 2022/6/24 0024 14:27
 */
@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private UserService userService;
//
//    /**
//     * 获取用户信息分页列表
//     *
//     * @param dto
//     * @return
//     */
//    @PostMapping("/page")
//    public CommonResult<Page<UserPageVO>> page(@RequestBody @Validated UserPageDTO dto) {
//        Page<UserPageVO> page = userService.getUserPage(dto);
//        return new CommonResult<>().success().data(page);
//    }
//
//    /**
//     * 根据Id获取用户信息详情
//     */
//    @GetMapping(path = "/{id}")
//    public CommonResult<UserDetailVO> id(@PathVariable(value = "id") String id) {
//        UserDetailVO vo = userService.getUserDetailById(id);
//        return new CommonResult<>().success().data(vo);
//    }
//
//    /**
//     * 创建用户信息
//     *
//     * @param dto
//     * @return
//     */
//    @PostMapping
//    public CommonResult<String> create(@RequestBody @Validated UserCreateDTO dto) {
//        String id = userService.createUser(dto);
//        return new CommonResult<>().success().data(id);
//    }
//
//    /**
//     * 修改用户信息
//     */
//    @PutMapping
//    public CommonResult update(@RequestBody @Validated UserUpdateDTO dto) {
//        userService.updateUser(dto);
//        return new CommonResult<>().success();
//    }
//
//    /**
//     * 删除用户信息
//     */
//    @DeleteMapping("/{ids}")
//    public CommonResult delete(@PathVariable String[] ids) {
//        userService.deleteUserByIds(ids);
//        return new CommonResult<>().success();
//    }

}
