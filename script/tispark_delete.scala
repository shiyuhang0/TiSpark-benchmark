/**
 * 以删除 ORDERS 表数据为例（需要先导入数据）
 * 使用：在 spark-shell 中 :load /{where}/tispark_delete.scala
 */
val startTime = System.currentTimeMillis()
spark.sql("delete from tidb_catalog.test.ORDERS_J2 where O_ORDERKEY > 0")
val endTime = System.currentTimeMillis()
println("tidb write time ms: "+(endTime-startTime))