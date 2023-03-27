// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

suite("test_colocate_join") {
    sql """ DROP TABLE IF EXISTS `test_colo1` """
    sql """ DROP TABLE IF EXISTS `test_colo2` """
    sql """ DROP TABLE IF EXISTS `test_colo3` """
    sql """ DROP TABLE IF EXISTS `test_colo4` """
    sql """ DROP TABLE IF EXISTS `test_colo5` """

    sql """
        CREATE TABLE `test_colo1` (
        `id` varchar(64) NULL,
        `name` varchar(64) NULL,
        `age` int NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`id`,`name`)
        COMMENT 'OLAP'
        DISTRIBUTED BY HASH(`id`,`name`) BUCKETS 4
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "colocate_with" = "group",
        "in_memory" = "false",
        "storage_format" = "V2",
        "disable_auto_compaction" = "false"
        );
    """

    sql """
        CREATE TABLE `test_colo2` (
        `id` varchar(64) NULL,
        `name` varchar(64) NULL,
        `age` int NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`id`,`name`)
        COMMENT 'OLAP'
        DISTRIBUTED BY HASH(`id`,`name`) BUCKETS 4
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "colocate_with" = "group",
        "in_memory" = "false",
        "storage_format" = "V2",
        "disable_auto_compaction" = "false"
        );
    """

    sql """
        CREATE TABLE `test_colo3` (
        `id` varchar(64) NULL,
        `name` varchar(64) NULL,
        `age` int NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`id`,`name`)
        COMMENT 'OLAP'
        DISTRIBUTED BY HASH(`id`,`name`) BUCKETS 4
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "colocate_with" = "group",
        "in_memory" = "false",
        "storage_format" = "V2",
        "disable_auto_compaction" = "false"
        );
    """

    sql """
        CREATE TABLE `test_colo4` (
        `id` varchar(64) NULL,
        `name` varchar(64) NULL,
        `age` int NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`id`,`name`)
        COMMENT 'OLAP'
        DISTRIBUTED BY HASH(`id`,`name`) BUCKETS 4
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "colocate_with" = "group",
        "in_memory" = "false",
        "storage_format" = "V2",
        "disable_auto_compaction" = "false"
        );
    """

    sql """
        CREATE TABLE `test_colo5` (
        `id` varchar(64) NULL,
        `name` varchar(64) NULL,
        `age` int NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`id`,`name`)
        COMMENT 'OLAP'
        DISTRIBUTED BY HASH(`id`,`name`) BUCKETS 4
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "colocate_with" = "group",
        "in_memory" = "false",
        "storage_format" = "V2",
        "disable_auto_compaction" = "false"
        );
    """

    sql """insert into test_colo1 values('1','a',12);"""
    sql """insert into test_colo2 values('1','a',12);"""
    sql """insert into test_colo3 values('1','a',12);"""
    sql """insert into test_colo4 values('1','a',12);"""
    sql """insert into test_colo5 values('1','a',12);"""

    explain {
        sql("select * from test_colo1 a inner join test_colo2 b on a.id = b.id and a.name = b.name inner join test_colo3 c on a.id=c.id and a.name= c.name inner join test_colo4 d on a.id=d.id and a.name= d.name inner join test_colo5 e on a.id=e.id and a.name= e.name;")
        contains "8:VHASH JOIN\n  |  join op: INNER JOIN(COLOCATE[])[]"
        contains "6:VHASH JOIN\n  |  join op: INNER JOIN(COLOCATE[])[]"
        contains "4:VHASH JOIN\n  |  join op: INNER JOIN(COLOCATE[])[]"
        contains "2:VHASH JOIN\n  |  join op: INNER JOIN(COLOCATE[])[]"
    }

    /* test join same table but hit different rollup, should disable colocate join */
    sql """ DROP TABLE IF EXISTS `test_query_colocate`;"""

    sql """
            CREATE TABLE `test_query_colocate` (
              `datekey` int(11) NULL,
              `rollup_1_condition` int null,
              `rollup_2_condition` int null,
              `sum_col1` bigint(20) SUM NULL,
              `sum_col2` bigint(20) SUM NULL
            ) ENGINE=OLAP
            AGGREGATE KEY(`datekey`,`rollup_1_condition`,`rollup_2_condition`)
            COMMENT ""
            PARTITION BY RANGE(`datekey`)
            (PARTITION p20220102 VALUES [("20220101"), ("20220102")),
            PARTITION p20220103 VALUES [("20220102"), ("20220103")))
            DISTRIBUTED BY HASH(`datekey`) BUCKETS 1
            rollup (
            rollup_1(datekey, sum_col1),
            rollup_2(datekey, sum_col2)
            )
            PROPERTIES (
                "replication_allocation" = "tag.location.default: 1",
                "in_memory" = "false",
                "storage_format" = "V2"
            )
    """

    sql """insert into test_query_colocate values
            (20220101, 102, 200, 200, 100),
            (20220101, 101, 200, 200, 100),
            (20220101, 102, 202, 200, 100),
            (20220101, 101, 202, 200, 100);"""

    explain {
        sql("select /*+SET_VAR(parallel_fragment_exec_instance_num=1)*/ " +
                " sum_col1,sum_col2 " +
                "from " +
                "(select datekey,sum(sum_col1) as sum_col1  from test_query_colocate where datekey=20220101 group by datekey) t1 " +
                "left join " +
                "(select datekey,sum(sum_col2) as sum_col2 from test_query_colocate where datekey=20220101 group by datekey) t2 " +
                "on t1.datekey = t2.datekey")
        contains "Tables are not in the same group"
    }

    sql """ DROP TABLE IF EXISTS `test_query_colocate` """

    /* test no rollup is selected */
    sql """ DROP TABLE IF EXISTS `tbl1`;"""
    sql """ DROP TABLE IF EXISTS `tbl2`;"""

    sql """
           create table tbl1(k1 int, k2 varchar(32), v bigint sum) AGGREGATE KEY(k1,k2) distributed by hash(k1) buckets 1 properties('replication_num' = '1');
    """

    sql """
            create table tbl2(k3 int, k4 varchar(32)) DUPLICATE KEY(k3) distributed by hash(k3) buckets 1 properties('replication_num' = '1');
    """

    explain {
        sql("select * from tbl1 join tbl2 on tbl1.k1 = tbl2.k3")
        contains "INNER JOIN"
    }

    sql """ DROP TABLE IF EXISTS `tbl1`;"""
    sql """ DROP TABLE IF EXISTS `tbl2`;"""

}
