package com.wangjp.sell.groups;

import javax.validation.groups.Default;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/29 9:19 下午
 * @detail 定义修改分组，注意一定要继承 Default，因为默认分组是 Default，不加会导致默认效验失效
 */
public interface Update extends Default {
}
