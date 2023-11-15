SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `organizations`;
CREATE TABLE IF NOT EXISTS `organizations` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

INSERT INTO `organizations` 
(`id`, `name`, `created`, `updated`) VALUES
(1, 'Default Organization', '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `organization_id` int UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

INSERT INTO `users` 
(`id`, `organization_id`, `name`, `fullname`, `email`, `created`, `updated`) VALUES
(1, 1, 'jsmith', 'John Smith', 'jsmith1@gmail.com', '2022-09-20 19:33:20', '2022-09-20 19:33:20'),
(2, 1, 'jwilliams', 'James Williams', 'jwilliams1@gmail.com', '2022-09-20 19:38:09', '2022-09-20 19:38:09'),
(3, 1, 'pparker', 'Peter Parker', 'pparker1@gmail.com', '2022-09-20 19:41:37', '2022-09-20 19:41:37'),
(4, 1, 'wwilcox', 'Walter Wilcox', 'wwilcox1@gmail.com', '2022-09-21 11:00:57', '2022-09-21 11:00:57'),
(5, 1, 'astairs', 'Alma Stairs', 'astairs@gmail.com', '2022-09-21 11:02:25', '2022-09-21 11:02:25'),
(6, 1, 'hsimpson', 'Homer Simpson', 'hsimpson@gmail.com', '2022-09-21 11:03:44', '2022-09-21 11:03:44');

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

INSERT INTO `roles` 
(`id`, `name`, `created`, `updated`) VALUES
(1, 'Help Desk Agent', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(2, 'Help Desk Supervisor', '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `departments`;
CREATE TABLE IF NOT EXISTS `departments` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

INSERT INTO `departments` 
(`id`, `name`, `created`, `updated`) VALUES
(1, 'Default Department', '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `department_id` int UNSIGNED NOT NULL,
  `role_id` int UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `department_id` (`department_id`),
  KEY `role_id` (`role_id`),
  KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

INSERT INTO `staff` 
(`id`, `department_id`, `role_id`, `name`, `fullname`, `email`, `password`, `created`, `updated`) VALUES
(1, 1, 2, 'ishtiaq', 'Ishtiaq Ahmed', 'ishtiaq@nmsu.edu', 'ishtiaq123', '2022-09-20 19:33:20', '2022-09-20 19:33:20'),
(2, 1, 1, 'israel', 'Israel Gonzalez', 'igonzals@nmsu.edu', 'israel123', '2022-09-20 19:38:09', '2022-09-20 19:38:09'),
(3, 1, 1, 'mengyang', 'Meng-Yang Tseng', 'mengyang@nmsu.edu', 'mengyang123', '2022-09-20 19:41:37', '2022-09-20 19:41:37'),
(4, 1, 1, 'everett', 'Everett Bukowski', 'everettb@nmsu.edu', 'everett123', '2022-09-21 11:00:57', '2022-09-21 11:00:57'),
(5, 1, 1, 'bali', 'Baokun Li', 'bali@nmsu.edu', 'bali123', '2022-09-21 11:02:25', '2022-09-21 11:02:25');

DROP TABLE IF EXISTS `slas`;
CREATE TABLE IF NOT EXISTS `slas` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `addhours` int NOT NULL,
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

INSERT INTO `slas` 
(`id`, `name`, `addhours`, `created`, `updated`) VALUES
(1, 'Within 1 hour', 1, '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(2, 'Within 1 day', 24, '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(3, 'Within 1 week', 168, '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(4, 'Within 30 days', 720, '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `ticket_status`;
CREATE TABLE IF NOT EXISTS `ticket_status` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

INSERT INTO `ticket_status` 
(`id`, `name`, `created`, `updated`) VALUES
(1, 'Opened', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(2, 'Assigned', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(3, 'Resolved', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(4, 'Closed', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(5, 'Deleted', '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `ticket_priority`;
CREATE TABLE IF NOT EXISTS `ticket_priority` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `color` varchar(10) NOT NULL DEFAULT '#ffffcc',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

INSERT INTO `ticket_priority` 
(`id`, `name`, `color`, `created`, `updated`) VALUES
(1, 'Low', '#ffffcc', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(2, 'Normal', '#fed976', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(3, 'High', '#fd8d3c', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(4, 'Emergency', '#e31a1c', '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `helptopic`;
CREATE TABLE IF NOT EXISTS `helptopic` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

INSERT INTO `helptopic` 
(`id`, `name`, `created`, `updated`) VALUES
(1, 'Inquiry', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(2, 'Feedback', '2022-09-21 01:33:18', '2022-09-21 01:33:18'),
(3, 'Issue', '2022-09-21 01:33:18', '2022-09-21 01:33:18');

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE IF NOT EXISTS `tickets` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` varchar(20) DEFAULT NULL,
  `user_id` int UNSIGNED NOT NULL DEFAULT '0',
  `staff_id` int UNSIGNED NOT NULL DEFAULT '0',
  `sla_id` int UNSIGNED NOT NULL DEFAULT '0',
  `status_id` int UNSIGNED NOT NULL DEFAULT '0',
  `helptopic_id` int UNSIGNED NOT NULL DEFAULT '0',
  `priority_id` int UNSIGNED NOT NULL DEFAULT '0',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NULL,
  `duedate` datetime DEFAULT NULL,
  `closed` datetime DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `staff_id` (`staff_id`),
  KEY `status_id` (`status_id`),
  KEY `helptopic_id` (`helptopic_id`),
  KEY `sla_id` (`sla_id`),
  KEY `closed` (`duedate`),
  KEY `duedate` (`closed`),
  KEY `created` (`created`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;

INSERT INTO `tickets` 
(`id`, `number`, `user_id`, `staff_id`, `sla_id`, `status_id`, `helptopic_id`, `priority_id`, `subject`, `duedate`, `closed`, `created`, `updated`) VALUES 
( 1, '234512', 1, 1, 1, 1, 1, 1, 'A lot of people think you dont have to do that with baseball', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 2, '345789', 2, 2, 2, 2, 3, 2, 'Increasing demands of consumers across the globe', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 3, '274511', 3, 3, 3, 3, 3, 3, 'Walter had talked me through the procedure', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 4, '345790', 4, 4, 2, 1, 3, 4, 'Thursday I went to sewing class with a new friend', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 5, '774502', 5, 5, 2, 2, 1, 1, 'The silverware drawer is empty', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 6, '995781', 6, 2, 4, 3, 2, 2, 'The U.S. government wants it', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 7, '345313', 1, 4, 2, 1, 3, 3, 'Warner was only a short distance away, and he reached the log first', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 8, '847976', 2, 3, 2, 2, 3, 4, 'The last time I saw the writer was at a bar in Topanga', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
( 9, '086478', 3, 1, 3, 3, 1, 1, 'You do the math, people', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(10, '293974', 4, 5, 2, 1, 3, 1, 'I believe, very strongly, in sound policy', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(11, '123948', 5, 1, 1, 2, 1, 2, 'You also had the honor of having them recognized', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(12, '084752', 6, 2, 3, 3, 3, 2, 'I did see the wee man from across the street the other day', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(13, '745729', 1, 3, 4, 3, 1, 3, 'Bill told me that he would come back to the house', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(14, '434489', 2, 3, 2, 2, 2, 3, 'The vetting of institutions is a major operation for the institutions', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(15, '887679', 3, 5, 2, 1, 2, 4, 'Emails obtained by The Post show they have been in discussions for a while', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(16, '548027', 4, 4, 1, 2, 3, 3, 'This is an area of the state and community that is going through an identity crisis', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(17, '483625', 5, 2, 2, 2, 3, 3, 'They were able to go and help pack it', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(18, '047392', 6, 4, 2, 2, 3, 3, 'The catch is that tickets will have to be purchased ahead of time', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(19, '131211', 1, 1, 3, 1, 3, 2, 'Caymanite Ocean Racing (CORE) will be hosting the 14th annual Annual Reef Race this Saturday', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(20, '111139', 2, 3, 3, 2, 3, 2, 'Because of it, every day I wake up in a good mood', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(21, '968402', 3, 3, 2, 3, 3, 2, 'A half-hour later, the group reached the rolling hills', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(22, '857390', 4, 3, 1, 3, 1, 2, 'A friend of mine a few years ago posted something about her daughter', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(23, '975930', 5, 1, 4, 1, 2, 2, 'And, yes, there is light at the end of the tunnel', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(24, '856456', 6, 2, 2, 2, 3, 1, 'I think I had a peace about this whole thing', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(25, '657761', 1, 4, 2, 2, 3, 1, 'In 2012, he and his wife Mary opened the Annandale Arts Center', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(26, '234822', 2, 5, 3, 3, 3, 2, 'Our heroes find themselves in another bizzare situation', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(27, '212121', 1, 3, 3, 1, 3, 2, 'How come some programmers are so special?', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(28, '910297', 2, 2, 3, 2, 2, 2, 'The problem I had with this was that all', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(29, '712938', 1, 1, 4, 1, 1, 1, 'The young Viola was visiting her father', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18'),
(30, '078291', 1, 5, 3, 1, 1, 1, 'Global Fund reported $222 million worth of corruption and mismanagement', '2022-10-10 02:33:18', NULL, '2022-10-10 01:33:18', '2022-10-11 01:33:18');

DROP TABLE IF EXISTS `threads`;
CREATE TABLE IF NOT EXISTS `threads` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `ticket_id` int UNSIGNED NOT NULL,
  `type` varchar(1) NOT NULL DEFAULT 'I',
  `lastresponse` datetime NULL DEFAULT NULL,
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `type` (`type`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `thread_entries`;
CREATE TABLE IF NOT EXISTS `thread_entries` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `thread_id` int UNSIGNED NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '',
  `body` text NULL,
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(1),
  PRIMARY KEY (`id`),
  KEY `thread_id` (`thread_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;