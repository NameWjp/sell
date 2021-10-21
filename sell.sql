/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 47.98.163.77:3306
 Source Schema         : sell

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 21/10/2021 17:27:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `parent_id` int(11) NOT NULL COMMENT '父节点id，为0是根节点',
  `parent_ids` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有父节点，用于判断当前层级',
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `dict_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `dict_value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 0, '0', 'base_code', '基础代码', 'base_code', '2021-05-23 18:14:03', '2021-06-06 18:07:45');
INSERT INTO `dict` VALUES (2, 0, '0', 'system_code', '系统代码', 'system_code', '2021-05-23 18:15:20', '2021-05-23 18:15:20');
INSERT INTO `dict` VALUES (3, 1, '0,1', 'is_enable', '是否启用', 'is_enable', '2021-05-23 18:16:37', '2021-05-23 18:16:37');
INSERT INTO `dict` VALUES (4, 3, '0,1,3', 'is_enable', '启用', '1', '2021-05-23 18:18:00', '2021-05-23 18:18:00');
INSERT INTO `dict` VALUES (5, 3, '0,1,3', 'is_enable', '停用', '2', '2021-05-23 18:18:26', '2021-06-01 23:32:44');
INSERT INTO `dict` VALUES (17, 1, '0,1', 'menu_type', '菜单类型', 'menu_type', '2021-06-12 23:55:34', '2021-06-12 23:55:34');
INSERT INTO `dict` VALUES (18, 17, '0,1,17', 'menu_type', '页面', '1', '2021-06-12 23:59:15', '2021-06-12 23:59:15');
INSERT INTO `dict` VALUES (20, 17, '0,1,17', 'menu_type', '按钮', '2', '2021-06-13 00:05:08', '2021-06-13 00:05:08');
INSERT INTO `dict` VALUES (22, 2, '0,2', 'product_status', '商品状态', 'product_status', '2021-10-19 17:11:57', '2021-10-19 17:11:57');
INSERT INTO `dict` VALUES (23, 22, '0,2,22', 'product_status', '正常', '1', '2021-10-19 17:12:12', '2021-10-19 17:12:12');
INSERT INTO `dict` VALUES (24, 22, '0,2,22', 'product_status', '下架', '2', '2021-10-19 17:12:21', '2021-10-19 17:12:21');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `type` tinyint(3) NOT NULL COMMENT '菜单类型 1页面 2按钮',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(11) NOT NULL COMMENT '父节点id，为0是根节',
  `sort` int(11) NOT NULL COMMENT '排序',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/dashboard', 1, 'dashboard', '首页', 0, 10, 'dashboard', '2021-06-13 00:44:44', '2021-06-20 18:33:18');
INSERT INTO `menu` VALUES (2, '/system-config', 1, 'setting', '系统配置', 0, 20, 'system-config', '2021-06-20 17:17:32', '2021-06-20 17:17:32');
INSERT INTO `menu` VALUES (4, '/system-config/account-manage', 1, NULL, '账户管理', 2, 10, 'system-config-account-manage', '2021-06-20 18:36:58', '2021-06-20 18:37:53');
INSERT INTO `menu` VALUES (5, '/system-config/dict-manage', 1, NULL, '字典管理', 2, 20, 'system-config-dict-manage', '2021-06-20 18:37:31', '2021-06-20 18:37:31');
INSERT INTO `menu` VALUES (6, '/system-config/role-manage', 1, NULL, '角色管理', 2, 30, 'system-config-role-manage', '2021-06-20 18:38:23', '2021-06-20 18:38:32');
INSERT INTO `menu` VALUES (7, '/system-config/menu-manage', 1, NULL, '菜单管理', 2, 40, 'system-config-menu-manage', '2021-06-20 18:39:12', '2021-06-20 18:39:12');
INSERT INTO `menu` VALUES (11, '/system-config/organ-manage', 1, NULL, '组织机构管理', 2, 50, 'system-config-organ-manage', '2021-06-26 15:20:39', '2021-06-26 15:20:39');
INSERT INTO `menu` VALUES (12, NULL, 2, NULL, '列表', 4, 10, 'user:list', '2021-10-11 14:29:46', '2021-10-11 14:40:03');
INSERT INTO `menu` VALUES (13, NULL, 2, NULL, '新增', 4, 10, 'user:create', '2021-10-11 14:30:04', '2021-10-11 14:38:24');
INSERT INTO `menu` VALUES (14, NULL, 2, NULL, '修改', 4, 10, 'user:update', '2021-10-11 14:30:47', '2021-10-11 14:38:16');
INSERT INTO `menu` VALUES (15, NULL, 2, NULL, '删除', 4, 10, 'user:delete', '2021-10-11 14:31:03', '2021-10-11 14:38:08');
INSERT INTO `menu` VALUES (16, NULL, 2, NULL, '列表', 5, 10, 'dict:list', '2021-10-11 14:49:28', '2021-10-11 14:50:27');
INSERT INTO `menu` VALUES (17, NULL, 2, NULL, '新增', 5, 10, 'dict:create', '2021-10-11 14:49:39', '2021-10-11 14:50:17');
INSERT INTO `menu` VALUES (18, NULL, 2, NULL, '修改', 5, 10, 'dict:update', '2021-10-11 14:49:56', '2021-10-11 14:49:56');
INSERT INTO `menu` VALUES (19, NULL, 2, NULL, '删除', 5, 10, 'dict:delete', '2021-10-11 14:50:08', '2021-10-11 14:50:08');
INSERT INTO `menu` VALUES (20, NULL, 2, NULL, '列表', 6, 10, 'role:list', '2021-10-11 14:52:23', '2021-10-11 14:52:23');
INSERT INTO `menu` VALUES (21, NULL, 2, NULL, '新增', 6, 10, 'role:create', '2021-10-11 14:52:34', '2021-10-11 14:52:34');
INSERT INTO `menu` VALUES (22, NULL, 2, NULL, '修改', 6, 10, 'role:update', '2021-10-11 14:52:45', '2021-10-11 14:52:45');
INSERT INTO `menu` VALUES (23, NULL, 2, NULL, '删除', 6, 10, 'role:delete', '2021-10-11 14:52:57', '2021-10-11 14:52:57');
INSERT INTO `menu` VALUES (24, NULL, 2, NULL, '列表', 7, 10, 'menu:list', '2021-10-11 14:56:03', '2021-10-11 14:56:03');
INSERT INTO `menu` VALUES (25, NULL, 2, NULL, '新增', 7, 10, 'menu:create', '2021-10-11 14:56:12', '2021-10-11 14:56:12');
INSERT INTO `menu` VALUES (26, NULL, 2, NULL, '修改', 7, 10, 'menu:update', '2021-10-11 14:56:25', '2021-10-11 14:56:25');
INSERT INTO `menu` VALUES (27, NULL, 2, NULL, '删除', 7, 10, 'menu:delete', '2021-10-11 14:56:36', '2021-10-11 14:56:36');
INSERT INTO `menu` VALUES (28, NULL, 2, NULL, '列表', 11, 10, 'organ:list', '2021-10-11 14:57:27', '2021-10-11 14:57:27');
INSERT INTO `menu` VALUES (29, NULL, 2, NULL, '新增', 11, 10, 'organ:create', '2021-10-11 14:57:35', '2021-10-11 14:57:35');
INSERT INTO `menu` VALUES (30, NULL, 2, NULL, '修改', 11, 10, 'organ:update', '2021-10-11 14:57:46', '2021-10-11 14:57:46');
INSERT INTO `menu` VALUES (31, NULL, 2, NULL, '删除', 11, 10, 'organ:delete', '2021-10-11 14:58:00', '2021-10-11 14:58:00');
INSERT INTO `menu` VALUES (33, '/product-manage', 1, 'product', '商品管理', 0, 30, 'product-manage', '2021-10-17 18:00:26', '2021-10-17 18:00:26');
INSERT INTO `menu` VALUES (34, '/product-manage/product-category', 1, NULL, '商品类型管理', 33, 10, 'product-manage-product-category', '2021-10-17 18:19:31', '2021-10-17 18:19:31');
INSERT INTO `menu` VALUES (35, NULL, 2, NULL, '列表', 34, 10, 'category:list', '2021-10-17 18:20:28', '2021-10-17 19:02:04');
INSERT INTO `menu` VALUES (36, NULL, 2, NULL, '新增', 34, 10, 'category:create', '2021-10-17 18:20:55', '2021-10-17 19:01:52');
INSERT INTO `menu` VALUES (37, NULL, 2, NULL, '修改', 34, 10, 'category:update', '2021-10-17 18:21:08', '2021-10-17 19:01:40');
INSERT INTO `menu` VALUES (38, NULL, 2, NULL, '删除', 34, 10, 'category:delete', '2021-10-17 18:21:24', '2021-10-17 19:01:29');
INSERT INTO `menu` VALUES (39, '/product-manage/product-info', 1, NULL, '商品管理', 33, 20, 'product-manage-product-info', '2021-10-19 16:47:50', '2021-10-19 16:47:50');
INSERT INTO `menu` VALUES (40, NULL, 2, NULL, '列表', 39, 10, 'productInfo:list', '2021-10-19 16:48:21', '2021-10-19 16:48:21');
INSERT INTO `menu` VALUES (41, NULL, 2, NULL, '新增', 39, 10, 'productInfo:create', '2021-10-19 16:48:28', '2021-10-19 16:48:28');
INSERT INTO `menu` VALUES (42, NULL, 2, NULL, '修改', 39, 10, 'productInfo:update', '2021-10-19 16:48:44', '2021-10-19 16:48:44');
INSERT INTO `menu` VALUES (43, NULL, 2, NULL, '删除', 39, 10, 'productInfo:delete', '2021-10-19 16:48:54', '2021-10-19 16:48:54');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单详情id',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单id',
  `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `product_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `product_quantity` int(11) NOT NULL COMMENT '商品数量',
  `product_icon` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品小图',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1618676566729135962', '1618676566658352534', '1', '皮蛋粥', 3.20, 1, 'http://test.png', '2021-04-18 00:22:47', '2021-04-18 00:22:47');
INSERT INTO `order_detail` VALUES ('1618676721851241161', '1618676721780842547', '1', '皮蛋粥', 3.20, 3, 'http://test.png', '2021-04-18 00:25:22', '2021-04-18 00:25:22');
INSERT INTO `order_detail` VALUES ('1618929242627325525', '1618929242555931603', '1', '皮蛋粥', 3.20, 3, 'http://test.png', '2021-04-20 22:34:03', '2021-04-20 22:34:03');

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单id',
  `buyer_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家名称',
  `buyer_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8, 2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL COMMENT '订单状态: 1新下单 2完结 3已取消',
  `pay_status` tinyint(3) NOT NULL COMMENT '支付状态: 1待支付 2支付成功',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_buyer_openid`(`buyer_openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('1618676566658352534', '周杰伦', '15555555555', '杭州市西湖区', '110110', 3.20, 2, 0, '2021-04-18 00:22:47', '2021-04-20 22:10:28');
INSERT INTO `order_master` VALUES ('1618676721780842547', '周杰伦', '15555555555', '杭州市西湖区', '110110', 9.60, 1, 0, '2021-04-18 00:25:22', '2021-04-20 22:24:25');
INSERT INTO `order_master` VALUES ('1618929242555931603', '林俊杰', '15555555555', '杭州市西湖区三墩镇', '110110', 9.60, 0, 1, '2021-04-20 22:34:03', '2021-04-20 22:37:58');

-- ----------------------------
-- Table structure for organ
-- ----------------------------
DROP TABLE IF EXISTS `organ`;
CREATE TABLE `organ`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '组织机构id',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organ
-- ----------------------------
INSERT INTO `organ` VALUES (1, 0, '浙江省', '2021-06-21 22:36:25', '2021-06-21 22:36:25');
INSERT INTO `organ` VALUES (2, 1, '杭州市', '2021-06-21 22:36:33', '2021-06-21 22:36:33');
INSERT INTO `organ` VALUES (3, 2, '西湖区', '2021-06-21 22:36:40', '2021-06-21 22:36:40');
INSERT INTO `organ` VALUES (5, 2, '上城区', '2021-10-11 16:39:22', '2021-10-11 16:39:22');
INSERT INTO `organ` VALUES (6, 2, '下城区', '2021-10-11 16:41:38', '2021-10-11 16:41:38');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '类目id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类目名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类目编号',
  `sort` int(11) NOT NULL COMMENT '类目排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '类目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (7, '男士最爱', 'men_love', 10, '2021-10-19 17:09:30', '2021-10-19 17:09:30');
INSERT INTO `product_category` VALUES (8, '女士最爱', 'woman_love', 10, '2021-10-19 17:09:54', '2021-10-19 17:09:54');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `product_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(8, 2) NOT NULL COMMENT '商品单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '小图',
  `product_status` tinyint(3) NOT NULL COMMENT '商品状态: 1正常 2下架',
  `category_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类目编号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `create_id` int(11) NOT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (5, '系统管理员', '系统管理员', '2021-06-26 16:57:35', '2021-06-26 16:57:35', 27);
INSERT INTO `role` VALUES (11, '测试角色', '测试角色', '2021-10-09 14:09:47', '2021-10-09 14:09:47', 28);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色菜单表id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (27, 5, 1);
INSERT INTO `role_menu` VALUES (42, 5, 2);
INSERT INTO `role_menu` VALUES (43, 5, 4);
INSERT INTO `role_menu` VALUES (44, 5, 5);
INSERT INTO `role_menu` VALUES (45, 5, 6);
INSERT INTO `role_menu` VALUES (46, 5, 7);
INSERT INTO `role_menu` VALUES (47, 5, 11);
INSERT INTO `role_menu` VALUES (65, 11, 1);
INSERT INTO `role_menu` VALUES (66, 11, 2);
INSERT INTO `role_menu` VALUES (67, 11, 4);
INSERT INTO `role_menu` VALUES (68, 11, 5);
INSERT INTO `role_menu` VALUES (69, 5, 12);
INSERT INTO `role_menu` VALUES (70, 5, 13);
INSERT INTO `role_menu` VALUES (71, 5, 14);
INSERT INTO `role_menu` VALUES (72, 5, 15);
INSERT INTO `role_menu` VALUES (73, 5, 16);
INSERT INTO `role_menu` VALUES (74, 5, 17);
INSERT INTO `role_menu` VALUES (75, 5, 18);
INSERT INTO `role_menu` VALUES (76, 5, 19);
INSERT INTO `role_menu` VALUES (77, 5, 20);
INSERT INTO `role_menu` VALUES (78, 5, 21);
INSERT INTO `role_menu` VALUES (79, 5, 22);
INSERT INTO `role_menu` VALUES (80, 5, 23);
INSERT INTO `role_menu` VALUES (81, 5, 24);
INSERT INTO `role_menu` VALUES (82, 5, 25);
INSERT INTO `role_menu` VALUES (83, 5, 26);
INSERT INTO `role_menu` VALUES (84, 5, 27);
INSERT INTO `role_menu` VALUES (85, 5, 28);
INSERT INTO `role_menu` VALUES (86, 5, 29);
INSERT INTO `role_menu` VALUES (87, 5, 30);
INSERT INTO `role_menu` VALUES (88, 5, 31);
INSERT INTO `role_menu` VALUES (96, 11, 12);
INSERT INTO `role_menu` VALUES (97, 11, 16);
INSERT INTO `role_menu` VALUES (98, 11, 6);
INSERT INTO `role_menu` VALUES (99, 11, 20);
INSERT INTO `role_menu` VALUES (100, 11, 7);
INSERT INTO `role_menu` VALUES (101, 11, 24);
INSERT INTO `role_menu` VALUES (102, 11, 11);
INSERT INTO `role_menu` VALUES (103, 11, 28);
INSERT INTO `role_menu` VALUES (105, 5, 33);
INSERT INTO `role_menu` VALUES (106, 5, 34);
INSERT INTO `role_menu` VALUES (107, 5, 35);
INSERT INTO `role_menu` VALUES (108, 5, 36);
INSERT INTO `role_menu` VALUES (109, 5, 37);
INSERT INTO `role_menu` VALUES (110, 5, 38);
INSERT INTO `role_menu` VALUES (111, 5, 39);
INSERT INTO `role_menu` VALUES (112, 5, 40);
INSERT INTO `role_menu` VALUES (113, 5, 41);
INSERT INTO `role_menu` VALUES (114, 5, 42);
INSERT INTO `role_menu` VALUES (115, 5, 43);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `organ_id` int(11) NOT NULL COMMENT '组织机构id',
  `is_enable` tinyint(3) NOT NULL DEFAULT 1 COMMENT '是否启用 1启用 2停用',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (27, 'admin', '$2a$10$o3Upla0/g3gMsUMzBSLT6eK2p2G7YxDbLxOQb/P4tFjBJYhuFlC0u', 1, 1, '2021-06-29 22:03:45', '2021-10-11 16:46:57');
INSERT INTO `user` VALUES (28, 'test', '$2a$10$lhGtfSqLaLLLly.Kml6aeeQFWkFW05l2I94Cc9zOfBfNxMhTj9SXO', 5, 1, '2021-10-09 14:10:41', '2021-10-11 16:41:59');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户角色表id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (9, 27, 5);
INSERT INTO `user_role` VALUES (11, 28, 11);

SET FOREIGN_KEY_CHECKS = 1;
