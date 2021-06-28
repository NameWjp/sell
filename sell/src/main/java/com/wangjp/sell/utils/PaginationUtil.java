package com.wangjp.sell.utils;

import com.wangjp.sell.vo.PaginationVO;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 8:42 下午
 * @detail
 */
public class PaginationUtil {
    public static <T> PaginationVO<T> genNotPaging(List<T> list) {
        PaginationVO<T> paginationVO = new PaginationVO<>();

        paginationVO.setList(list);
        paginationVO.setPageNum(1);
        paginationVO.setPageSize(0);
        paginationVO.setHasNextPage(false);
        paginationVO.setTotal(list.size());

        return paginationVO;
    }
}
