package com.wangjp.sell.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/22 4:22 下午
 * @detail 分页对象
 */
@Data
public class PaginationVO<T> {

    // 是否有下一页
    private Boolean hasNextPage;

    // 分页内容
    private List<T> list;

    // 当前页码
    private Number pageNum;

    // 分页大小
    private Number pageSize;

    // 总数
    private Number total;
}
