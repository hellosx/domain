CREATE TABLE `domain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `domain_name` varchar(32) DEFAULT NULL COMMENT '域名名称',
  `domain_length` int(4) DEFAULT NULL COMMENT '域名长度',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2783 DEFAULT CHARSET=utf8;


CREATE TABLE `domain_mapper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `domain_id` bigint(20) DEFAULT NULL,
  `domain_name` varchar(32) DEFAULT NULL,
  `domain_message` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8;
