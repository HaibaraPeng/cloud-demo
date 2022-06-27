package com.roc.demo.common.core.validated;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description ValidatedList
 * @Author dongp
 * @Date 2022/6/27 0027 18:42
 */
@Getter
@Setter
public class ValidatedList<E> {

    @Valid
    @NotEmpty(message = "列表不能为空")
    private List<E> list;

    public ValidatedList() {
        list = new ArrayList<>();
    }

    public ValidatedList(List<E> list) {
        this.list = list;
    }
}
