本文档介绍如何用 TPC-H 生成数据。

# Steps
1. 下载 TPC-H 或直接使用本仓库的 TPC-H_Tools_v3.0.0

2. cd 到 dbgen 目录下

3. 配置 makefile.suite

```
CC      = gcc
DATABASE= ORACLE
MACHINE = LINUX
WORKLOAD = TPCH
```

4. `make` 编译代码
5. 使用 `./dbgen` 命令生成数据，主要有以下 option
```
Basic Options
===========================
-C <n> -- separate data set into <n> chunks (requires -S, default: 1)
-f     -- force. Overwrite existing files
-h     -- display this message
-q     -- enable QUIET mode
-s <n> -- set Scale Factor (SF) to  <n> (default: 1)
-S <n> -- build the <n>th step of the data/update set (used with -C or -U)
-U <n> -- generate <n> update sets
-v     -- enable VERBOSE mode

Advanced Options
===========================
-b <s> -- load distributions for <s> (default: dists.dss)
-d <n> -- split deletes between <n> files (requires -U)
-i <n> -- split inserts between <n> files (requires -U)
-T c   -- generate cutomers ONLY
-T l   -- generate nation/region ONLY
-T L   -- generate lineitem ONLY
-T n   -- generate nation ONLY
-T o   -- generate orders/lineitem ONLY
-T O   -- generate orders ONLY
-T p   -- generate parts/partsupp ONLY
-T P   -- generate parts ONLY
-T r   -- generate region ONLY
-T s   -- generate suppliers ONLY
-T S   -- generate partsupp ONLY
```

# Example
生成约 1G 的数据
```
./dbgen -s 1
```
生成 10G 数据中的 ORDERS 表
```
./dbgen -s 1 -T O
```



