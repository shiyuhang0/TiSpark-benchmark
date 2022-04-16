/**
 * 以导入 ORDERS 表数据为例（需要先建表）
 * 使用：在 spark-shell 中 :load /{where}/jdbc_write.scala
 */

import org.apache.spark.sql.types._
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions
val schema = new StructType().add("O_ORDERKEY",IntegerType).add("O_CUSTKEY",IntegerType).add("O_ORDERSTATUS",StringType).add("O_TOTALPRICE",DecimalType(15,2)).add("O_ORDERDATE",DateType).add("O_ORDERPRIORITY",StringType).add("O_CLERK",StringType).add("O_SHIPPRIORITY",IntegerType).add("O_COMMENT",StringType)
val df = spark.read.format("csv").option("delimiter","|").schema(schema).load("hdfs://172.16.201.185:9000/test1/orders.tbl")
val startTime = System.currentTimeMillis()
df.write.format("jdbc").option("driver", "com.mysql.jdbc.Driver").option("url", "jdbc:mysql://172.16.201.117:4000/test?rewriteBatchedStatements=true").option("useSSL", "false").option("dbtable", "ORDERS_J").option("isolationLevel", "NONE").option("user", "root").mode(saveMode = "append").save()
val endTime = System.currentTimeMillis()
println("spark jdbc write time ms: "+(endTime-startTime))
