-- ----------------------------
-- Table structure for tx_test
-- ----------------------------
DROP TABLE IF EXISTS `tx_test`;
CREATE TABLE `tx_test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `count` int(11) NOT NULL COMMENT '模拟的金币数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;