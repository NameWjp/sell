package com.wangjp.sell.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/28 9:21 下午
 * @detail 全局枚举效验器
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = EnumValueValidator.Validator.class)
public @interface EnumValueValidator {

    // 定义效验失败的错误消息
    String message() default "参数验证失败";

    // 保存需要效验的枚举类
    Class<? extends Enum<?>> enumClass();

    // 定义获取值的方法
    String getValueMethodName() default "getCode";

    /**
     * groups 和 payload 是验证必带的参数
     */
    // 指定这个约束条件属于哪个校验组，进而可以控制效验的时机，参考：https://juejin.cn/post/6844903902811275278#heading-10
    Class<?>[] groups() default {};

    // 通过此属性指定约束条件的严重级别
    Class<? extends Payload>[] payload() default {};


    class Validator implements ConstraintValidator<EnumValueValidator, Object> {

        private Class<? extends Enum<?>> enumClass;

        private String getValueMethodName;

        // 枚举类的所有值
        private List<Object> values = new ArrayList<>();

        @Override
        public void initialize(EnumValueValidator constraintAnnotation) {
            enumClass = constraintAnnotation.enumClass();
            getValueMethodName = constraintAnnotation.getValueMethodName();

            // 获取枚举对象的所有枚举值
            Object[] objects = enumClass.getEnumConstants();
            try {
                Method method = enumClass.getMethod(getValueMethodName);
                if (Objects.isNull(method)) {
                    throw new RuntimeException(String.format("枚举对象 %s 缺少名为 %s 的方法", enumClass.getName(), getValueMethodName));
                }
                Object value;
                for (Object obj : objects) {
                    value = method.invoke(obj);
                    values.add(value);
                }
            } catch (Exception ignored) {}
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value instanceof String) {
                String valueStr = (String)value;
                return valueStr.isEmpty() || values.contains(value);
            }
            return Objects.isNull(value) || values.contains(value);
        }
    }
}


