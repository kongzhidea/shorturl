CREATE TABLE `short_url` (
  `id` bigint(20) NOT NULL auto_increment,
  `original_url` varchar(500) character set utf8 collate utf8_bin default '',
  `encrypted_url` varchar(10) character set utf8 collate utf8_bin default '',
  `create_time` datetime NOT NULL,
  `visit_count` int(11) default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `encrypted_url` (`encrypted_url`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 