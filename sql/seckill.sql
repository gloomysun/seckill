CREATE TABLE seckill_user (
  id            BIGINT(20) PRIMARY KEY,
  nickname      VARCHAR(32) NOT NULL DEFAULT "",
  password      VARCHAR(32) NOT NULL DEFAULT "",
  salt          VARCHAR(32) NOT NULL DEFAULT "",
  head          VARCHAR(32) NOT NULL DEFAULT "",
  registerDate  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  lastLoginDate DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  loginCount    INT(11) NOT NULL DEFAULT 0
);
CREATE TABLE goods (
  id          BIGINT(20) PRIMARY KEY COMMENT '商品id',
  goods_name   VARCHAR(32)  NOT NULL DEFAULT "" COMMENT '商品名称',
  goods_title  VARCHAR(32) NOT NULL DEFAULT "" COMMENT '商品标题',
  goods_img    VARCHAR(128) NOT NULL DEFAULT "" COMMENT '商品图片',
  goods_detail LONGTEXT NOT NULL DEFAULT "" COMMENT '商品介绍',
  goods_price  DECIMAL(10,2)   DEFAULT '0.00' COMMENT '价格',
  goods_stock  INT(11)      DEFAULT '0' COMMENT '库存'
);
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `delivery_addr_id` BIGINT(20) NOT NULL DEFAULT '' COMMENT '地址',
  `goods_name` varchar(32) NOT NULL DEFAULT '' COMMENT '商品名(冗余字段)',
  `goods_count` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `order_channel` tinyint(4) NOT NULL COMMENT '订单平台（1-pc，2-ios，3-安卓）',
  `status` tinyint(4) NOT NULL COMMENT '状态（0新建未支付，1已支付，2已发货，3已收货）',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE seckill_goods(
  id BIGINT(20) PRIMARY KEY COMMENT '秒杀商品id',
  goods_id BIGINT(20) COMMENT '商品id',
  seckill_price DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '秒杀价',
  stock_count INT(11) NOT NULL DEFAULT 0 COMMENT '秒杀商品库存',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间'
);

CREATE TABLE seckill_order(
  user_id BIGINT(20)  COMMENT '用户id',
  seckill_id BIGINT(20) COMMENT '秒杀商品id',
  order_id BIGINT(20) COMMENT '订单id',
  PRIMARY KEY (`seckill_id`, `user_id`)/*联合主键*/
)
