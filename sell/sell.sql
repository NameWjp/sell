create table `product_info` (
	`product_id` varchar(32) not null comment '商品id',
	`product_name` varchar(64) not null comment '商品名称',
	`product_price` decimal(8, 2) not null comment '商品单价',
	`product_stock` int not null comment '库存',
	`product_description` varchar(64) comment '描述',
	`product_icon` varchar(512) comment '小图',
	`product_code` tinyint(3) default '0' comment '商品状态: 0正常 1下架',
	`category_type` int not null comment '类目编号',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '修改时间',
	primary key (`product_id`)
) comment '商品表';

create table `product_category` (
	`category_id` int not null auto_increment comment '类目id',
	`category_name` varchar(64) not null comment '类目名称',
	`category_type` int not null comment '类目编号',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '修改时间',
	primary key (`category_id`),
	unique key `uqe_category_type` (`category_type`)
) comment '类目表';

create table `order_master` (
	`order_id` varchar(32) not null comment '订单id',
	`buyer_name` varchar(32) not null comment '买家名称',
	`buyer_phone` varchar(32) not null comment '买家电话',
	`buyer_address` varchar(128) not null comment '买家地址',
	`buyer_openid` varchar(64) not null comment '买家微信openid',
	`order_amount` decimal(8,2) not null comment '订单总金额',
	`order_status` tinyint(3) not null comment '订单状态，0: 新下单',
	`pay_status` tinyint(3) not null comment '支付状态，0: 未支付',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '修改时间',
	primary key (`order_id`),
	key `idx_buyer_openid` (`buyer_openid`)
) comment '订单表';

create table `order_detail` (
	`detail_id` varchar(32) not null comment '订单详情id',
	`order_id` varchar(32) not null comment '订单id',
	`product_id` varchar(32) not null comment '商品id',
	`product_name` varchar(64) not null comment '商品名称',
	`product_price` decimal(8,2) not null comment '商品价格',
	`product_quantity` int not null comment '商品数量',
	`product_icon` varchar(512) comment '商品小图',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '修改时间',
	primary key (`detail_id`),
	key `idx_product_id` (`product_id`)
) comment '订单详情表';