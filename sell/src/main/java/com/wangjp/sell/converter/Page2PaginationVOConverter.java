package com.wangjp.sell.converter;

import com.wangjp.sell.vo.PaginationVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/22 4:44 下午
 * @detail
 */
public class Page2PaginationVOConverter {

    public static <T> PaginationVO<T> convert(Page<T> page) {
        int pageNum = page.getNumber() + 1;
        int pageSize = page.getSize();
        List<T> list = page.getContent();
        Long total = page.getTotalElements();
        Boolean hasNextPage = page.getTotalPages() > pageNum;

        PaginationVO<T> paginationVO = new PaginationVO<>();
        paginationVO.setList(list);
        paginationVO.setPageNum(pageNum);
        paginationVO.setPageSize(pageSize);
        paginationVO.setTotal(total);
        paginationVO.setHasNextPage(hasNextPage);

        return paginationVO;
    }
}
