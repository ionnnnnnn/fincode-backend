drop database if exists `fincode`;
create database `fincode` default CHARACTER set utf8;
use `fincode`;
drop table if exists `user`;
drop table if exists `stock_detail`;
drop table if exists `stock`;
drop table if exists `industry`;

drop table if exists `user`;
create table user
(
    id           int unsigned auto_increment          not null
        primary key,
    passport     varchar(255)                         null,
    password     varchar(255)                         null,
--    salt         varchar(255)                         null,
    nickname     varchar(255)                         null,
    gmt_created  datetime   default CURRENT_TIMESTAMP null,
    gmt_modified datetime   default CURRENT_TIMESTAMP null
);

drop table if exists `industry`;
create table industry
(
    id           int                                  not null
        primary key,
    name         varchar(255)                         null,
    is_deleted   tinyint(1) default 0                 null,
    gmt_created  datetime   default CURRENT_TIMESTAMP null,
    gmt_modified datetime   default CURRENT_TIMESTAMP null
);

drop table if exists `stock`;
create table stock
(
    id           int unsigned auto_increment
        primary key,
    name         varchar(32)                          null,
    ts_code      varchar(32)                          null,
    industry_id  int                                  null,
    is_deleted   tinyint(1) default 0                 null,
    gmt_created  datetime   default CURRENT_TIMESTAMP null,
    gmt_modified datetime   default CURRENT_TIMESTAMP null,
    constraint stock_industry_id_fk
        foreign key (industry_id) references industry (id)
);

drop table if exists `stock_detail`;
create table stock_detail
(
    id           int auto_increment
        primary key,
    stock_id     int unsigned                         null,
    name         varchar(32)                          null,
    enname       varchar(255)                         null,
    ts_code      varchar(32)                          null,
    list_date    varchar(32)                          null,
    area         varchar(32)                          null,
    industry_id  int                                  null,
    is_deleted   tinyint(1) default 0                 null,
    gmt_created  datetime   default CURRENT_TIMESTAMP null,
    gmt_modified datetime   default CURRENT_TIMESTAMP null,
    ext_info     text                                 null,
    constraint stock_detail_stock_fk
        foreign key (stock_id) references stock (id)
);

drop table if exists `stock_price`;
create table stock_price
(
    id        bigint auto_increment
        primary key,
    amount    double      null,
    `change`  double      null,
    close     double      null,
    high      double      null,
    low       double      null,
    open      double      null,
    companyId varchar(32) null,
    pct_chg   double      null,
    pre_close double      null,
    vol       double      null,
    time      int         null,
    key time_idx (companyId,time),
    KEY `time_only_idx` (`time`)
);


-- fincode.stock_followed definition
drop table if exists `stock_followed`;
CREATE TABLE `stock_followed` (
                                  `id`          int     auto_increment NOT NULL,
                                  `user_id`     int     NULL,
                                  `stock_id`    int     NULL,
                                  `is_deleted`  tinyint(1)      default 0 ,
                                  gmt_created  datetime   default CURRENT_TIMESTAMP NULL,
                                  gmt_modified datetime   default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL,

                                  PRIMARY KEY (`id`)

#   KEY `fk_stock_id` (`stock_id`),
#   CONSTRAINT `fk_stock_id` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
#   CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

drop table if exists `strategy`;
create table strategy
(
    id           int
        primary key,
    name         varchar(255)                          null,
    type         varchar(5)                          null,
    is_deleted   tinyint(1) default 0                 null,
    gmt_created  datetime   default CURRENT_TIMESTAMP null,
    gmt_modified datetime   default CURRENT_TIMESTAMP null,
    func_enabled tinyint(1) default 0
);

# create table stock_tip_overall
# (
#     stock_id                int       not null,
#     strategy_id             int       not null,
#     industry_id             int       null,
#     day_span                int       null,
#     history_profit_rate     double    null,
#     history_match_rate      double    null,
#     industry_match_rate     double    null,
#     profit_index            double    null,
#     is_deleted     tinyint(1) default 0      null,
#     gmt_created  datetime   default CURRENT_TIMESTAMP null,
#     gmt_modified datetime   default CURRENT_TIMESTAMP null,
#     profit_rate             double     null,
#     match_rate              double     null,
#     all_count               int        null,
#     return_index            double     not null default 0,
#     primary key (stock_id, strategy_id)
# );

drop table if exists `stock_tip_overall`;
CREATE TABLE stock_tip_overall (
                                   `stock_id` int NOT NULL,
                                   `strategy_id` int NOT NULL,
                                   `profit_rate` double NOT NULL,
                                   `match_rate` double NOT NULL,
                                   `all_count` int NOT NULL,
                                   `return_index` double NOT NULL DEFAULT '0',
                                   PRIMARY KEY (`stock_id`,`strategy_id`),
                                   KEY `stock_tip_overall_stock_id_strategy_id_index` (`stock_id`,`strategy_id`)
);

# alter table stock_tip_overall add column industry_id int null after strategy_id;
# alter table stock_tip_overall add column day_span int null after industry_id;
# alter table stock_tip_overall add column  int null after strategy_id;
# alter table stock_tip_overall add column industry_id int null after strategy_id;
# alter table stock_tip_overall add column industry_id int null after strategy_id;
# alter table stock_tip_overall add column industry_id int null after strategy_id;
# alter table stock_tip_overall add column industry_id int null after strategy_id;
# alter table stock_tip_overall add column industry_id int null after strategy_id;
# alter table stock_tip_overall add column industry_id int null after strategy_id;

# create table stock_tip_daily
# (
#     id           bigint unsigned auto_increment
#         primary key,
#     stock_id     int  null ,
#     strategy_id  int  null ,
#     type         varchar(32) null,
#     trade_date   int null ,
#     high         double null,
#     low          double null,
#     open         double null,
#     close        double null,
#     next_day_open double null,
#     next_day_date int null,
#     is_deleted   tinyint(1) default 0 null,
#     is_hit        tinyint(1)  default 0 null,
#     gmt_created  datetime   default CURRENT_TIMESTAMP null,
#     gmt_modified datetime   default CURRENT_TIMESTAMP null,
#     ext_info     text
# );

drop table if exists `stock_tip_daily`;
CREATE TABLE `stock_tip_daily` (
                                   `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                                   `stock_id` int DEFAULT NULL,
                                   `strategy_id` int DEFAULT NULL,
                                   `type` varchar(32) DEFAULT NULL,
                                   `trade_date` int DEFAULT NULL,
                                   `high` double DEFAULT NULL,
                                   `low` double DEFAULT NULL,
                                   `open` double DEFAULT NULL,
                                   `close` double DEFAULT NULL,
                                   `next_day_open` double DEFAULT NULL,
                                   `next_day_date` int DEFAULT NULL,
                                   `is_deleted` tinyint(1) DEFAULT '0',
                                   `is_hit` tinyint(1) DEFAULT '0',
                                   `gmt_created` datetime DEFAULT CURRENT_TIMESTAMP,
                                   `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP,
                                   `ext_info` text,
                                   PRIMARY KEY (`id`),
                                   KEY `date_index` (`stock_id`,`strategy_id`,`trade_date`),
                                   KEY `date_type_index` (`stock_id`,`strategy_id`,`type`,`trade_date`),
                                   KEY `strategy_only_date_index` (`strategy_id`,`trade_date`)
);

drop table if exists `stock_tip_data`;
# create table stock_tip_data
# (
#     id           int auto_increment
#         primary key,
#     sell_tip_id  int null,
#     buy_tip_id   int null,
#     profit_rate  double null,
#     stock_id     int null,
#     strategy_id  int null,
#     buy_in_date  int null,
#     sell_out_date int null
# );

CREATE TABLE `stock_tip_data` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `sell_tip_id` int NOT NULL,
                                  `buy_tip_id` int NOT NULL,
                                  `profit_rate` double NOT NULL,
                                  `stock_id` int NOT NULL,
                                  `strategy_id` int NOT NULL,
                                  `buy_in_date` int NOT NULL,
                                  `sell_out_date` int NOT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `stock_tip_data_id_uindex` (`id`),
                                  KEY `stock_strategy_id_buy_in_date_index` (`stock_id`,`strategy_id`,`buy_in_date` DESC)
);

drop table if exists `stock_pool`;

create table `stock_pool` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `user_id` int NOT NULL,
                                  `pool_name` varchar(255) NOT NULL,
                                  `condition` text NOT NULL,
                                  `gmt_created` datetime DEFAULT CURRENT_TIMESTAMP,
                                  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`)
);

drop table if exists `pool_detail`;

create table `pool_detail` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `pool_id` bigint NOT NULL,
                                  `stock_id` int NOT NULL,
                                  # 加策略id
                                  `strategy_id` int NOT NULL,
                                  PRIMARY KEY (`id`)
);
