package com.xianglan.qnytv.domain.base;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class PageResult<T> {

    private Integer total;

    private List<T> list;

    public PageResult(Integer total, List<T> list){
        this.total = total;
        this.list = list;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
