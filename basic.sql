SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for T_APP_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `T_APP_AUTH`;
CREATE TABLE `T_APP_AUTH`  (
  `UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `AUTH_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AUTH_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AUTH_DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`UUID`) USING BTREE,
  UNIQUE INDEX `index_auth_id`(`AUTH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'APP接口权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_APP_MESSAGE
-- ----------------------------
DROP TABLE IF EXISTS `T_APP_MESSAGE`;
CREATE TABLE `T_APP_MESSAGE`  (
  `UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `SENDER` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `SENDER_NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `RECEIVER` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `RECEIVER_NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `TITLE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `CONTENT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `UNREAD` tinyint(1) NULL DEFAULT 1,
  `SEND_TIME` datetime(0) NULL DEFAULT NULL,
  `READ_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'APP消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_APP_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_APP_ROLE`;
CREATE TABLE `T_APP_ROLE`  (
  `UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ROLE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `ROLE_NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `ROLE_DESCRIBE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'APP用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_APP_ROLE_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `T_APP_ROLE_AUTH`;
CREATE TABLE `T_APP_ROLE_AUTH`  (
  `UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ROLE_UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AUTH_UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'APP角色与权限关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_APP_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_APP_USER_ROLE`;
CREATE TABLE `T_APP_USER_ROLE`  (
  `UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `USER_UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `ROLE_UUID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'APP角色用户关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for TBL_USER
-- ----------------------------
DROP TABLE IF EXISTS `T_APP_USER`;
CREATE TABLE `TBL_USER`  (
  `UUID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `AVATAR` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PASSWORD` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DEPARTMENT_UUID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`UUID`) USING BTREE,
  UNIQUE INDEX `UN_AVATAR`(`AVATAR`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
