/*
Navicat MySQL Data Transfer

Source Server         : shopping
Source Server Version : 50718
Source Host           : cdb-58w35e0w.cd.tencentcdb.com:10070
Source Database       : shopping

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-11-22 14:50:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ad
-- ----------------------------
DROP TABLE IF EXISTS `ad`;
CREATE TABLE `ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adName` varchar(255) DEFAULT NULL COMMENT '广告名称',
  `adLocation` varchar(255) DEFAULT NULL COMMENT '广告位置',
  `adImg` varchar(255) DEFAULT NULL COMMENT '图片',
  `adStatus` int(11) DEFAULT NULL COMMENT '广告状态: 1:申请中,0展示,-1:不展示,默认为1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad
-- ----------------------------

-- ----------------------------
-- Table structure for book_properties
-- ----------------------------
DROP TABLE IF EXISTS `book_properties`;
CREATE TABLE `book_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookPropertiesName` varchar(255) DEFAULT NULL COMMENT '书籍类属性名',
  `productTypeName` varchar(255) DEFAULT NULL COMMENT '所属商品类型',
  `optionalValue` varchar(255) DEFAULT NULL COMMENT '可选值列表',
  `typeId` int(11) DEFAULT NULL COMMENT '商品类型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_properties
-- ----------------------------
INSERT INTO `book_properties` VALUES ('1', '颜色', '书籍类', '红色,绿色,白色', '1');
INSERT INTO `book_properties` VALUES ('2', '书籍种类', '书籍类', '小说,历史,军事', '1');
INSERT INTO `book_properties` VALUES ('3', '颜色', '书籍类', '红色,绿色,白色', '1');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `userId` bigint(255) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `createTime` varchar(255) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('11', '17', 'white', '2019-07-03 21:05:48');
INSERT INTO `cart` VALUES ('27', '48', 'admin1234', '2019-11-12 19:35:16');
INSERT INTO `cart` VALUES ('28', '49', 'admin', '2019-11-12 20:03:15');
INSERT INTO `cart` VALUES ('29', '50', 'admin666', '2019-11-20 17:07:32');

-- ----------------------------
-- Table structure for cart_product
-- ----------------------------
DROP TABLE IF EXISTS `cart_product`;
CREATE TABLE `cart_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cartId` int(11) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `productId` int(11) DEFAULT NULL COMMENT '商品id',
  `productImg` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `productPrice` decimal(10,2) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL COMMENT '商品加入购物车时间',
  `productCount` int(11) DEFAULT '1' COMMENT '购物车中的商品数量',
  `shopId` int(244) DEFAULT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart_product
-- ----------------------------

-- ----------------------------
-- Table structure for clothing_properties
-- ----------------------------
DROP TABLE IF EXISTS `clothing_properties`;
CREATE TABLE `clothing_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clothingPropertiesName` varchar(255) DEFAULT NULL COMMENT '服装属性名称',
  `productTypeName` varchar(255) DEFAULT NULL COMMENT '所属商品类型名称',
  `optionalValue` varchar(255) DEFAULT NULL COMMENT '可选值列表',
  `typeId` int(11) DEFAULT NULL COMMENT '商品类型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clothing_properties
-- ----------------------------
INSERT INTO `clothing_properties` VALUES ('1', '颜色', '服装类', '红色,白色,咖啡色', '2');
INSERT INTO `clothing_properties` VALUES ('2', '款式', '服装类', '休闲类,学生装,正装', '2');
INSERT INTO `clothing_properties` VALUES ('4', 'demo', '服装类', '123,456', '2');
INSERT INTO `clothing_properties` VALUES ('6', '测试', '服装类', '123', '2');
INSERT INTO `clothing_properties` VALUES ('7', '测试', '服装类', '123,456', '2');

-- ----------------------------
-- Table structure for comment_reply
-- ----------------------------
DROP TABLE IF EXISTS `comment_reply`;
CREATE TABLE `comment_reply` (
  `replyId` int(11) NOT NULL,
  `replyContent` varchar(255) DEFAULT NULL,
  `replyTime` varchar(255) DEFAULT NULL,
  `commentId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`replyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment_reply
-- ----------------------------

-- ----------------------------
-- Table structure for elec_properties
-- ----------------------------
DROP TABLE IF EXISTS `elec_properties`;
CREATE TABLE `elec_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `elecPropertiesName` varchar(255) DEFAULT NULL COMMENT '电子数码类属性名称',
  `productTypeName` double(10,2) DEFAULT NULL COMMENT '所属商品名称',
  `optionalValue` varchar(255) DEFAULT NULL COMMENT '可选值列表',
  `typeId` int(11) DEFAULT NULL COMMENT '商品类型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of elec_properties
-- ----------------------------

-- ----------------------------
-- Table structure for life_properties
-- ----------------------------
DROP TABLE IF EXISTS `life_properties`;
CREATE TABLE `life_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lifePropertiesName` varchar(255) DEFAULT NULL COMMENT '生活用品类属性名称',
  `productTypeName` varchar(255) DEFAULT NULL COMMENT '所属商品类型名称',
  `optionalValue` varchar(255) DEFAULT NULL COMMENT '可选值列表',
  `typeId` int(11) DEFAULT NULL COMMENT '商品类型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of life_properties
-- ----------------------------

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `orderNum` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `orderName` varchar(255) DEFAULT NULL COMMENT '订单名称',
  `addressId` bigint(255) DEFAULT NULL COMMENT '收货地址id',
  `orderPrice` decimal(10,2) DEFAULT NULL COMMENT '订单价格',
  `orderInfoStatus` int(11) DEFAULT '1' COMMENT '订单状态(-1;已删除,0已完成,1:未支付,2已支付)',
  `userId` bigint(255) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) DEFAULT NULL COMMENT '买家',
  `createTime` varchar(255) DEFAULT NULL COMMENT '订单创建日期',
  `orderDesc` varchar(255) DEFAULT NULL COMMENT '订单描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=318 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('315', 'RNo201911140205250390942', 'while的订单', '42', '125.00', '1', '17', 'while', '2019-11-14 14:42:45', 'while的订单描述');
INSERT INTO `order_info` VALUES ('316', 'RNo201911140039578689042', 'while的订单', '42', '125.00', '2', '17', 'while', '2019-11-14 14:43:27', 'while的订单描述');
INSERT INTO `order_info` VALUES ('317', 'RNo201911140147521379458', 'while的订单', '13', '9.90', '2', '17', 'while', '2019-11-14 14:59:59', 'while的订单描述');

-- ----------------------------
-- Table structure for order_product
-- ----------------------------
DROP TABLE IF EXISTS `order_product`;
CREATE TABLE `order_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderInfoId` int(11) DEFAULT NULL COMMENT '所属订单信息id',
  `productCount` varchar(255) DEFAULT NULL COMMENT '商品数量',
  `productPrice` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `productId` int(11) DEFAULT NULL COMMENT '商品id',
  `shopId` int(255) DEFAULT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_product
-- ----------------------------
INSERT INTO `order_product` VALUES ('291', '315', '1', '123.00', '商品名称长度要规定', '40', '11');
INSERT INTO `order_product` VALUES ('292', '315', '1', '1.00', '测试数据', '51', '11');
INSERT INTO `order_product` VALUES ('293', '315', '1', '1.00', '测试111111', '55', '11');
INSERT INTO `order_product` VALUES ('294', '317', '1', '9.90', '床桌', '58', '11');

-- ----------------------------
-- Table structure for order_return
-- ----------------------------
DROP TABLE IF EXISTS `order_return`;
CREATE TABLE `order_return` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `returnNum` varchar(255) DEFAULT NULL COMMENT '退货订单编号',
  `useName` varchar(255) DEFAULT NULL COMMENT '退货人',
  `userId` bigint(255) DEFAULT NULL COMMENT '用户id',
  `returnPrice` decimal(10,2) DEFAULT NULL COMMENT '退货金额',
  `orderReturnStatus` int(11) DEFAULT NULL COMMENT '退货状态:-1:已拒绝,0:退货中,1:待处理,2:已完成',
  `createTime` varchar(255) DEFAULT NULL COMMENT '申请时间',
  `productInfoId` bigint(255) DEFAULT NULL COMMENT '商品信息id',
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `returnReason` varchar(255) DEFAULT NULL COMMENT '退货原因',
  `description` varchar(255) DEFAULT NULL COMMENT '退货描述',
  `returnImg` varchar(255) DEFAULT NULL COMMENT '退货凭证图片',
  `delPerson` varchar(255) DEFAULT NULL COMMENT '处理人',
  `comments` varchar(255) DEFAULT NULL COMMENT '处理退货备注',
  `orderInfoId` int(255) DEFAULT NULL COMMENT '订单信息id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_return
-- ----------------------------
INSERT INTO `order_return` VALUES ('1', null, 'abcdefghij', '1', '999.00', null, null, '3', 'goodExample', '不想要了23333', '23333', 'abc.png', 'white', '无', '17');

-- ----------------------------
-- Table structure for order_send
-- ----------------------------
DROP TABLE IF EXISTS `order_send`;
CREATE TABLE `order_send` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) DEFAULT NULL COMMENT '发货人',
  `comment` varchar(255) DEFAULT NULL COMMENT '发货备注',
  `postalCode` varchar(255) DEFAULT NULL COMMENT '邮编',
  `productId` bigint(20) DEFAULT NULL COMMENT '商品信息id',
  `productName` varchar(255) DEFAULT NULL COMMENT '发货商品名称',
  `addressId` bigint(20) DEFAULT NULL COMMENT '收货地址id',
  `orderInfoId` bigint(20) DEFAULT NULL COMMENT '订单信息id',
  `orderSendStatus` int(11) DEFAULT NULL COMMENT '发货状态:-1:已拒绝,0:发货中,1:待处理,2:已完成',
  `createTime` varchar(255) DEFAULT NULL COMMENT '发货时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_send
-- ----------------------------

-- ----------------------------
-- Table structure for pay
-- ----------------------------
DROP TABLE IF EXISTS `pay`;
CREATE TABLE `pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payWay` varchar(255) DEFAULT NULL COMMENT '支付方式',
  `payStatus` int(11) DEFAULT NULL COMMENT '支付状态(1:待支付 ,2已支付,-1:未支付)',
  `payTime` varchar(255) DEFAULT NULL COMMENT '支付时间',
  `orderInfoId` bigint(11) DEFAULT NULL COMMENT '订单信息',
  `userName` varchar(255) DEFAULT NULL COMMENT '支付人',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `payPrice` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pay
-- ----------------------------

-- ----------------------------
-- Table structure for pet_properties
-- ----------------------------
DROP TABLE IF EXISTS `pet_properties`;
CREATE TABLE `pet_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `petPropertiesName` int(11) DEFAULT NULL COMMENT '宠物类属性名称',
  `productTypeName` varchar(255) DEFAULT NULL COMMENT '所属商品类型名称',
  `optionalValue` varchar(255) DEFAULT NULL COMMENT '可选值列表',
  `typeId` int(11) DEFAULT NULL COMMENT '属性类型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pet_properties
-- ----------------------------

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `productNum` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `productStock` varchar(255) DEFAULT NULL COMMENT '商品库存',
  `productPrice` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `productWeight` double(10,2) DEFAULT NULL COMMENT '商品重量',
  `productTypeId` int(11) DEFAULT NULL COMMENT '商品类型id',
  `productType` varchar(255) DEFAULT NULL COMMENT '商品类型',
  `description` varchar(255) DEFAULT NULL COMMENT '商品描述',
  `productImg` varchar(255) DEFAULT NULL COMMENT '商品图片(封面)',
  `productImgPic1` varchar(255) DEFAULT NULL COMMENT '商品图片1',
  `productImgPic2` varchar(255) DEFAULT NULL COMMENT '商品图片2',
  `productImgPic3` varchar(255) DEFAULT NULL COMMENT '商品图片3',
  `productImgPic4` varchar(255) DEFAULT NULL,
  `productInfoStatus` int(1) DEFAULT '1' COMMENT '-1:已下架 0:缺货,1:正常',
  `shopId` int(255) DEFAULT NULL COMMENT '所属店铺id',
  `shopName` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `QRCode` varchar(255) DEFAULT NULL COMMENT '商品二维码,商品的唯一标识',
  `createDate` varchar(255) DEFAULT NULL COMMENT '添加商品时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('30', 'PNo20190917111225000', '小米', '100', '1.00', '1.00', '2', '服装类', '123132', '/static/image/productImage/PNo20190917111225000@.png', '/static/image/productImage/PNo20190917111225000@.png', '/static/image/productImage/PNo20190917111225000@.png', '/static/image/productImage/PNo20190917111225000@.png', '/static/image/productImage/PNo20190917111225000@.png', '1', '11', 'white的店铺', '/static/image/productCode/30PNo20190917111225000.png', '2019-09-17 11:12:25');
INSERT INTO `product_info` VALUES ('31', 'PNo20190917111907000', '小米2', '100', '1.00', '50.00', '3', '电子数码类', '123123', '/static/image/productImage/PNo201909171119070001.jpg', '/static/image/productImage/PNo201909171119070001.jpg', '/static/image/productImage/PNo201909171119070001.jpg', '/static/image/productImage/PNo201909171119070001.jpg', '/static/image/productImage/PNo201909171119070001.jpg', '1', '11', 'white的店铺', '/static/image/productCode/31PNo20190917111907000.png', '2019-09-17 11:19:07');
INSERT INTO `product_info` VALUES ('32', 'PNo20190917112848000', '测试数据3', '1', '1.11', '100.00', '4', '生活用品类', '赞,赞,赞~~', '/static/image/productImage/PNo201909171128480003.jpg', '/static/image/productImage/PNo201909171128480003.jpg', '/static/image/productImage/PNo201909171128480003.jpg', '/static/image/productImage/PNo201909171128480003.jpg', '/static/image/productImage/PNo201909171128480003.jpg', '1', '11', 'white的店铺', '/static/image/productCode/32PNo20190917112848000.png', '2019-09-17 11:28:48');
INSERT INTO `product_info` VALUES ('33', 'PNo20190917114038000', '测试数据2', '100', '1.00', '50.00', '3', '电子数码类', '必须赞一个~', '/static/image/productImage/PNo201909171140380002.jpg', '/static/image/productImage/PNo201909171140380002.jpg', '/static/image/productImage/PNo201909171140380002.jpg', '/static/image/productImage/PNo201909171140380002.jpg', '/static/image/productImage/PNo201909171140380002.jpg', '1', '11', 'white的店铺', '/static/image/productCode/33PNo20190917114038000.png', '2019-09-17 11:40:38');
INSERT INTO `product_info` VALUES ('34', 'PNo20190917115311000', '测试', '100', '123.00', '50.00', '1', '书籍测试类', '1231233213', '/static/image/productImage/PNo201909171153110004.jpg', '/static/image/productImage/PNo201909171153110004.jpg', '/static/image/productImage/PNo201909171153110004.jpg', '/static/image/productImage/PNo201909171153110004.jpg', '/static/image/productImage/PNo201909171153110004.jpg', '1', '11', 'white的店铺', '/static/image/productCode/34PNo20190917115311000.png', '2019-09-17 11:53:11');
INSERT INTO `product_info` VALUES ('37', 'PNo20190917122824000', '小米Max123', '100', '123.00', '100.00', '1', '书籍测试类', '123123', '/static/image/productImage/PNo20190917122824000@.png', '/static/image/productImage/PNo20190917122824000@.png', '/static/image/productImage/PNo20190917122824000@.png', '/static/image/productImage/PNo20190917122824000@.png', '/static/image/productImage/PNo20190917122824000@.png', '1', '11', 'white的店铺', '/static/image/productCode/37PNo20190917122824000.png', '2019-09-17 12:28:24');
INSERT INTO `product_info` VALUES ('38', 'PNo20190917123753000', '测试123456', '100', '123.00', '50.00', '1', '书籍测试类', '234567', '/static/image/productImage/PNo201909171237530002.jpg', '/static/image/productImage/PNo201909171237530002.jpg', '/static/image/productImage/PNo201909171237530002.jpg', '/static/image/productImage/PNo201909171237530002.jpg', '/static/image/productImage/PNo201909171237530002.jpg', '1', '11', 'white的店铺', '/static/image/productCode/38PNo20190917123753000.png', '2019-09-17 12:37:53');
INSERT INTO `product_info` VALUES ('39', 'PNo20190917125452000', '测试123132213', '100', '999999.99', '1231.00', '1', '书籍测试类', '123123', '/static/image/productImage/PNo201909171254520006.jpg', '/static/image/productImage/PNo201909171254520006.jpg', '/static/image/productImage/PNo201909171254520006.jpg', '/static/image/productImage/PNo201909171254520006.jpg', '/static/image/productImage/PNo201909171254520006.jpg', '1', '11', 'white的店铺', '/static/image/productCode/39PNo20190917125452000.png', '2019-09-17 12:54:52');
INSERT INTO `product_info` VALUES ('40', 'PNo20190917130425000', '商品名称长度要规定', '100', '123.00', '50.00', '3', '电子数码类', '32456', '/static/image/productImage/PNo201909171304250004.png', '/static/image/productImage/PNo201909171304250004.png', '/static/image/productImage/PNo201909171304250004.png', '/static/image/productImage/PNo201909171304250004.png', '/static/image/productImage/PNo201909171304250004.png', '1', '11', 'white的店铺', '/static/image/productCode/40PNo20190917130425000.png', '2019-09-17 13:04:25');
INSERT INTO `product_info` VALUES ('41', 'PNo20190917131433000', '测试123123123123', '100', '123.00', '123.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo201909171314330001.jpg', '/static/image/productImage/PNo201909171314330001.jpg', '/static/image/productImage/PNo201909171314330001.jpg', '/static/image/productImage/PNo201909171314330001.jpg', '/static/image/productImage/PNo201909171314330001.jpg', '1', '11', 'white的店铺', '/static/image/productCode/41PNo20190917131433000.png', '2019-09-17 13:14:33');
INSERT INTO `product_info` VALUES ('42', 'PNo20190917131740000', '测试11111', '100', '999999.99', '50.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo20190917131740000@.png', '/static/image/productImage/PNo20190917131740000@.png', '/static/image/productImage/PNo20190917131740000@.png', '/static/image/productImage/PNo20190917131740000@.png', '/static/image/productImage/PNo20190917131740000@.png', '1', '11', 'white的店铺', '/static/image/productCode/42PNo20190917131740000.png', '2019-09-17 13:17:40');
INSERT INTO `product_info` VALUES ('43', 'PNo20190917132131000', '测试2222', '100', '999999.99', '50.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo20190917132131000@.png', '/static/image/productImage/PNo20190917132131000@.png', '/static/image/productImage/PNo20190917132131000@.png', '/static/image/productImage/PNo20190917132131000@.png', '/static/image/productImage/PNo20190917132131000@.png', '1', '11', 'white的店铺', '/static/image/productCode/43PNo20190917132131000.png', '2019-09-17 13:21:31');
INSERT INTO `product_info` VALUES ('44', 'PNo20190917132304000', '小米3', '100', '999999.99', '3.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo20190917132304000@.png', '/static/image/productImage/PNo20190917132304000@.png', '/static/image/productImage/PNo20190917132304000@.png', '/static/image/productImage/PNo20190917132304000@.png', '/static/image/productImage/PNo20190917132304000@.png', '1', '11', 'white的店铺', '/static/image/productCode/44PNo20190917132304000.png', '2019-09-17 13:23:04');
INSERT INTO `product_info` VALUES ('45', 'PNo20190917141845000', '小米2333', '100', '123.00', '50.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo20190917141845000@.png', '/static/image/productImage/PNo20190917141845000@.png', '/static/image/productImage/PNo20190917141845000@.png', '/static/image/productImage/PNo20190917141845000@.png', '/static/image/productImage/PNo20190917141845000@.png', '1', '11', 'white的店铺', '/static/image/productCode/45PNo20190917141845000.png', '2019-09-17 14:18:45');
INSERT INTO `product_info` VALUES ('46', 'PNo20190917143912000', '小米33333', '100', '123.00', '100.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo20190917143912000@.png', '/static/image/productImage/PNo20190917143912000@.png', '/static/image/productImage/PNo20190917143912000@.png', '/static/image/productImage/PNo20190917143912000@.png', '/static/image/productImage/PNo20190917143912000@.png', '1', '11', 'white的店铺', '/static/image/productCode/46PNo20190917143912000.png', '2019-09-17 14:39:12');
INSERT INTO `product_info` VALUES ('47', 'PNo20190917144046000', '小米4444', '100', '123.00', '3.00', '1', '书籍测试类', '123', '/static/image/productImage/PNo20190917144046000@.png', '/static/image/productImage/PNo20190917144046000@.png', '/static/image/productImage/PNo20190917144046000@.png', '/static/image/productImage/PNo20190917144046000@.png', '/static/image/productImage/PNo20190917144046000@.png', '1', '11', 'white的店铺', '/static/image/productCode/47PNo20190917144046000.png', '2019-09-17 14:40:46');
INSERT INTO `product_info` VALUES ('48', 'PNo20190919152806000', '22', '1', '1.11', '50.00', '1', '书籍测试类', '123123', '/static/image/productImage/PNo20190919152806000@.png', '/static/image/productImage/PNo20190919152806000@.png', '/static/image/productImage/PNo20190919152806000@.png', '/static/image/productImage/PNo20190919152806000@.png', '/static/image/productImage/PNo20190919152806000@.png', '1', '11', 'white的店铺', '/static/image/productCode/48PNo20190919152806000.png', '2019-09-19 15:28:06');
INSERT INTO `product_info` VALUES ('51', 'PNo20190926182030000', '测试数据', '1', '1.00', '1.00', '3', '电子数码类', '超赞~', '/static/image/productImage/PNo20190926182030000@.png', '/static/image/productImage/PNo20190926182030000@.png', '/static/image/productImage/PNo20190926182030000@.png', '/static/image/productImage/PNo20190926182030000@.png', '/static/image/productImage/PNo20190926182030000@.png', '1', '11', 'white的店铺', '/static/image/productCode/51PNo20190926182030000.png', '2019-09-26 18:20:29');
INSERT INTO `product_info` VALUES ('52', 'PNo20190926182745000', '测试数据1', '1', '1.00', '1.00', '3', '电子数码类', '赞一个~', '/static/image/productImage/PNo20190926182745000@.png', '/static/image/productImage/PNo20190926182745000@.png', '/static/image/productImage/PNo20190926182745000@.png', '/static/image/productImage/PNo20190926182745000@.png', '/static/image/productImage/PNo20190926182745000@.png', '1', '11', 'white的店铺', '/static/image/productCode/52PNo20190926182745000.png', '2019-09-26 18:27:45');
INSERT INTO `product_info` VALUES ('53', 'PNo20190927123617000', '测试数据4', '1', '1.00', '1.00', '3', '电子数码类', '赞一个啊~', '/static/image/productImage/PNo20190927123617000@.png', '/static/image/productImage/PNo20190927123617000@.png', '/static/image/productImage/PNo20190927123617000@.png', '/static/image/productImage/PNo20190927123617000@.png', '/static/image/productImage/PNo20190927123617000@.png', '1', '11', 'white的店铺', '/static/image/productCode/53PNo20190927123617000.png', '2019-09-27 12:36:17');
INSERT INTO `product_info` VALUES ('54', 'PNo20190928150151000', '测试数据5', '1', '1.00', '2.00', '3', '电子数码类', '不要钱,免费测试~', '/static/image/productImage/PNo20190928150151000@.png', '/static/image/productImage/PNo20190928150151000@.png', '/static/image/productImage/PNo20190928150151000@.png', '/static/image/productImage/PNo20190928150151000@.png', '/static/image/productImage/PNo20190928150151000@.png', '1', '11', 'white的店铺', '/static/image/productCode/54PNo20190928150151000.png', '2019-09-28 15:01:51');
INSERT INTO `product_info` VALUES ('55', 'PNo20191003191056000', '测试111111', '1', '1.00', '1.00', '3', '电子数码类', '这是描述~', '/static/image/productImage/PNo20191003191056000@.png', '/static/image/productImage/PNo20191003191056000@.png', '/static/image/productImage/PNo20191003191056000@.png', '/static/image/productImage/PNo20191003191056000@.png', '/static/image/productImage/PNo20191003191056000@.png', '1', '11', 'white的店铺', '/static/image/productCode/55PNo20191003191056000.png', '2019-10-03 19:10:56');
INSERT INTO `product_info` VALUES ('56', 'PNo20191012094617000', '健身哑铃', '1', '88.80', '50.00', '4', '生活用品类', '因毕业,所以想出售,需要的话联系', '/static/image/productImage/PNo20191012094617000@.png', '/static/image/productImage/PNo20191012094617000@.png', '/static/image/productImage/PNo20191012094617000@.png', '/static/image/productImage/PNo20191012094617000@.png', '/static/image/productImage/PNo20191012094617000@.png', '1', '11', 'white的店铺', '/static/image/productCode/56PNo20191012094617000.png', '2019-10-12 09:46:17');
INSERT INTO `product_info` VALUES ('57', 'PNo20191012094826000', '白兔', '1', '11.11', '1.00', '5', '宠物类', '想找人照顾一下我的兔兔', '/static/image/productImage/PNo20191012094826000@.png', '/static/image/productImage/PNo20191012094826000@.png', '/static/image/productImage/PNo20191012094826000@.png', '/static/image/productImage/PNo20191012094826000@.png', '/static/image/productImage/PNo20191012094826000@.png', '1', '11', 'white的店铺', '/static/image/productCode/57PNo20191012094826000.png', '2019-10-12 09:48:26');
INSERT INTO `product_info` VALUES ('58', 'PNo20191012095118000', '床桌', '1', '9.90', '1.00', '4', '生活用品类', '因毕业用不到了,99成新~', '/static/image/productImage/PNo20191012095118000@.png', '/static/image/productImage/PNo20191012095118000@.png', '/static/image/productImage/PNo20191012095118000@.png', '/static/image/productImage/PNo20191012095118000@.png', '/static/image/productImage/PNo20191012095118000@.png', '1', '11', 'white的店铺', '/static/image/productCode/58PNo20191012095118000.png', '2019-10-12 09:51:18');
INSERT INTO `product_info` VALUES ('59', 'PNo20191012095437000', '测试数据7', '1', '1.00', '1.00', '4', '生活用品类', '测试数据,不知道写啥!', '/static/image/productImage/PNo20191012095437000@.png', '/static/image/productImage/PNo20191012095437000@.png', '/static/image/productImage/PNo20191012095437000@.png', '/static/image/productImage/PNo20191012095437000@.png', '/static/image/productImage/PNo20191012095437000@.png', '1', '11', 'white的店铺', '/static/image/productCode/59PNo20191012095437000.png', '2019-10-12 09:54:37');
INSERT INTO `product_info` VALUES ('60', 'PNo20191012095542000', '宠物测试数据1', '1', '1.00', '1.00', '5', '宠物类', '找人托管,谢谢', '/static/image/productImage/PNo20191012095542000@.png', '/static/image/productImage/PNo20191012095542000@.png', '/static/image/productImage/PNo20191012095542000@.png', '/static/image/productImage/PNo20191012095542000@.png', '/static/image/productImage/PNo20191012095542000@.png', '1', '11', 'white的店铺', '/static/image/productCode/60PNo20191012095542000.png', '2019-10-12 09:55:42');
INSERT INTO `product_info` VALUES ('61', 'PNo20191012095637000', '宠物测试数据2', '1', '1.00', '1.00', '5', '宠物类', '养不起了,找人领走', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '1', '11', 'white的店铺', '/static/image/productCode/61PNo20191012095637000.png', '2019-10-12 09:56:37');
INSERT INTO `product_info` VALUES ('62', 'PNo20191012095711000', '宠物测试数据3', '1', '1.00', '1.00', '5', '宠物类', '我是描述~', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '/static/image/productImage/PNo20191012095637000@.png', '1', '11', 'white的店铺', '/static/image/productCode/62PNo20191012095711000.png', '2019-10-12 09:57:11');
INSERT INTO `product_info` VALUES ('63', 'PNo20191012100416000', '宠物测试数据4', '1', '1.00', '1.00', '5', '宠物类', '这是宠物测试数据~', '/static/image/productImage/PNo20191012100416000@.png', '/static/image/productImage/PNo20191012100416000@.png', '/static/image/productImage/PNo20191012100416000@.png', '/static/image/productImage/PNo20191012100416000@.png', '/static/image/productImage/PNo20191012100416000@.png', '1', '11', 'white的店铺', '/static/image/productCode/63PNo20191012100416000.png', '2019-10-12 10:04:16');
INSERT INTO `product_info` VALUES ('64', 'PNo20191012143915000', '服装测试数据1', '1', '1.00', '2.00', '2', '服装类', '商品描述长度不能超过13', '/static/image/productImage/PNo20191012143915000@.png', '/static/image/productImage/PNo20191012143915000@.png', '/static/image/productImage/PNo20191012143915000@.png', '/static/image/productImage/PNo20191012143915000@.png', '/static/image/productImage/PNo20191012143915000@.png', '1', '11', 'white的店铺', '/static/image/productCode/64PNo20191012143915000.png', '2019-10-12 14:39:15');
INSERT INTO `product_info` VALUES ('65', 'PNo20191012144006000', '服装测试数据2', '1', '1.00', '1.00', '2', '服装类', '我是服装测试数据的描述!', '/static/image/productImage/PNo20191012144006000@.png', '/static/image/productImage/PNo20191012144006000@.png', '/static/image/productImage/PNo20191012144006000@.png', '/static/image/productImage/PNo20191012144006000@.png', '/static/image/productImage/PNo20191012144006000@.png', '1', '11', 'white的店铺', '/static/image/productCode/65PNo20191012144006000.png', '2019-10-12 14:40:06');
INSERT INTO `product_info` VALUES ('66', 'PNo20191012144050000', '服装测试数据3', '1', '1.00', '2.00', '2', '服装类', '我是服装测试数据描述!', '/static/image/productImage/PNo20191012144050000@.png', '/static/image/productImage/PNo20191012144050000@.png', '/static/image/productImage/PNo20191012144050000@.png', '/static/image/productImage/PNo20191012144050000@.png', '/static/image/productImage/PNo20191012144050000@.png', '1', '11', 'white的店铺', '/static/image/productCode/66PNo20191012144050000.png', '2019-10-12 14:40:50');
INSERT INTO `product_info` VALUES ('67', 'PNo20191012144117000', '服装测试数据4', '1', '1.00', '1.00', '2', '服装类', '我是服装测试数据描述!', '/static/image/productImage/PNo20191012144117000@.png', '/static/image/productImage/PNo20191012144117000@.png', '/static/image/productImage/PNo20191012144117000@.png', '/static/image/productImage/PNo20191012144117000@.png', '/static/image/productImage/PNo20191012144117000@.png', '1', '11', 'white的店铺', '/static/image/productCode/67PNo20191012144117000.png', '2019-10-12 14:41:17');
INSERT INTO `product_info` VALUES ('68', 'PNo20191019114253000', 'Readmi', '1', '1.00', '1.00', '3', '电子数码类', 'Redmi 8A 5000mAh大电量 大字体大音量大内存 骁龙八核处理器 AI人脸解锁 莱茵护眼全面屏', '/static/image/productImage/PNo201910191142530004.jpg', '/static/image/productImage/PNo201910191142530004.jpg', '/static/image/productImage/PNo201910191142530004.jpg', '/static/image/productImage/PNo201910191142530004.jpg', '/static/image/productImage/PNo201910191142530004.jpg', '1', '11', 'white的店铺', '/static/image/productCode/68PNo20191019114253000.png', '2019-10-19 11:42:53');
INSERT INTO `product_info` VALUES ('69', 'PNo20191019122121000', '小米9SE', '1', '1.00', '1.00', '3', '电子数码类', '小米9SE,免费送6G+128G AI人脸解锁,40W无线快充', '/static/image/productImage/PNo201910191221210004.jpg', '/static/image/productImage/PNo201910191221210004.jpg', '/static/image/productImage/PNo201910191221210004.jpg', '/static/image/productImage/PNo201910191221210004.jpg', '/static/image/productImage/PNo201910191221210004.jpg', '1', '11', 'white的店铺', '/static/image/productCode/69PNo20191019122121000.png', '2019-10-19 12:21:21');
INSERT INTO `product_info` VALUES ('77', 'PNo20191103164108000', '小米Max', '1', '1.00', '1.00', '3', '电子数码类', '特价处理小米MAX3,99成新', '/static/image/productImage/20191108173440000/20191108173440000_0_2.jpg', '/static/image/productImage/20191103164108000/20191103164108000_1_3.jpg', '/static/image/productImage/20191103164108000/20191103164108000_3_6.jpg', '/static/image/productImage/20191103164108000/20191103164108000_4_7.jpg', '/static/image/productImage/20191108173211000/20191108173211000_0_4.jpg', '1', '11', 'white的店铺', '/static/image/productCode/77PNo20191103164108000.png', '2019-11-03 16:41:08');
INSERT INTO `product_info` VALUES ('78', 'PNo20191112120458000', '二手吉他', '1', '1.11', '50.00', '10', '乐器类', '二手吉他,因毕业要出手...', '/static/image/productImage/20191112120458000/20191112120458000_0_2.jpg', '/static/image/productImage/20191112120458000/20191112120458000_1_3.jpg', '/static/image/productImage/20191112120458000/20191112120458000_2_4.jpg', '/static/image/productImage/20191112120458000/20191112120458000_3_6.jpg', '/static/image/productImage/20191112120458000/20191112120458000_3_6.jpg', '1', '11', 'white的店铺', '/static/image/productCode/78PNo20191112120458000.png', '2019-11-12 12:04:58');
INSERT INTO `product_info` VALUES ('79', 'PNo20191112121346000', '二手架子鼓', '1', '1.00', '1.00', '10', '乐器类', '因毕业用不到,想送人', '/static/image/productImage/20191112121346000/20191112121346000_0_2.jpg', '/static/image/productImage/20191112121346000/20191112121346000_1_4.jpg', '/static/image/productImage/20191112121346000/20191112121346000_2_7.jpg', '/static/image/productImage/20191112121346000/20191112121346000_3_8.jpg', '/static/image/productImage/20191112121346000/20191112121346000_3_8.jpg', '1', '11', 'white的店铺', '/static/image/productCode/79PNo20191112121346000.png', '2019-11-12 12:13:46');

-- ----------------------------
-- Table structure for product_sell
-- ----------------------------
DROP TABLE IF EXISTS `product_sell`;
CREATE TABLE `product_sell` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) DEFAULT NULL COMMENT '已售商品名称',
  `productImg` varchar(255) DEFAULT NULL COMMENT '已售商品图片',
  `producntId` bigint(255) DEFAULT NULL COMMENT '已售商品id',
  `userId` bigint(255) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',
  `shopId` bigint(255) DEFAULT NULL COMMENT '已售商品所属店铺id',
  `createTime` varchar(255) DEFAULT NULL COMMENT '交易时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_sell
-- ----------------------------
INSERT INTO `product_sell` VALUES ('1', 'iPhone XI', null, '3', '17', 'white', '1', '2019-07-07 20:59:03');

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(255) DEFAULT NULL COMMENT '商品类型名称',
  `typePropertiesCount` int(255) DEFAULT NULL COMMENT '商品类型属性数量',
  `createTime` varchar(255) DEFAULT '0' COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_type
-- ----------------------------
INSERT INTO `product_type` VALUES ('1', '书籍类', '3', '2019-07-06 17:51:08');
INSERT INTO `product_type` VALUES ('2', '服装类', '5', '2019-07-06 17:51:08');
INSERT INTO `product_type` VALUES ('3', '电子数码类', '0', '2019-07-06 17:51:08');
INSERT INTO `product_type` VALUES ('4', '生活用品类', '0', '2019-07-06 17:51:08');
INSERT INTO `product_type` VALUES ('5', '宠物类', '0', '2019-07-06 17:51:08');
INSERT INTO `product_type` VALUES ('10', '乐器类', '0', '0');

-- ----------------------------
-- Table structure for role_refer
-- ----------------------------
DROP TABLE IF EXISTS `role_refer`;
CREATE TABLE `role_refer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `parentId` int(11) DEFAULT NULL COMMENT '父级ID',
  `parentName` varchar(255) DEFAULT NULL COMMENT '父级角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_refer
-- ----------------------------
INSERT INTO `role_refer` VALUES ('1', '普通用户', '3', '管理员');
INSERT INTO `role_refer` VALUES ('2', '会员用户', '3', '管理员');
INSERT INTO `role_refer` VALUES ('3', '管理员', '4', '超级管理员');
INSERT INTO `role_refer` VALUES ('4', '超级管理员', '5', '系统管理员');
INSERT INTO `role_refer` VALUES ('5', '系统管理员', null, null);

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `shopName` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `userId` bigint(255) DEFAULT NULL COMMENT '经营人id',
  `userName` varchar(255) DEFAULT NULL COMMENT '店铺经营人',
  `shopNum` varchar(255) DEFAULT NULL COMMENT '店铺编号',
  `shopLevel` varchar(255) DEFAULT NULL COMMENT '店铺等级',
  `shopStatus` int(11) DEFAULT '1' COMMENT '店铺状态: 1:开通店铺,0:禁用,-1:未通过申请,2未开通店铺 ,默认为1',
  `createTime` varchar(255) DEFAULT NULL COMMENT '店铺创建时间',
  `shopImg` varchar(255) DEFAULT NULL COMMENT '店铺封面',
  `shopDesc` varchar(255) DEFAULT NULL COMMENT '店铺描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('11', 'white的店铺', '17', 'white', 'SNo562159148893618', '一般', '1', '2019-07-06 18:05:22', null, '这是一条成熟的描述~');
INSERT INTO `shop` VALUES ('13', null, '21', 'admin', 'SNo568962939508676', '一般', '1', '2019-09-20 15:02:15', null, null);
INSERT INTO `shop` VALUES ('18', null, '39', 'admin123', 'SNo569575576627171', '一般', '1', '2019-09-27 17:12:56', null, null);
INSERT INTO `shop` VALUES ('21', 'zhangsan的店铺', '42', 'zhangsan', 'SNo571456169967240', '一般', '1', '2019-10-19 11:36:09', null, null);
INSERT INTO `shop` VALUES ('22', 'admin1的店铺', '43', 'admin1', 'SNo572145557966061', '一般', '1', '2019-10-27 11:05:57', null, null);
INSERT INTO `shop` VALUES ('23', 'admin12的店铺', '44', 'admin12', 'SNo572321084662255', '一般', '1', '2019-10-29 11:51:24', null, null);
INSERT INTO `shop` VALUES ('24', 'admin1111的店铺', '45', 'admin1111', 'SNo572525734887047', '一般', '1', '2019-10-31 20:42:14', null, null);
INSERT INTO `shop` VALUES ('25', 'admin888的店铺', '46', 'admin888', 'SNo572596192249375', '一般', '1', '2019-11-01 16:16:32', null, null);
INSERT INTO `shop` VALUES ('26', 'HuaiKB的店铺', '47', 'HuaiKB', 'SNo572860461484360', '一般', '1', '2019-11-04 17:41:01', null, null);
INSERT INTO `shop` VALUES ('27', 'admin1234的店铺', '48', 'admin1234', 'SNo573558516452188', '一般', '1', '2019-11-12 19:35:16', null, null);
INSERT INTO `shop` VALUES ('28', 'admin的店铺', '49', 'admin', 'SNo573560195924904', '一般', '1', '2019-11-12 20:03:15', null, null);
INSERT INTO `shop` VALUES ('29', 'admin666的店铺', '50', 'admin666', 'SNo574240852634141', '一般', '1', '2019-11-20 17:07:32', null, null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userName` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `phone` varchar(255) NOT NULL COMMENT '联系电话',
  `email` varchar(255) NOT NULL COMMENT '邮箱/账号',
  `pictureHead` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `roleName` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `roleId` int(11) DEFAULT NULL COMMENT '角色id',
  `shopName` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `shopId` int(255) DEFAULT NULL COMMENT '店铺id',
  `userStatus` int(255) DEFAULT '1' COMMENT '0为禁用状态,1为正常状态',
  `createTime` varchar(255) DEFAULT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('17', 'while', '111', '11111111111', '13122256789@163.com', null, '普通用户', '13', '张三的店铺', '11', '1', '2019-07-03 21:05:48');
INSERT INTO `user` VALUES ('48', 'admin1234', '58CF703F664397EC4F0AC359B84B565C', '22222222222', '@163.com', null, '普通用户', '29', 'admin1234的店铺', '27', '1', '2019-11-12 19:35:15');
INSERT INTO `user` VALUES ('49', 'admin', '58CF703F664397EC4F0AC359B84B565C', '33333333333', '123456@qq.com', null, '普通用户', '30', 'admin的店铺', '28', '1', '2019-11-12 20:03:15');
INSERT INTO `user` VALUES ('50', 'admin666', 'AF8F9DFFA5D420FBC249141645B962EE', '11111111', '12345446@qq.com', null, '普通用户', '31', 'admin666的店铺', '29', '1', '2019-11-20 17:07:31');

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL COMMENT '收货地址人',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT '' COMMENT '收货地址',
  `comment` varchar(255) DEFAULT '' COMMENT '收货备注',
  `userId` int(255) DEFAULT NULL COMMENT '用户id',
  `createTime` varchar(255) DEFAULT NULL,
  `addressTag` varchar(255) DEFAULT '家' COMMENT '地址标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES ('13', '地址测试名称3', '1234567890', '河北省保定市莲池区长城北大街27号', '西苑123号', '17', '2019-07-06 18:15:37', '公司');
INSERT INTO `user_address` VALUES ('42', 'while', '15299988888', '河北省张家口市太平南路福鼎小区', '赞~', '17', '2019-11-05 09:12:24', '家');
INSERT INTO `user_address` VALUES ('43', 'admin1234', '', '', '', '48', '2019-11-12 19:35:16', '家');
INSERT INTO `user_address` VALUES ('44', 'admin', '', '', '', '49', '2019-11-12 20:03:15', '家');
INSERT INTO `user_address` VALUES ('45', 'admin666', '', '', '', '50', '2019-11-20 17:07:32', '家');

-- ----------------------------
-- Table structure for user_admin
-- ----------------------------
DROP TABLE IF EXISTS `user_admin`;
CREATE TABLE `user_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_admin
-- ----------------------------
INSERT INTO `user_admin` VALUES ('1', 'admin', '123');
INSERT INTO `user_admin` VALUES ('2', '123', '123');

-- ----------------------------
-- Table structure for user_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `productId` bigint(11) DEFAULT NULL COMMENT '商品id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `userId` int(30) DEFAULT NULL COMMENT '用户id',
  `commentContent` varchar(255) DEFAULT '' COMMENT '评论内容',
  `commentImg` varchar(255) DEFAULT NULL COMMENT '评论图片',
  `commentStatus` int(11) DEFAULT '1' COMMENT '评论状态(1:正常 , -1删除,默认为1)',
  `createTime` varchar(255) DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_comment
-- ----------------------------
INSERT INTO `user_comment` VALUES ('1', '手机', '40', 'while', '2', 'while', 'afafalgagma.png', '1', '2019-06-26 21:27:20');
INSERT INTO `user_comment` VALUES ('2', '衣服', '40', 'while', '1', '哈哈', 'afafalgagma.png', '1', '2019-06-26 21:28:07');
INSERT INTO `user_comment` VALUES ('3', '测试', '34', 'while', '34', '123456', null, '1', '2019-09-23 14:34:12');
INSERT INTO `user_comment` VALUES ('4', '测试', '34', 'while', '34', '这是一条成熟的评论', null, '1', '2019-09-23 14:35:39');
INSERT INTO `user_comment` VALUES ('5', '测试', '34', 'while', '34', '这个商品不错~~', null, '1', '2019-09-23 14:38:17');
INSERT INTO `user_comment` VALUES ('6', '越南新娘', '32', 'while', '32', '超赞', null, '1', '2019-09-23 14:53:46');
INSERT INTO `user_comment` VALUES ('7', '小米2', '31', 'while', '31', '小米2\n', null, '1', '2019-09-23 15:10:30');
INSERT INTO `user_comment` VALUES ('8', '测试', '34', 'while', '34', '超赞哦~~', null, '1', '2019-09-23 15:58:26');
INSERT INTO `user_comment` VALUES ('9', '测试123456', '38', 'while', '38', '这张图片是路飞~', null, '1', '2019-09-23 16:32:43');
INSERT INTO `user_comment` VALUES ('10', '小米', '30', 'while', '30', '评论测试~~', null, '1', '2019-09-23 17:15:00');
INSERT INTO `user_comment` VALUES ('11', '小米', '30', 'while', '30', '评论测试2~~~~', null, '1', '2019-09-23 17:20:24');
INSERT INTO `user_comment` VALUES ('14', '小米', '30', 'while', '30', '赞一个~', null, '1', '2019-09-24 11:27:56');
INSERT INTO `user_comment` VALUES ('16', '小米', '30', 'while', '17', '12345', null, '1', '2019-09-24 14:05:47');
INSERT INTO `user_comment` VALUES ('18', '测试123123123123', '41', 'while', '17', '赞一个~~', null, '1', '2019-09-24 14:46:43');
INSERT INTO `user_comment` VALUES ('21', '测试123123123123', '41', 'while', '17', '1234', null, '1', '2019-09-24 14:52:15');
INSERT INTO `user_comment` VALUES ('22', null, null, null, null, '赞一个~', null, '1', '2019-09-24 20:44:47');
INSERT INTO `user_comment` VALUES ('23', '小米Max4567890-', '40', 'while', '17', '赞一个', null, '1', '2019-09-24 20:45:21');
INSERT INTO `user_comment` VALUES ('24', '小米Max4567890-', '40', 'while', '17', '啊哈~\n1234', null, '1', '2019-09-24 20:45:36');
INSERT INTO `user_comment` VALUES ('25', '小米Max123', '37', 'while', '17', '用户评论测试~~', null, '1', '2019-09-25 09:17:10');
INSERT INTO `user_comment` VALUES ('26', '小米Max123', '37', 'while', '17', '我要在前面~', null, '1', '2019-09-25 09:17:26');
INSERT INTO `user_comment` VALUES ('27', null, null, null, null, '123', null, '1', '2019-09-27 16:15:36');
INSERT INTO `user_comment` VALUES ('28', null, null, null, null, '一条评论~', null, '1', '2019-09-28 14:56:43');
INSERT INTO `user_comment` VALUES ('29', '测试数据4', '53', 'while', '17', '评论在此,谁敢放肆~', null, '1', '2019-09-28 14:58:16');
INSERT INTO `user_comment` VALUES ('30', '测试123132213', '39', 'while', '17', '评论一下~', null, '1', '2019-09-28 17:29:20');
INSERT INTO `user_comment` VALUES ('31', null, null, null, null, '123456', null, '1', '2019-09-29 10:03:38');
INSERT INTO `user_comment` VALUES ('32', '小米Max4567890-', '40', 'while', '17', '123', null, '1', '2019-09-30 19:35:30');
INSERT INTO `user_comment` VALUES ('33', '小米3', '44', 'while', '17', '评论一下哈~', null, '1', '2019-10-02 21:19:37');
INSERT INTO `user_comment` VALUES ('34', '测试11111', '42', 'while', '17', '评论一个~', null, '1', '2019-10-03 19:07:39');
INSERT INTO `user_comment` VALUES ('35', '测试数据2', '33', 'while', '17', '评论一下\n', null, '1', '2019-10-05 16:21:22');
INSERT INTO `user_comment` VALUES ('36', '测试123456', '38', 'while', '17', '123', null, '1', '2019-10-09 10:41:16');
INSERT INTO `user_comment` VALUES ('37', '健身哑铃', '56', 'while', '17', '一瓶可乐拿走', null, '1', '2019-10-12 09:53:16');
INSERT INTO `user_comment` VALUES ('38', '床桌', '58', 'while', '17', '123', null, '1', '2019-10-15 11:53:13');
INSERT INTO `user_comment` VALUES ('39', null, null, null, null, '123', null, '1', '2019-10-15 12:04:31');
INSERT INTO `user_comment` VALUES ('40', '床桌', '58', 'while', '17', '123', null, '1', '2019-10-15 12:05:19');
INSERT INTO `user_comment` VALUES ('41', null, null, null, null, '123', null, '1', '2019-10-15 12:05:29');
INSERT INTO `user_comment` VALUES ('42', '小米2', '31', 'while', '17', '123', null, '1', '2019-10-15 12:08:37');
INSERT INTO `user_comment` VALUES ('43', '测试数据3', '32', 'while', '17', '这是一条评论~\n', null, '1', '2019-10-15 12:11:15');
INSERT INTO `user_comment` VALUES ('44', '测试数据3', '32', 'while', '17', '123', null, '1', '2019-10-15 12:11:31');
INSERT INTO `user_comment` VALUES ('45', '小米9SE', '69', 'while', '17', '123', null, '1', '2019-10-26 10:43:58');
INSERT INTO `user_comment` VALUES ('46', '小米Max123', '37', 'while', '17', '评价一下哈!', null, '1', '2019-10-26 10:49:06');
INSERT INTO `user_comment` VALUES ('47', '床桌', '58', 'while', '17', '123', null, '1', '2019-11-02 17:16:42');
INSERT INTO `user_comment` VALUES ('48', '床桌', '58', 'while', '17', '哈哈~~', null, '1', '2019-11-02 17:16:54');
INSERT INTO `user_comment` VALUES ('49', '床桌', '58', 'while', '17', '发布评论~~', null, '1', '2019-11-02 17:28:38');
INSERT INTO `user_comment` VALUES ('50', '床桌', '58', 'while', '17', '评论~', null, '1', '2019-11-02 17:52:20');
INSERT INTO `user_comment` VALUES ('51', '小米Max', '77', 'while', '17', '评论~~~', null, '1', '2019-11-11 14:59:36');

-- ----------------------------
-- Table structure for user_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `user_evaluate`;
CREATE TABLE `user_evaluate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `evaluateContent` varchar(255) DEFAULT NULL COMMENT '评价内容',
  `evaluateImg` varchar(255) DEFAULT NULL COMMENT '评价图片',
  `shopId` int(11) DEFAULT NULL COMMENT '店铺id',
  `shopName` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `shopEvaluate` int(11) DEFAULT NULL COMMENT '对店铺的评价(-1:极差,0:较差,1:一般,2:还不错3:极好)',
  `productId` int(11) DEFAULT NULL COMMENT '商品id',
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `evaluateStatus` int(11) DEFAULT '1' COMMENT '评价状态(1:正常,-1:删除,默认为1)',
  `createTime` varchar(255) DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_evaluate
-- ----------------------------
INSERT INTO `user_evaluate` VALUES ('1', 'white', '1', '哈哈哈', 'waesdfghhgagsdfg.png', '1', '张三的店铺', '1', '1', 'iPhone X', '1', '2019-06-27 09:54:40');
INSERT INTO `user_evaluate` VALUES ('2', 'white', '1', '哈哈哈2222233333', 'waesdfghhgagsdfg.png', '1', '张三的店铺', '1', '1', 'iPhone XR', '1', '2019-06-27 10:07:10');
INSERT INTO `user_evaluate` VALUES ('3', 'white', '1', '2222233333', 'waesdfghhgagsdfg.png', '1', '张三的店铺', '1', '1', 'iPhone MAX', '-1', '2019-06-27 10:10:42');

-- ----------------------------
-- Table structure for user_power
-- ----------------------------
DROP TABLE IF EXISTS `user_power`;
CREATE TABLE `user_power` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `powerName` varchar(255) NOT NULL COMMENT '权限名称',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `powerDescription` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_power
-- ----------------------------
INSERT INTO `user_power` VALUES ('16', '管理普通用户', '17', 'white', '管理员的权限');
INSERT INTO `user_power` VALUES ('17', '普通用户', '32', '', null);
INSERT INTO `user_power` VALUES ('18', '普通用户', '34', 'admin', null);
INSERT INTO `user_power` VALUES ('19', '普通用户', '35', 'admin123', null);
INSERT INTO `user_power` VALUES ('20', '普通用户', '36', 'admin123', null);
INSERT INTO `user_power` VALUES ('21', '普通用户', '37', 'admin456', null);
INSERT INTO `user_power` VALUES ('22', '普通用户', '38', 'admin1', null);
INSERT INTO `user_power` VALUES ('23', '普通用户', '39', 'admin123', null);
INSERT INTO `user_power` VALUES ('24', '普通用户', '40', 'admin1', null);
INSERT INTO `user_power` VALUES ('25', '普通用户', '41', 'admin12', null);
INSERT INTO `user_power` VALUES ('26', '普通用户', '42', 'zhangsan', null);
INSERT INTO `user_power` VALUES ('27', '普通用户', '43', 'admin1', null);
INSERT INTO `user_power` VALUES ('28', '普通用户', '44', 'admin12', null);
INSERT INTO `user_power` VALUES ('29', '普通用户', '45', 'admin1111', null);
INSERT INTO `user_power` VALUES ('30', '普通用户', '46', 'admin888', null);
INSERT INTO `user_power` VALUES ('31', '普通用户', '47', 'HuaiKB', null);
INSERT INTO `user_power` VALUES ('32', '普通用户', '48', 'admin1234', null);
INSERT INTO `user_power` VALUES ('33', '普通用户', '49', 'admin', null);
INSERT INTO `user_power` VALUES ('34', '普通用户', '50', 'admin666', null);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `roleName` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `roleDescription` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `referRoleId` int(11) DEFAULT NULL COMMENT '参考角色id',
  `createTime` varchar(255) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('13', null, null, '17', '5', '2019-07-03 21:05:48');
INSERT INTO `user_role` VALUES ('14', '普通用户', null, '32', '1', '2019-09-20 12:50:59');
INSERT INTO `user_role` VALUES ('15', '普通用户', null, '34', '1', '2019-09-20 15:01:59');
INSERT INTO `user_role` VALUES ('16', '普通用户', null, '35', '1', '2019-09-20 15:17:09');
INSERT INTO `user_role` VALUES ('17', '普通用户', null, '36', '1', '2019-09-20 15:20:12');
INSERT INTO `user_role` VALUES ('18', '普通用户', null, '37', '1', '2019-09-20 15:54:13');
INSERT INTO `user_role` VALUES ('19', '普通用户', null, '38', '1', '2019-09-27 16:38:11');
INSERT INTO `user_role` VALUES ('20', '普通用户', null, '39', '1', '2019-09-27 17:12:56');
INSERT INTO `user_role` VALUES ('21', '普通用户', null, '40', '1', '2019-09-28 10:56:59');
INSERT INTO `user_role` VALUES ('22', '普通用户', null, '41', '1', '2019-09-28 11:02:01');
INSERT INTO `user_role` VALUES ('23', '普通用户', null, '42', '1', '2019-10-19 11:36:09');
INSERT INTO `user_role` VALUES ('24', '普通用户', null, '43', '1', '2019-10-27 11:05:57');
INSERT INTO `user_role` VALUES ('25', '普通用户', null, '44', '1', '2019-10-29 11:51:23');
INSERT INTO `user_role` VALUES ('26', '普通用户', null, '45', '1', '2019-10-31 20:42:14');
INSERT INTO `user_role` VALUES ('27', '普通用户', null, '46', '1', '2019-11-01 16:16:31');
INSERT INTO `user_role` VALUES ('28', '普通用户', null, '47', '1', '2019-11-04 17:41:00');
INSERT INTO `user_role` VALUES ('29', '普通用户', null, '48', '1', '2019-11-12 19:35:15');
INSERT INTO `user_role` VALUES ('30', '普通用户', null, '49', '1', '2019-11-12 20:03:15');
INSERT INTO `user_role` VALUES ('31', '普通用户', null, '50', '1', '2019-11-20 17:07:32');
