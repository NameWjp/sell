package com.wangjp.sell.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/24 9:37 下午
 * @detail 树转换方法
 */
public class TreeUtil {

    /**
     * 获取树前几级
     * @param collection tree 集合
     * @param clazz      集合元素类型
     * @param level      展示层级，从第一级开始
     * @param <T>        泛型
     * @return           新的 tree 集合
     */
    public static <T> Collection<T> getSubTree(@NotNull Collection<T> collection, @NotNull Class<T> clazz, @NotNull Integer level) {
        return getSubTree(collection, null, clazz, level);
    }

    /**
     * 获取树前几级
     * @param collection tree 集合
     * @param children   子节点集合属性名称
     * @param clazz      集合元素类型
     * @param level      展示层级
     * @param <T>        泛型
     * @return           新的 tree 集合
     */
    public static <T> Collection<T> getSubTree(@NotNull Collection<T> collection, String children, @NotNull Class<T> clazz, @NotNull Integer level) {
        try {
            if (StringUtils.isEmpty(children)) children = "children";

            // 初始化根节点集合，支持 set 和 list
            Collection<T> result;
            if (collection.getClass().isAssignableFrom(Set.class)) {
                result = new HashSet<>();
            } else {
                result = new ArrayList<>();
            }

            Gson gson = new Gson();
            for (T c : collection) {
                result.add(gson.fromJson(gson.toJson(c), clazz));
            }

            // 获取 children 字段，从当前对象或其父类
            Field childrenField = getField(clazz, children);
            childrenField.setAccessible(true);

            setTreeChildrenByLevel(result, childrenField, level, 1);

            childrenField.setAccessible(false);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 获取树前几级
     * @param collection    树集合
     * @param childrenField children 字段
     * @param targetLevel   目标层级
     * @param currentLevel  当前层级
     * @param <T>           泛型
     * @return              新的树集合
     * @throws IllegalAccessException
     */
    public static <T> void setTreeChildrenByLevel(
            @NotNull Collection<T> collection,
            @NotNull Field childrenField,
            @NotNull Integer targetLevel,
            @NotNull Integer currentLevel
    ) throws IllegalAccessException {
        for (T node : collection) {
            Collection<T> children = (Collection<T>) childrenField.get(node);
            if (currentLevel.equals(targetLevel)) {
                if (children.getClass().isAssignableFrom(Set.class)) {
                    children = new HashSet<>();
                } else {
                    children = new ArrayList<>();
                }
                childrenField.set(node, children);
            } else {
                setTreeChildrenByLevel(children, childrenField, targetLevel, currentLevel + 1);
            }
        }
    }

    /**
     * 集合转树结构
     * @param collection 目标集合
     * @param clazz      集合元素类型
     * @param <T>        泛型
     * @return           转换后的树结构
     */
    public static <T> Collection<T> list2Tree(@NotNull Collection<T> collection, @NotNull Class<T> clazz) {
        return list2Tree(collection, null, null, null, clazz);
    }

    /**
     * 集合转树结构
     * @param collection 目标集合
     * @param id         节点 id 名称
     * @param parentId   父节点 id 名称
     * @param children   子节点集合属性名称
     * @param clazz      集合元素类型
     * @param <T>        泛型
     * @return           转换后的树结构
     */
    public static <T> Collection<T> list2Tree(
            @NotNull Collection<T> collection,
            String id,
            String parentId,
            String children,
            @NotNull Class<T> clazz
    ) {
        try {
            if (collection == null || collection.isEmpty()) return null;
            if (StringUtils.isEmpty(id)) id = "id";
            if (StringUtils.isEmpty(parentId)) parentId = "parentId";
            if (StringUtils.isEmpty(children)) children = "children";

            // 初始化根节点集合，支持 set 和 list
            Collection<T> roots;
            if (collection.getClass().isAssignableFrom(Set.class)) {
                roots = new HashSet<>();
            } else {
                roots = new ArrayList<>();
            }

            // 获取 id 字段，从当前对象或其父类
            Field idField = getField(clazz, id);

            // 获取 parentId 字段，从当前对象或其父类
            Field parentIdField = getField(clazz, parentId);

            // 获取 children 字段，从当前对象或其父类
            Field childrenField = getField(clazz, children);

            // 设置可访问
            idField.setAccessible(true);
            parentIdField.setAccessible(true);
            childrenField.setAccessible(true);

            // 找出所有根节点
            for (T c : collection) {
                Object parentIdFieldValue = parentIdField.get(c);
                if (isRootNode(parentIdFieldValue)) {
                    roots.add(c);
                }
            }

            // 从目标集合移除根节点
            collection.removeAll(roots);

            // 遍历根节点，依次添加子节点
            for (T root : roots) {
                addChild(root, collection, idField, parentIdField, childrenField);
            }

            // 关闭可访问
            idField.setAccessible(false);
            parentIdField.setAccessible(false);
            childrenField.setAccessible(false);

            return roots;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 为目标节点添加孩子节点
     * @param node          目标节点
     * @param collection    目标集合
     * @param idField       id 字段
     * @param parentIdField 父节点 id 字段
     * @param childrenField 子节点字段
     * @param <T>           泛型
     */
    private static <T> void addChild(
            @NotNull T node,
            @NotNull Collection<T> collection,
            @NotNull Field idField,
            @NotNull Field parentIdField,
            @NotNull Field childrenField
    ) throws IllegalAccessException {
        Object id = idField.get(node);
        Collection<T> children = (Collection<T>) childrenField.get(node);
        // 如果子节点的集合为 null，初始化集合
        if (children == null) {
            if (collection.getClass().isAssignableFrom(Set.class)) {
                children = new HashSet<>();
            } else {
                children = new ArrayList<>();
            }
            childrenField.set(node, children);
        }

        for (T t : collection) {
            Object o = parentIdField.get(t);
            if (id.equals(o)) {
                children.add(t);
                // 递归遍历孩子节点
                addChild(t, collection, idField, parentIdField, childrenField);
            }
        }
    }

    /**
     * 判断是否为根节点, 判断方式为: 父节点编号为空或为 0, 则认为是根节点
     * @param parentId  父节点id
     * @return          是否为根节点的布尔值
     */
    private static boolean isRootNode(Object parentId) {
        boolean flag = false;
        if (parentId == null) {
            flag = true;
        } else if (parentId instanceof String && (StringUtils.isEmpty((String)parentId) || parentId.equals("0"))) {
            flag = true;
        } else if (parentId instanceof Integer && Integer.valueOf(0).equals(parentId)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取 Field
     * @param clazz 类型
     * @param key   名称
     * @param <T>   泛型
     * @return      Field 对象
     * @throws NoSuchFieldException
     */
    private static <T> Field getField(Class<T> clazz, String key) throws NoSuchFieldException {
        Field field;
        try {
            field = clazz.getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            field = clazz.getSuperclass().getDeclaredField(key);
        }
        return field;
    }
}
