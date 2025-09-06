package org.example.shortlink.admin.test;

public class UserTableShardingTest {
    /**
     * 用户表 SQL 语句
     * 输出生成分表的 SQL 语句
     */
    public static final String SQL = "CREATE TABLE `t_user_%d` (\n" +
            "`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "`username` varchar(256) DEFAULT NULL COMMENT '用户名',\n" +
            "`password` varchar(512) DEFAULT NULL COMMENT '密码',\n" +
            "`real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',\n" +
            "`phone` varchar(128) DEFAULT NULL COMMENT '手机号',\n" +
            "`mail` varchar(256) DEFAULT NULL COMMENT '邮箱',\n" +
            "`deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',\n" +
            "`create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
            "`update_time` datetime DEFAULT NULL COMMENT '更新时间',\n" +
            "`del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标志（0未删除，1已删除）',\n" +
            "PRIMARY KEY (`id`),\n" +
            "UNIQUE KEY `idx_unique_username` (`username`) USING BTREE\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8mb4;";
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }

    }
}
