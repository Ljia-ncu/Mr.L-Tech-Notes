-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT 'len(50)',
  `age` int(11) NOT NULL DEFAULT '0',
  `account` decimal(10,0) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，1删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
