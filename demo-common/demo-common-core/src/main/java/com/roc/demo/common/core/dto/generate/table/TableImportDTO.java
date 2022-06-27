package com.roc.demo.common.core.dto.generate.table;

import com.roc.demo.common.core.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Description TableListDTO
 * @Author dongp
 * @Date 2022/6/27 0027 11:43
 */
@Getter
@Setter
public class TableImportDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "导入表项目不能为空")
    @Valid
    private List<ItemDTO> itemList;

    @Getter
    @Setter
    public static class ItemDTO implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 表名称
         */
        @NotBlank(message = "表名称不能为空")
        private String tableName;

        /**
         * 表描述
         */
        private String tableComment;
    }

}
