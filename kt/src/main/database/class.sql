/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : class

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 14/05/2021 18:46:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class_relation
-- ----------------------------
DROP TABLE IF EXISTS `class_relation`;
CREATE TABLE `class_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `department` int(11) NULL DEFAULT NULL COMMENT '所属学院',
  `major` int(11) NULL DEFAULT NULL COMMENT '所属专业',
  `grades` int(11) NULL DEFAULT NULL COMMENT '所属班级',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '教师id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of class_relation
-- ----------------------------
INSERT INTO `class_relation` VALUES (8, 6, '计算机操作系统', NULL, NULL, 4, 4, '2021-03-03 16:00:14', NULL, 1);
INSERT INTO `class_relation` VALUES (9, 7, '数据结构', NULL, NULL, 4, 4, '2021-03-03 16:03:13', NULL, 1);
INSERT INTO `class_relation` VALUES (10, 8, '软件工程概论', NULL, NULL, 5, 5, '2021-03-03 16:04:00', NULL, 1);
INSERT INTO `class_relation` VALUES (14, 9, '数据库原理', NULL, NULL, 8, 12, '2021-04-29 13:37:54', NULL, 1);

-- ----------------------------
-- Table structure for class_schedule
-- ----------------------------
DROP TABLE IF EXISTS `class_schedule`;
CREATE TABLE `class_schedule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程安排id',
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  `class_relation_id` int(11) NULL DEFAULT NULL COMMENT '课程关联id',
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `weekth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第几周有课（当年第几周）',
  `school_weekth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校时间的第几周',
  `day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '周几有课',
  `section` int(2) NULL DEFAULT NULL COMMENT '节次',
  `department` int(11) NULL DEFAULT NULL COMMENT '所属学院',
  `major` int(11) NULL DEFAULT NULL COMMENT '所属专业',
  `grades` int(11) NULL DEFAULT NULL COMMENT '所属班级',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '教师用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程安排表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '课下交流问题id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `parent` int(11) NULL DEFAULT NULL COMMENT '评论父id（一级评论父id为0）',
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 216 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课下交流问题评论表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (7, 3, 1, NULL, '学好计算机原理应该熟悉知识结构', '2021-03-05 20:57:31', NULL, 1);
INSERT INTO `comment` VALUES (8, 3, 1, NULL, '学好计算机原理需要理解原理', '2021-04-09 21:32:03', NULL, 1);
INSERT INTO `comment` VALUES (9, 3, 2, NULL, '需要理解课本', '2021-04-09 21:33:35', NULL, 1);
INSERT INTO `comment` VALUES (203, 4, 1, NULL, '掌握课本', '2021-04-25 13:38:34', NULL, 1);
INSERT INTO `comment` VALUES (208, 8, 2, NULL, '理解原理', '2021-05-06 12:36:37', NULL, 1);
INSERT INTO `comment` VALUES (209, 6, 2, NULL, '熟悉知识结构', '2021-05-06 12:36:55', NULL, 1);
INSERT INTO `comment` VALUES (210, 5, 2, NULL, '掌握课本', '2021-05-06 12:37:08', NULL, 1);
INSERT INTO `comment` VALUES (211, 8, 1, NULL, '理解指针', '2021-05-06 12:37:47', NULL, 1);
INSERT INTO `comment` VALUES (212, 5, 1, NULL, '理解知识之间的联系', '2021-05-06 12:38:12', NULL, 1);
INSERT INTO `comment` VALUES (213, 8, 1, NULL, '多动手', '2021-05-06 12:38:39', NULL, 1);
INSERT INTO `comment` VALUES (214, 6, 4, NULL, '理解好课本', '2021-05-06 12:39:33', NULL, 1);
INSERT INTO `comment` VALUES (215, 8, 4, NULL, '理解最重要', '2021-05-06 12:40:05', NULL, 1);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程描述',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (6, '计算机操作系统', '计算机', 1, '2021-03-03 15:57:58', NULL);
INSERT INTO `course` VALUES (7, '数据结构', '计算机', 1, '2021-03-03 15:58:17', NULL);
INSERT INTO `course` VALUES (8, '软件工程概论', '计算机', 1, '2021-03-03 15:58:38', NULL);
INSERT INTO `course` VALUES (9, '数据库原理', '数据库', 1, '2021-03-03 15:58:54', NULL);

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `member_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `wechat_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信图片',
  `union_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(2) NULL DEFAULT NULL COMMENT '用户类别 1:学生2:教师',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别 1男,2女',
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `department` int(11) NULL DEFAULT NULL COMMENT '学院',
  `major` int(11) NULL DEFAULT NULL COMMENT '专业',
  `grades` int(11) NULL DEFAULT NULL COMMENT '班级',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, '12345', 'Mo', '20210429114058882.jpeg', '123', 1, '许岳林', 1, '20171308140', 1, 2, 4, '13784999213', 'e10adc3949ba59abbe56e057f20f883e', 1, '2021-03-12 17:47:43', NULL);
INSERT INTO `member` VALUES (2, '123456', 'Mz', '20210413235937362.jpg', '1245', 1, '向南', 1, '20171308888', 1, 2, 4, '13722655965', 'e10adc3949ba59abbe56e057f20f883e', 1, '2021-04-21 17:48:07', NULL);
INSERT INTO `member` VALUES (3, '1234567', 'MMo', '20210506124749198.jpg', '123456', 1, '李蕊', 2, '20171308777', 1, 2, 4, '13788522656', 'e10adc3949ba59abbe56e057f20f883e', 1, '2021-04-06 17:48:11', NULL);
INSERT INTO `member` VALUES (4, '1223456', 'Teacher张', '20210429202827430.png', '1234566', 2, '张老师', 1, NULL, NULL, NULL, NULL, '13784999211', 'e10adc3949ba59abbe56e057f20f883e', 1, '2021-04-07 17:48:22', NULL);
INSERT INTO `member` VALUES (5, '1233333', 'Teacher李', '20210428114331447.jpg', '1233333', 2, '李老师', 1, NULL, NULL, NULL, NULL, '13784999212', '96e79218965eb72c92a549dd5a330112', 1, '2021-04-14 17:48:27', NULL);
INSERT INTO `member` VALUES (6, '12333333', '平台管理员', '20210428114331447.jpg', '123456', 3, '平台管理员1', 1, NULL, NULL, NULL, NULL, '13784999210', '96e79218965eb72c92a549dd5a330112', 1, '2021-04-19 17:48:32', NULL);
INSERT INTO `member` VALUES (7, '111111', 'mmmm', '20210428114331447.jpg', '123123', 1, '李四', 1, '20171302555', 1, 3, 5, '13784999209', '96e79218965eb72c92a549dd5a330112', 1, '2021-04-07 17:48:36', NULL);
INSERT INTO `member` VALUES (8, NULL, NULL, '20210413234427501.jpg', NULL, 1, '李杰', 1, '20171308132', 1, 2, 4, '15011220078', '96e79218965eb72c92a549dd5a330112', 1, '2021-04-13 22:28:07', NULL);
INSERT INTO `member` VALUES (9, NULL, NULL, '20210428114331447.jpg', NULL, 2, '测试教师', 1, NULL, NULL, NULL, NULL, '13784999222', '96e79218965eb72c92a549dd5a330112', 2, '2021-04-14 13:38:05', NULL);
INSERT INTO `member` VALUES (10, NULL, NULL, '20210428114331447.jpg', NULL, 2, '赵老师', 1, NULL, NULL, NULL, NULL, '15011220012', '96e79218965eb72c92a549dd5a330112', 2, '2021-04-17 21:38:16', NULL);
INSERT INTO `member` VALUES (11, NULL, NULL, '20210428114331447.jpg', NULL, 2, '孙老师', 1, NULL, NULL, NULL, NULL, '15011221122', '96e79218965eb72c92a549dd5a330112', 1, '2021-04-17 21:39:58', NULL);
INSERT INTO `member` VALUES (12, NULL, NULL, '20210428114331447.jpg', NULL, 2, '赵老师', 1, NULL, NULL, NULL, NULL, '13784999219', '96e79218965eb72c92a549dd5a330112', 1, '2021-04-29 13:36:33', NULL);
INSERT INTO `member` VALUES (13, NULL, NULL, '20210428114331447.jpg', NULL, 1, '齐轩', 1, '2017130222', 1, 2, 8, '13784999218', 'e10adc3949ba59abbe56e057f20f883e', 1, '2021-04-29 13:49:58', NULL);

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交流问题id',
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  `class_relation_id` int(11) NULL DEFAULT NULL COMMENT '课程关联id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `post_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课下交流问题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (3, NULL, NULL, 1, '计算机组成原理应该怎么学习', '2021-03-05 20:21:44', NULL, 1);
INSERT INTO `post` VALUES (4, NULL, NULL, 1, '数据结构应该怎么学习', '2021-04-09 21:24:22', NULL, 1);
INSERT INTO `post` VALUES (5, NULL, NULL, 2, '数据结构学习的好方法', '2021-04-09 23:04:15', NULL, 1);
INSERT INTO `post` VALUES (6, NULL, NULL, 1, '学习计算机组成原理的好方法有哪些', '2021-04-09 23:04:48', NULL, 1);
INSERT INTO `post` VALUES (7, NULL, NULL, 1, '数据结构的链表的学习方法', '2021-04-09 23:07:42', NULL, 2);
INSERT INTO `post` VALUES (8, NULL, NULL, 3, '数据结构的链表的学习方法', '2021-05-06 12:36:10', NULL, 1);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `question_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题名称',
  `schedule_id` int(11) NULL DEFAULT NULL COMMENT '课程安排id',
  `class_relation_id` int(11) NULL DEFAULT NULL COMMENT '课程关联id',
  `state` int(2) NULL DEFAULT NULL COMMENT '提问状态（1待回答 2回答完毕）',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '创建人教师用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提问表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (10, '课堂提问', NULL, 9, 2, 4, '2021-05-06 12:22:47', NULL, 1);
INSERT INTO `question` VALUES (11, '提问1', NULL, 9, 2, 4, '2021-05-06 12:25:03', NULL, 1);
INSERT INTO `question` VALUES (12, '提问2', NULL, 9, 2, 4, '2021-05-06 12:28:48', NULL, 1);
INSERT INTO `question` VALUES (13, '提问', NULL, 9, 1, 4, '2021-05-06 16:47:42', NULL, 1);

-- ----------------------------
-- Table structure for question_response
-- ----------------------------
DROP TABLE IF EXISTS `question_response`;
CREATE TABLE `question_response`  (
  `response_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `question_id` int(11) NULL DEFAULT NULL COMMENT '提问id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `answer` int(2) NULL DEFAULT NULL COMMENT '是否作出回答（1被选中回答 2未被选中回答）',
  `grade` int(2) NULL DEFAULT NULL COMMENT '评分等级（1优秀 2良好 3一般 4较差）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`response_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提问响应表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question_response
-- ----------------------------
INSERT INTO `question_response` VALUES (37, 10, 1, 1, 1, '2021-05-06 12:23:06', NULL, 1);
INSERT INTO `question_response` VALUES (38, 10, 2, 1, 2, '2021-05-06 12:23:55', NULL, 1);
INSERT INTO `question_response` VALUES (39, 10, 3, 1, 3, '2021-05-06 12:24:04', NULL, 1);
INSERT INTO `question_response` VALUES (40, 11, 1, 1, 3, '2021-05-06 12:25:27', NULL, 1);
INSERT INTO `question_response` VALUES (41, 11, 2, 1, 1, '2021-05-06 12:25:31', NULL, 1);
INSERT INTO `question_response` VALUES (42, 11, 3, 1, 1, '2021-05-06 12:25:34', NULL, 1);
INSERT INTO `question_response` VALUES (43, 12, 1, 1, 1, '2021-05-06 12:28:59', NULL, 1);
INSERT INTO `question_response` VALUES (44, 12, 2, 1, 4, '2021-05-06 12:29:01', NULL, 1);
INSERT INTO `question_response` VALUES (45, 12, 3, 1, 1, '2021-05-06 12:29:03', NULL, 1);
INSERT INTO `question_response` VALUES (46, 13, 3, 2, NULL, '2021-05-06 16:48:15', NULL, 1);
INSERT INTO `question_response` VALUES (47, 13, 2, 2, NULL, '2021-05-06 16:48:18', NULL, 1);

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课后作业试卷id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课后作业标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `schedule_id` int(11) NULL DEFAULT NULL COMMENT '课程安排id',
  `class_relation_id` int(11) NULL DEFAULT NULL COMMENT '课程关联id',
  `state` int(2) NULL DEFAULT NULL COMMENT '发布状态（1未发布 2已发布）',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '发布人id',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '截止时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课后作业试卷表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (18, '数据结构课后作业1', '数据结构课后作业', NULL, 9, NULL, 4, '2021-05-05 00:00:00', '2021-05-12 00:00:00', '2021-05-06 11:54:50', NULL, 1);
INSERT INTO `task` VALUES (19, '数据结构课后作业2', '按时完成', NULL, 9, NULL, 4, '2021-05-05 00:00:00', '2021-05-13 00:00:00', '2021-05-06 12:03:35', NULL, 1);
INSERT INTO `task` VALUES (20, '数据结构课后作业', '按时完成', NULL, 9, NULL, 4, '2021-05-06 12:09:47', '2021-05-10 00:00:00', '2021-05-06 12:10:08', NULL, 1);
INSERT INTO `task` VALUES (21, '数据结构课后作业4', '按时完成', NULL, 9, NULL, 4, '2021-05-03 00:00:00', '2021-05-05 00:00:00', '2021-05-06 12:11:18', NULL, 1);
INSERT INTO `task` VALUES (22, '数据结构课后作业5', '按时完成', NULL, 9, NULL, 4, '2021-05-03 00:00:00', '2021-05-08 00:00:00', '2021-05-06 12:15:38', NULL, 1);

-- ----------------------------
-- Table structure for task_answer
-- ----------------------------
DROP TABLE IF EXISTS `task_answer`;
CREATE TABLE `task_answer`  (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课后作业回答id',
  `task_id` int(11) NULL DEFAULT NULL COMMENT '课后作业试卷id',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户回答（图片路径）',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `evaluate` int(2) NULL DEFAULT NULL COMMENT '1优秀 2良好 3一般 4较差',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(1) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课后作业回答表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of task_answer
-- ----------------------------
INSERT INTO `task_answer` VALUES (5, 18, '[{\"url\":\"20210506115554491.jpg\"}]', 1, 1, '2021-05-06 11:55:57', '2021-05-06 11:59:17', 1);
INSERT INTO `task_answer` VALUES (6, 18, '[{\"url\":\"20210506120028876.jpg\"}]', 2, 2, '2021-05-06 12:00:30', '2021-05-06 12:00:54', 1);
INSERT INTO `task_answer` VALUES (7, 18, '[{\"url\":\"20210506120139935.jpg\"}]', 3, 3, '2021-05-06 12:01:40', '2021-05-06 12:01:57', 1);
INSERT INTO `task_answer` VALUES (8, 19, '[{\"url\":\"20210506120421703.jpg\"}]', 1, 1, '2021-05-06 12:04:23', '2021-05-06 12:07:24', 1);
INSERT INTO `task_answer` VALUES (9, 19, '[{\"url\":\"20210506120434743.jpg\"}]', 2, 3, '2021-05-06 12:04:36', '2021-05-06 12:07:07', 1);
INSERT INTO `task_answer` VALUES (10, 19, '[{\"url\":\"20210506120606927.jpg\"}]', 3, 1, '2021-05-06 12:06:08', '2021-05-06 12:07:01', 1);
INSERT INTO `task_answer` VALUES (11, 20, '[{\"url\":\"20210506121154902.jpg\"}]', 1, NULL, '2021-05-06 12:11:55', NULL, 1);
INSERT INTO `task_answer` VALUES (12, 20, '[{\"url\":\"20210506121221733.jpg\"}]', 2, 2, '2021-05-06 12:12:22', '2021-05-06 12:13:29', 1);
INSERT INTO `task_answer` VALUES (13, 20, '[{\"url\":\"20210506121233295.jpg\"}]', 3, 2, '2021-05-06 12:12:34', '2021-05-06 12:13:35', 1);

-- ----------------------------
-- Table structure for task_question
-- ----------------------------
DROP TABLE IF EXISTS `task_question`;
CREATE TABLE `task_question`  (
  `question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课后作业问题id',
  `task_id` int(11) NULL DEFAULT NULL COMMENT '课后作业试卷id',
  `question_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '问题排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课后作业问题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of task_question
-- ----------------------------
INSERT INTO `task_question` VALUES (25, 18, '完成p18习题', 1, '2021-05-06 11:54:50', NULL, 1);
INSERT INTO `task_question` VALUES (26, 19, '完成p30课后习题', 1, '2021-05-06 12:03:35', NULL, 1);
INSERT INTO `task_question` VALUES (27, 20, '编写算法复制二叉树', 1, '2021-05-06 12:10:08', NULL, 1);
INSERT INTO `task_question` VALUES (28, 21, '完成p35习题', 1, '2021-05-06 12:11:18', NULL, 1);
INSERT INTO `task_question` VALUES (29, 22, '完成p59课后习题', 1, '2021-05-06 12:15:38', NULL, 1);

-- ----------------------------
-- Table structure for tbstatic
-- ----------------------------
DROP TABLE IF EXISTS `tbstatic`;
CREATE TABLE `tbstatic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(11) NULL DEFAULT NULL COMMENT '静态数据类别（1:学校专业库 2）',
  `parent` int(11) NULL DEFAULT NULL COMMENT '父id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '静态数据表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbstatic
-- ----------------------------
INSERT INTO `tbstatic` VALUES (1, 1, 0, '计算机学院', 1, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (2, 1, 1, '软件工程', 1, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (3, 1, 1, '计算机科学与技术', 2, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (4, 1, 2, '4班', 4, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (5, 1, 3, '4班', 1, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (6, 1, 2, '3班', 3, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (7, 1, 2, '2班', 2, NULL, NULL, 1);
INSERT INTO `tbstatic` VALUES (8, 1, 2, '1班', 1, NULL, NULL, 1);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `teacher_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (3, 4, '张三老师', '13784999210', 1, '2021-01-21 21:16:44', NULL);
INSERT INTO `teacher` VALUES (4, 5, '李四老师', '13784999210', 1, '2021-01-21 21:18:21', NULL);
INSERT INTO `teacher` VALUES (6, NULL, '王五老师', '15011220078', 1, '2021-02-20 17:29:12', NULL);
INSERT INTO `teacher` VALUES (8, NULL, '李六老师', '13784999219', 1, '2021-03-03 16:05:28', NULL);

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `test_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `test_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课堂测验试卷名称',
  `schedule_id` int(11) NULL DEFAULT NULL COMMENT '课程安排id',
  `class_relation_id` int(11) NULL DEFAULT NULL COMMENT '课程关联id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `state` int(2) NULL DEFAULT NULL COMMENT '发布状态（1未发布 2已发布）',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '发布人用户id',
  `duration_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '持续时间（s）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`test_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课堂测验试卷表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (31, '数据结构课堂测验1', NULL, 9, '按时完成', 2, 4, NULL, '2021-05-06 09:38:47', '2021-05-06 09:46:28', 1);
INSERT INTO `test` VALUES (32, '数据结构课堂测验2', NULL, 9, '数据结构课堂测验', 2, 4, NULL, '2021-05-06 09:42:25', '2021-05-06 10:19:57', 1);
INSERT INTO `test` VALUES (33, '数据结构课堂测验3', NULL, 9, '数据结构课堂测验', 2, 4, NULL, '2021-05-06 09:45:26', '2021-05-06 10:21:26', 1);
INSERT INTO `test` VALUES (34, '数据结构课堂测验4', NULL, 9, '课堂测验', 2, 4, NULL, '2021-05-06 10:28:16', '2021-05-06 10:28:29', 1);
INSERT INTO `test` VALUES (35, '数据结构课堂测验', NULL, 9, '课堂测验', 1, 4, NULL, '2021-05-06 14:00:09', NULL, 1);

-- ----------------------------
-- Table structure for test_answer
-- ----------------------------
DROP TABLE IF EXISTS `test_answer`;
CREATE TABLE `test_answer`  (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回答id',
  `test_id` int(11) NULL DEFAULT NULL COMMENT '测试试卷id',
  `question_id` int(11) NULL DEFAULT NULL COMMENT '问题id',
  `choice_id` int(11) NULL DEFAULT NULL COMMENT '回答选项id（单选题回答选项id 判断题 1正确 2错误）',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课堂测验回答表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_answer
-- ----------------------------
INSERT INTO `test_answer` VALUES (40, 31, 38, 108, 1, '2021-05-06 09:50:53', NULL, 1);
INSERT INTO `test_answer` VALUES (41, 31, 39, 111, 1, '2021-05-06 09:50:53', NULL, 1);
INSERT INTO `test_answer` VALUES (42, 31, 38, 107, 2, '2021-05-06 09:52:05', NULL, 1);
INSERT INTO `test_answer` VALUES (43, 31, 39, 111, 2, '2021-05-06 09:52:05', NULL, 1);
INSERT INTO `test_answer` VALUES (44, 31, 38, 108, 3, '2021-05-06 10:11:27', NULL, 1);
INSERT INTO `test_answer` VALUES (45, 31, 39, 110, 3, '2021-05-06 10:11:27', NULL, 1);
INSERT INTO `test_answer` VALUES (46, 32, 40, 114, 1, '2021-05-06 10:20:15', NULL, 1);
INSERT INTO `test_answer` VALUES (47, 32, 41, 118, 1, '2021-05-06 10:20:15', NULL, 1);
INSERT INTO `test_answer` VALUES (48, 32, 40, 114, 2, '2021-05-06 10:20:23', NULL, 1);
INSERT INTO `test_answer` VALUES (49, 32, 41, 118, 2, '2021-05-06 10:20:23', NULL, 1);
INSERT INTO `test_answer` VALUES (50, 32, 40, 114, 3, '2021-05-06 10:20:34', NULL, 1);
INSERT INTO `test_answer` VALUES (51, 32, 41, 117, 3, '2021-05-06 10:20:34', NULL, 1);
INSERT INTO `test_answer` VALUES (52, 33, 42, 121, 1, '2021-05-06 10:21:51', NULL, 1);
INSERT INTO `test_answer` VALUES (53, 33, 43, 125, 1, '2021-05-06 10:21:51', NULL, 1);
INSERT INTO `test_answer` VALUES (54, 33, 42, 120, 2, '2021-05-06 10:22:01', NULL, 1);
INSERT INTO `test_answer` VALUES (55, 33, 43, 125, 2, '2021-05-06 10:22:01', NULL, 1);
INSERT INTO `test_answer` VALUES (56, 33, 42, 120, 3, '2021-05-06 10:22:12', NULL, 1);
INSERT INTO `test_answer` VALUES (57, 33, 43, 125, 3, '2021-05-06 10:22:12', NULL, 1);
INSERT INTO `test_answer` VALUES (62, 34, 44, 130, 1, '2021-05-06 10:44:08', NULL, 1);
INSERT INTO `test_answer` VALUES (63, 34, 45, 132, 1, '2021-05-06 10:44:08', NULL, 1);
INSERT INTO `test_answer` VALUES (64, 34, 46, 136, 1, '2021-05-06 10:44:08', NULL, 1);
INSERT INTO `test_answer` VALUES (65, 34, 47, 139, 1, '2021-05-06 10:44:08', NULL, 1);
INSERT INTO `test_answer` VALUES (66, 34, 44, 130, 2, '2021-05-06 11:42:07', NULL, 1);
INSERT INTO `test_answer` VALUES (67, 34, 45, 133, 2, '2021-05-06 11:42:07', NULL, 1);
INSERT INTO `test_answer` VALUES (68, 34, 46, 135, 2, '2021-05-06 11:42:07', NULL, 1);
INSERT INTO `test_answer` VALUES (69, 34, 47, 140, 2, '2021-05-06 11:42:07', NULL, 1);
INSERT INTO `test_answer` VALUES (70, 34, 44, 130, 3, '2021-05-06 11:42:30', NULL, 1);
INSERT INTO `test_answer` VALUES (71, 34, 45, 133, 3, '2021-05-06 11:42:30', NULL, 1);
INSERT INTO `test_answer` VALUES (72, 34, 46, 136, 3, '2021-05-06 11:42:30', NULL, 1);
INSERT INTO `test_answer` VALUES (73, 34, 47, 139, 3, '2021-05-06 11:42:30', NULL, 1);

-- ----------------------------
-- Table structure for test_choice
-- ----------------------------
DROP TABLE IF EXISTS `test_choice`;
CREATE TABLE `test_choice`  (
  `choice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '选项id',
  `question_id` int(11) NULL DEFAULT NULL COMMENT '测验问题id',
  `choice_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项内容',
  `right_answer` int(2) NULL DEFAULT NULL COMMENT '是否为正确答案（1是 2否）',
  `choice_sort` int(2) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`choice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 153 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课堂测验选项表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_choice
-- ----------------------------
INSERT INTO `test_choice` VALUES (105, 38, '数据的逻辑结构', 2, 1, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (106, 38, '数据的存储结构', 2, 2, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (107, 38, '数据的逻辑结构和存储结构', 2, 3, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (108, 38, '数据的逻辑结构、存储结构及其基本操作', 1, 4, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (109, 39, '图', 2, 1, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (110, 39, '树', 2, 2, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (111, 39, '栈', 1, 3, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_choice` VALUES (112, 40, '完整性规则', 2, 1, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (113, 40, '数据结构', 2, 2, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (114, 40, '恢复', 1, 3, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (115, 40, '数据操作', 2, 4, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (116, 41, '图', 2, 1, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (117, 41, '树', 2, 2, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (118, 41, '栈', 1, 3, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_choice` VALUES (119, 42, '一对多关系', 2, 1, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (120, 42, '多对多关系', 1, 2, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (121, 42, '多对一关系', 2, 3, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (122, 42, '一对多关系', 2, 4, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (123, 43, '完整性规则', 2, 1, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (124, 43, '数据结构', 2, 2, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (125, 43, '恢复', 1, 3, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (126, 43, '数据操作', 2, 4, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_choice` VALUES (127, 44, '数据的逻辑结构', 2, 1, '2021-05-06 10:28:16', NULL, 1);
INSERT INTO `test_choice` VALUES (128, 44, '数据的存储结构', 2, 2, '2021-05-06 10:28:16', NULL, 1);
INSERT INTO `test_choice` VALUES (129, 44, '数据的逻辑结构和存储结构', 2, 3, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (130, 44, '数据的逻辑结构、存储结构及其基本操作', 1, 4, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (131, 45, '图', 2, 1, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (132, 45, '树', 2, 2, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (133, 45, '栈', 1, 3, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (134, 46, '完整性规则', 2, 1, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (135, 46, '数据结构', 2, 2, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (136, 46, '恢复', 1, 3, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (137, 46, '数据操作', 2, 4, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (138, 47, '一对多关系', 2, 1, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (139, 47, '多对多关系', 1, 2, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (140, 47, '多对一关系', 2, 3, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (141, 47, '一对多关系', 2, 4, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_choice` VALUES (142, 48, '图', 2, 1, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_choice` VALUES (143, 48, '树', 2, 2, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_choice` VALUES (144, 48, '栈', 1, 3, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_choice` VALUES (145, 49, '完整性规则', 2, 1, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_choice` VALUES (146, 49, '数据结构', 2, 2, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_choice` VALUES (147, 49, '恢复', 1, 3, '2021-05-06 14:00:10', NULL, 1);
INSERT INTO `test_choice` VALUES (148, 49, '数据操作', 2, 4, '2021-05-06 14:00:10', NULL, 1);
INSERT INTO `test_choice` VALUES (149, 50, '一对多关系', 2, 1, '2021-05-06 14:00:10', NULL, 1);
INSERT INTO `test_choice` VALUES (150, 50, '多对多关系', 1, 2, '2021-05-06 14:00:10', NULL, 1);
INSERT INTO `test_choice` VALUES (151, 50, '多对一关系', 2, 3, '2021-05-06 14:00:10', NULL, 1);
INSERT INTO `test_choice` VALUES (152, 50, '一对多关系', 2, 4, '2021-05-06 14:00:10', NULL, 1);

-- ----------------------------
-- Table structure for test_question
-- ----------------------------
DROP TABLE IF EXISTS `test_question`;
CREATE TABLE `test_question`  (
  `question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题id',
  `test_id` int(11) NULL DEFAULT NULL COMMENT '课堂测验试卷id',
  `question_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题名称',
  `type` int(2) NULL DEFAULT NULL COMMENT '1选择题 2判断题',
  `sort` int(11) NULL DEFAULT NULL COMMENT '问题排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_state` int(2) NULL DEFAULT NULL COMMENT '1未删除 2逻辑删除',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课堂测验问题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_question
-- ----------------------------
INSERT INTO `test_question` VALUES (38, 31, '研究数据结构就是研究_____', NULL, 1, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_question` VALUES (39, 31, '具有线性结构的数据结构是_____', NULL, 2, '2021-05-06 09:38:47', NULL, 1);
INSERT INTO `test_question` VALUES (40, 32, '关系数据模型的三个组成部分中，不包括_____', NULL, 1, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_question` VALUES (41, 32, '具有线性结构的数据结构是_____', NULL, 2, '2021-05-06 09:42:25', NULL, 1);
INSERT INTO `test_question` VALUES (42, 33, '非线性结构是数据元素之间存在一种_____', NULL, 1, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_question` VALUES (43, 33, '关系数据模型的三个组成部分中，不包括_____', NULL, 2, '2021-05-06 09:45:26', NULL, 1);
INSERT INTO `test_question` VALUES (44, 34, '研究数据结构就是研究_____', NULL, 1, '2021-05-06 10:28:16', NULL, 1);
INSERT INTO `test_question` VALUES (45, 34, '具有线性结构的数据结构是_____', NULL, 2, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_question` VALUES (46, 34, '关系数据模型的三个组成部分中，不包括_____', NULL, 3, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_question` VALUES (47, 34, '非线性结构是数据元素之间存在一种_____', NULL, 4, '2021-05-06 10:28:17', NULL, 1);
INSERT INTO `test_question` VALUES (48, 35, '具有线性结构的数据结构是_____', NULL, 1, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_question` VALUES (49, 35, '关系数据模型的三个组成部分中，不包括_____', NULL, 2, '2021-05-06 14:00:09', NULL, 1);
INSERT INTO `test_question` VALUES (50, 35, '非线性结构是数据元素之间存在一种_____', NULL, 3, '2021-05-06 14:00:10', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
