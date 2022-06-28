package com.roc.demo.common.core.dto.generate.table;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description TableListDTO
 * @Author dongp
 * @Date 2022/6/27 0027 11:43
 */
@Getter
@Setter
public class TableBatchGenerateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空")
    private String tables;

}
