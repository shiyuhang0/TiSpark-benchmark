/**
 * 以导入 ORDERS 表数据为例（需要先建表）
 */
import org.apache.spark.sql.types._
val schema = new StructType().add("O_ORDERKEY",IntegerType).add("O_CUSTKEY",IntegerType).add("O_ORDERSTATUS",StringType).add("O_TOTALPRICE",DecimalType(15,2)).add("O_ORDERDATE",DateType).add("O_ORDERPRIORITY",StringType).add("O_CLERK",StringType).add("O_SHIPPRIORITY",IntegerType).add("O_COMMENT",StringType)
val df = spark.read.format("csv").option("delimiter","|").schema(schema).load("hdfs://172.16.201.185:9000/test1/orders.tbl")
val tidbOptions = Map(
  "tidb.addr" -> "172.16.201.117",
  "tidb.password" -> "",
  "tidb.port" -> "4000",
  "tidb.user" -> "root",
  "spark.tispark.pd.addresses" -> "172.16.201.117:2379"
)
val startTime = System.currentTimeMillis()
df.write.format("tidb").options(tidbOptions).option("database", "test").option("table", "ORDERS_T").option("writeThreadPerTask","2").mode("append").save()
val endTime = System.currentTimeMillis()
println("tispark write time ms: "+(endTime-startTime))