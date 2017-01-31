
/**
 * Author:  cas
 * Created: Jan 30, 2017
 */

CREATE TABLE `employee` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL DEFAULT '',
  `personal_id` varchar(30) NOT NULL DEFAULT '',
  `phone` varchar(15) NOT NULL DEFAULT '',
  `password` varchar(100) DEFAULT '',
  `role` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
