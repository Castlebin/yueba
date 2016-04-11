-- --------------------------------------------------------
-- Host:                         172.16.1.47
-- Server version:               5.6.20-log - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table ggq.activity
CREATE TABLE IF NOT EXISTS `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `activity_address` varchar(255) DEFAULT NULL,
  `activity_address_lat` double DEFAULT NULL,
  `activity_address_lng` double DEFAULT NULL,
  `activity_begin_time` timestamp NULL DEFAULT NULL,
  `activity_end_time` timestamp NULL DEFAULT NULL,
  `apply_begin_time` timestamp NULL DEFAULT NULL,
  `apply_end_time` timestamp NULL DEFAULT NULL,
  `assembling_address` varchar(255) DEFAULT NULL,
  `assembling_address_lat` double DEFAULT NULL,
  `assembling_address_lng` double DEFAULT NULL,
  `description` varchar(1024) NOT NULL,
  `username` varchar(64) NOT NULL,
  `max_age` int(11) DEFAULT NULL,
  `min_age` int(11) DEFAULT NULL,
  `people_limit` int(11) DEFAULT NULL,
  `pic` varchar(255) NOT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `apply_female_count` int(11) NOT NULL,
  `apply_male_count` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `open` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ggq.activity: ~9 rows (approximately)
DELETE FROM `activity`;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` (`id`, `create_time`, `modify_time`, `activity_address`, `activity_address_lat`, `activity_address_lng`, `activity_begin_time`, `activity_end_time`, `apply_begin_time`, `apply_end_time`, `assembling_address`, `assembling_address_lat`, `assembling_address_lng`, `description`, `username`, `max_age`, `min_age`, `people_limit`, `pic`, `price`, `title`, `apply_female_count`, `apply_male_count`, `version`, `open`) VALUES
	(1, '2016-04-10 03:14:49', '2016-04-10 03:32:16', '香山', NULL, NULL, '2016-04-12 03:14:00', '2016-04-13 03:14:00', NULL, '2016-04-11 03:14:00', '五道口地铁站', NULL, NULL, '香山公园位于北京西郊，地势险峻，苍翠连绵，占地188公顷，是一座具有山林特色的皇家园林。景区内主峰香炉峰俗称“鬼见愁”，海拔575米。香山公园始建于金大定二十六年，距今已有近900年的历史。香山公园有香山寺、洪光寺等著名旅游景点。 香山公园于1993年被评为首都文明单位，2001年被国家旅游局评为AAAA景区，2002年被评为首批北京市精品公园。', 'gaoyuxin', 30, 25, 12, '/upload/91683dde-9552-4e57-a5a4-a5ba0f8649f3.jpg', 30.00, '香山一日游', 1, 1, 2, b'1'),
	(2, '2016-04-10 03:20:16', '2016-04-10 09:15:32', '北海公园', NULL, NULL, '2016-04-10 14:15:00', '2016-04-10 21:15:00', NULL, '2016-04-10 10:15:00', '五道口清华科技园', NULL, NULL, '北海的樱花不及你的容颜，听说下雨天程序员和软妹子更配哦～', 'qinxue', 30, 25, 5, '/upload/8c1001e93901213f58f5e6a554e736d12e2e9581.jpg', 50.00, '开发完项目出去疯玩', 1, 1, 2, b'1'),
	(3, '2016-04-10 05:19:13', '2016-04-10 06:00:35', '19F', NULL, NULL, '2016-04-10 10:18:00', '2016-04-12 05:18:00', NULL, '2016-04-10 06:18:00', '清华科技园', NULL, NULL, '快闪是“快闪影片”或“快闪行动”的简称,是新近在国际流行开的一种嬉皮行为，可视为一种短暂的行为艺术。简单地说就是：许多人用网络或其它方式，在一个指定的地点，在明确指定的时间，出人意料的同时做一系列指定的歌舞或其它行为，然后迅速闪开。“快闪行动”初期是纯为搞笑或是膜拜纪念，而后，当“快闪行动”被制作为专业的“快闪影片”之后，在网络有很大的传播影响力，在公益、商业等各个领域充分的发挥了不少正面作用。', 'gaoyuxin', 30, 25, 12, '/upload/4110dc6b-1706-4892-9196-522400254e81.png', 0.00, '快闪', 1, 1, 2, b'1'),
	(4, '2016-04-10 05:22:26', '2016-04-10 06:01:29', '康顿庄园', NULL, NULL, '2016-04-10 15:22:00', '2016-04-11 05:22:00', NULL, '2016-04-10 11:22:00', '康顿庄园', NULL, NULL, '品酒并不是喝酒，品酒是一门学问。譬如欣赏一幅画、听一首音乐，如果你没有美术和音乐的修养，就不可能说出他的好坏。品酒并不是大师们的专利，掌握它，你将真正享受到酒的美妙。', 'yuran', 30, 25, 12, '/upload/5fb1fcc2d4a9ff206915aaf675aca5f3.jpg', 350.00, '品酒会', 1, 1, 2, b'1'),
	(5, '2016-04-10 05:31:52', '2016-04-10 09:24:44', '青海湖', NULL, NULL, '2016-04-11 05:30:00', '2016-04-12 05:30:00', NULL, '2016-04-11 01:30:00', '青海湖', NULL, NULL, '青海湖又名“措温布”，即藏语“青色的海”之意。它位于青海省西北部的青海湖盆地内，既是中国最大的内陆湖泊，也是中国最大的咸水湖。由祁连山的大通山、日月山与青海南山之间的断层陷落形成。\r\n2012年7月30日，青海省气象科学研究所最新的遥感监测结果显示，中国国最大内陆咸水湖青海湖面积持续8年增大。\r\n2014年10月20日9时50分，青海湖海心山北侧出现“龙吸水”壮观场景。[1] ', 'yuran', 30, 25, 12, '/upload/9e4b2f46-0382-4e70-a536-ee27604d0958.jpg', 521.00, '青海湖之旅', 2, 2, 4, b'1'),
	(6, '2016-04-10 08:11:25', '2016-04-10 08:20:53', '西土城', NULL, NULL, '2016-04-10 18:07:00', '2016-04-10 23:07:00', NULL, '2016-04-10 13:07:00', '五道口', NULL, NULL, '跟对的人在一起每天都是情人节。', '陈小桂', 30, 25, 2, '/upload/d152ebfced7281cdcc473949b5d188a7.jpg', 75.00, '吃玩', 1, 0, 1, b'1'),
	(7, '2016-04-10 09:26:13', '2016-04-10 12:32:20', '皇庭广场G层中庭', NULL, NULL, '2016-04-11 11:24:00', '2016-04-12 09:24:00', NULL, '2016-04-11 06:24:00', '皇庭广场G层中庭', NULL, NULL, '吃，是一件特别正经的事儿\r\n甜，是一种非常幸福的味道\r\n所以爱“吃甜”的人，运气都不会特别差。\r\n4月25日、26日、27日三天\r\n皇庭广场G层中庭\r\n我们汇集了zui具代表的20家\r\n品类zui全、品种zui多、品牌zui明星气质\r\n打造宇宙最强“甜品星系”\r\n号召地球里的‘甜品星人”\r\n吃！起！来！\r\n', '龙莲', 30, 25, 12, '/upload/30862083997121314.jpg', 50.00, '甜品嘉年华', 1, 1, 2, b'1'),
	(8, '2016-04-10 09:28:03', '2016-04-10 12:37:12', '大连市第八中学', NULL, NULL, '2016-05-01 14:23:00', '2016-05-03 22:23:00', NULL, '2016-04-30 18:23:00', '西安路百盛商场', NULL, NULL, '梦里魂牵梦绕，坐在后排的女生，篮球场，八角亭', 'ping', 30, 25, 60, '/upload/5d0d4f11-fa2a-4f4b-be7f-65ad09db39a6.jpg', 0.00, '回母校看看', 2, 1, 3, b'1'),
	(9, '2016-04-10 12:31:25', '2016-04-10 12:34:56', '五道口', NULL, NULL, '2016-04-10 19:31:00', '2016-04-10 23:31:00', NULL, '2016-04-10 14:31:00', '五道口', NULL, NULL, '哈哈', 'qinxue', 30, 25, 2, '/upload/fbdffe98-c414-4d27-9991-2c2cc64530a7.jpg', 24.00, '开发完去吃饭', 1, 1, 2, b'1');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;


-- Dumping structure for table ggq.activity_tag
CREATE TABLE IF NOT EXISTS `activity_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `activity_id` bigint(20) NOT NULL,
  `tag_name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `activity_id_tag_name` (`activity_id`,`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ggq.activity_tag: ~2 rows (approximately)
DELETE FROM `activity_tag`;
/*!40000 ALTER TABLE `activity_tag` DISABLE KEYS */;
INSERT INTO `activity_tag` (`id`, `create_time`, `modify_time`, `activity_id`, `tag_name`) VALUES
	(1, '2016-04-09 19:07:07', '2016-04-09 19:07:08', 1, 'K歌'),
	(2, '2016-04-09 19:07:18', '2016-04-09 19:07:19', 1, '户外');
/*!40000 ALTER TABLE `activity_tag` ENABLE KEYS */;


-- Dumping structure for table ggq.activity_user
CREATE TABLE IF NOT EXISTS `activity_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `username` varchar(64) NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `comment_for_activity` decimal(19,2) DEFAULT NULL,
  `comment_for_activity_content` varchar(255) DEFAULT NULL,
  `comment_for_initiator` decimal(19,2) DEFAULT NULL,
  `comment_for_initiator_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_activity_id` (`username`,`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ggq.activity_user: ~20 rows (approximately)
DELETE FROM `activity_user`;
/*!40000 ALTER TABLE `activity_user` DISABLE KEYS */;
INSERT INTO `activity_user` (`id`, `create_time`, `modify_time`, `username`, `activity_id`, `comment_for_activity`, `comment_for_activity_content`, `comment_for_initiator`, `comment_for_initiator_content`) VALUES
	(1, '2016-04-10 03:14:49', '2016-04-10 03:14:49', 'gaoyuxin', 1, NULL, NULL, NULL, NULL),
	(2, '2016-04-10 03:20:16', '2016-04-10 03:20:16', 'qinxue', 2, NULL, NULL, NULL, NULL),
	(3, '2016-04-10 03:32:16', '2016-04-10 03:32:16', '欧阳娜娜', 1, NULL, NULL, NULL, NULL),
	(4, '2016-04-10 05:19:13', '2016-04-10 05:19:13', 'gaoyuxin', 3, NULL, NULL, NULL, NULL),
	(5, '2016-04-10 05:20:24', '2016-04-10 05:20:24', 'yuran', 3, NULL, NULL, NULL, NULL),
	(6, '2016-04-10 05:22:26', '2016-04-10 05:22:26', 'yuran', 4, NULL, NULL, NULL, NULL),
	(7, '2016-04-10 05:22:48', '2016-04-10 05:22:48', 'gaoyuxin', 4, NULL, NULL, NULL, NULL),
	(8, '2016-04-10 05:31:52', '2016-04-10 05:31:52', 'yuran', 5, NULL, NULL, NULL, NULL),
	(9, '2016-04-10 05:32:29', '2016-04-10 05:32:29', 'gaoyuxin', 5, NULL, NULL, NULL, NULL),
	(10, '2016-04-10 08:11:25', '2016-04-10 08:11:25', '陈小桂', 6, NULL, NULL, NULL, NULL),
	(11, '2016-04-10 09:15:32', '2016-04-10 09:15:32', '蔷蔷', 2, NULL, NULL, NULL, NULL),
	(12, '2016-04-10 09:20:59', '2016-04-10 09:20:59', '蔷蔷', 5, NULL, NULL, NULL, NULL),
	(13, '2016-04-10 09:24:44', '2016-04-10 09:24:44', 'wainguo', 5, NULL, NULL, NULL, NULL),
	(14, '2016-04-10 09:26:13', '2016-04-10 09:26:13', '龙莲', 7, NULL, NULL, NULL, NULL),
	(15, '2016-04-10 09:28:03', '2016-04-10 09:28:03', 'ping', 8, NULL, NULL, NULL, NULL),
	(16, '2016-04-10 09:34:16', '2016-04-10 09:34:16', 'qinxue', 8, NULL, NULL, NULL, NULL),
	(17, '2016-04-10 12:31:25', '2016-04-10 12:31:25', 'qinxue', 9, NULL, NULL, NULL, NULL),
	(18, '2016-04-10 12:32:20', '2016-04-10 12:32:20', 'qinxue', 7, NULL, NULL, NULL, NULL),
	(19, '2016-04-10 12:34:56', '2016-04-10 12:34:56', 'xiongmingjing', 9, NULL, NULL, NULL, NULL),
	(20, '2016-04-10 12:37:12', '2016-04-10 12:37:12', 'qinning', 8, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `activity_user` ENABLE KEYS */;


-- Dumping structure for table ggq.tag
CREATE TABLE IF NOT EXISTS `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(64) NOT NULL,
  `description` varchar(64) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ggq.tag: ~6 rows (approximately)
DELETE FROM `tag`;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`id`, `tag_name`, `description`, `create_time`, `modify_time`, `name`) VALUES
	(1, '户外', '户外', '2016-04-08 19:39:36', '2016-04-08 18:36:38', ''),
	(2, 'K歌', 'K歌', '2016-04-08 19:39:57', '2016-04-08 18:36:38', ''),
	(3, '滑雪', '滑雪', '2016-04-08 19:39:59', '2016-04-08 18:36:38', ''),
	(4, '美食', '美食', '2016-04-08 19:40:02', '2016-04-08 18:36:37', ''),
	(5, '爬山', '爬山', '2016-04-08 19:40:05', '2016-04-08 18:36:37', ''),
	(6, '酒会', '酒会', '2016-04-08 19:40:07', '2016-04-08 18:36:37', '');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;


-- Dumping structure for table ggq.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `birthday` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `grade` decimal(19,2) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `participate_count` int(11) NOT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ggq.user: ~14 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `salt`, `nickname`, `gender`, `birthday`, `create_time`, `modify_time`, `grade`, `avatar`, `participate_count`, `mobile`) VALUES
	(1, 'qinxue', 'toANVRc3zLd0J7DRw5qnpA==', 'f14da145-206e-48c0-845f-50fcc287e093', 'wojiameizimengmengda', 'MALE', '1986-10-23', '2016-04-10 03:11:18', '2016-04-10 12:33:33', 0.00, '/upload/c4d611f1-bee0-4961-9c92-3c9b6494b35d.jpeg', 4, '18701116712'),
	(2, 'gaoyuxin', 'abYgUC0msSo3vgBTwZVDGg==', 'f08d0b04-f1d5-43e7-9a5e-ac54111fbce1', '项空月', 'MALE', '1990-02-15', '2016-04-10 03:11:43', '2016-04-10 05:32:29', 4.75, NULL, 4, '15012341234'),
	(3, 'wainguo', 'rkOGSjM2ORbM4WmOf0pVaw==', '0ecac7c7-b6e7-4eca-a451-26b74cf30c02', '星哥', 'MALE', '1990-02-15', '2016-04-10 03:16:30', '2016-04-10 09:24:44', 0.00, '/upload/8fc491b3-1352-4bd6-ad7d-07b6cbd9f978.jpg', 1, '13466375130'),
	(4, '欧阳娜娜', '2QxAiL5SPMioL/qwOachGw==', 'b5aca206-c2a5-4650-988d-a51527d38101', 'nana', 'FEMALE', '1991-03-14', '2016-04-10 03:30:57', '2016-04-10 03:33:53', 0.00, '/upload/fbaf30d6-9422-4448-be79-f55ddedafee1.jpg', 1, '18716235368'),
	(5, 'yuran', '8m1WwQhJm4/9CXa8b0Rx7A==', '13538f90-308b-4679-b5f8-0edd7d985909', '羽然', 'FEMALE', '1990-02-14', '2016-04-10 05:20:14', '2016-04-10 05:31:52', 0.00, NULL, 3, '15012341233'),
	(6, '陈小桂', 'w3WRVLzLKDM/tfVXxPbByw==', '00e1b340-59ef-4408-afea-33d2e7b8bcb8', 'chenxiaoguo', 'FEMALE', '1990-02-14', '2016-04-10 07:57:49', '2016-04-10 08:11:25', 0.00, '/upload/6b5dfad4-03e2-4c5d-bc03-dc6a544aa769.jpg', 1, '18765122578'),
	(7, '潇潇', 'pgn+4UBNnLpxy5PJlg/y2g==', 'b6ecfbeb-b7e4-4aa4-b0ac-81054915ac72', 'Lisa', 'FEMALE', '1990-02-28', '2016-04-10 09:09:22', '2016-04-10 09:09:22', 0.00, NULL, 0, '187012345421'),
	(8, '晶晶', 'l+Mz84IX6qgtJragUh5fwg==', 'a8f13992-dec1-4ea6-a00d-c27cdb064fe2', 'angela', 'FEMALE', '1985-09-20', '2016-04-10 09:11:06', '2016-04-10 09:11:06', 0.00, NULL, 0, '18764443288'),
	(9, '蔷蔷', 'QYLjDJXNWYzzvndAJXfOdg==', 'f8127828-ba8b-4c0b-87f7-a6a0b7a5ce95', 'lesley', 'FEMALE', '1988-10-16', '2016-04-10 09:14:33', '2016-04-10 09:20:59', 0.00, '/upload/08e6d64c-6f6c-4272-babf-8f3e69ac2f59.jpg', 2, '18612345824'),
	(10, '龙莲', 'bDfSW9bUDK0NuXq1yLrgSQ==', '5bb2d14a-ebb8-4c8d-93b6-bb21aa82ceff', '天罗第一美女', 'FEMALE', '1990-02-06', '2016-04-10 09:15:48', '2016-04-10 09:26:13', 0.00, '/upload/4f1e9aae-1b97-4776-ba2e-0b0397c0ed1a.jpg', 1, '15012341111'),
	(11, 'ping', '8x6SnknTC2OJRRn7sgstMw==', 'bce27cf4-a86a-4cc8-96c0-da9c89f7c891', 'ping', 'FEMALE', '1986-07-22', '2016-04-10 09:22:46', '2016-04-10 09:28:29', 0.00, '/upload/dbc23943-91b4-4b2b-a2ce-85d0888852ae.jpg', 1, '13324652789'),
	(12, 'xiongmingjing', 'RKPsWV8HNJA84x+48UbAhQ==', 'a8f83336-d2df-4eae-a294-31a63a731b50', 'bear', 'FEMALE', '1990-02-15', '2016-04-10 12:34:27', '2016-04-10 12:34:56', 0.00, NULL, 1, '187044443215'),
	(13, 'qinning', 'dKK9EbcqRO05gI+hdzx7vA==', 'e0d5c0af-8f87-4ea9-babf-5d3a7fa2a399', 'qn', 'FEMALE', '1990-02-14', '2016-04-10 12:37:00', '2016-04-10 12:37:12', 0.00, NULL, 1, '123568532'),
	(14, 'yangyu', 'KiMmCOGZYgprioInLWR5uA==', '696eb24d-4f66-4238-946e-14b51197fcf3', 'yy', 'FEMALE', '1990-02-14', '2016-04-10 12:37:39', '2016-04-10 12:37:39', 0.00, NULL, 0, '12386523541');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table ggq.user_tag
CREATE TABLE IF NOT EXISTS `user_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tag_name` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_name_username` (`tag_name`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ggq.user_tag: ~2 rows (approximately)
DELETE FROM `user_tag`;
/*!40000 ALTER TABLE `user_tag` DISABLE KEYS */;
INSERT INTO `user_tag` (`id`, `create_time`, `modify_time`, `tag_name`, `username`) VALUES
	(1, '2016-04-09 19:06:28', '2016-04-09 19:06:29', '户外', 'gaoyuxin'),
	(2, '2016-04-09 19:06:43', '2016-04-09 19:06:52', 'K歌', 'gaoyuxin');
/*!40000 ALTER TABLE `user_tag` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
