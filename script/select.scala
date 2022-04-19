/**
 * 执行 TPC-H query, SQL 见 query 文件
 * 使用：在 sark-shell 中 :load /{where}/select.scala (注意：需要指定 query.scala 的位置)
 * - TiSpark 测试：调用 test.tiSpark()
 * - Spark JDBC 测试：调用 test.sparkJDBC()
 */

import org.apache.spark.sql.DataFrame

:load {where}/query.scala
object test {

  def doAction(df:DataFrame):Unit = {
    df.rdd.foreach(e=>e)
  }

  def execute(queryType:String): Unit ={
    val query =  Query.getQuery
    var number = 0
    var totalTime = 0L
    query.foreach(q=>{
      number+=1
      val start_ts = System.currentTimeMillis()
      doAction(spark.sql(q))
      val end_ts = System.currentTimeMillis()
      totalTime = totalTime + end_ts - start_ts
      println(s"$queryType$number:"+(end_ts - start_ts))
    })
    println(s"$queryType totalTime:"+ totalTime)
  }

  def tiSpark(dbName:String = "test")={
    spark.sql(s"use tidb_catalog.$dbName")
    execute("TiSpark Query")
  }


  def sparkJDBC(dbName:String = "test")={
    val tpchTables = Seq(
      "lineitem",
      "orders",
      "customer",
      "nation",
      "customer",
      "partsupp",
      "part",
      "region",
      "supplier")
    tpchTables.foreach( name=>
      spark.read
        .format("jdbc")
        .option("url", "jdbc:mysql://${ip:port}/test")
        .option("useSSL", "false")
        .option("dbtable", s"`$dbName`.`$name`")
        .option("driver", "com.mysql.jdbc.Driver")
        .option("user", "root")
        .load()
        .createOrReplaceTempView(s"`$name`")
    )
    execute("Spark JDBC Query")
    tpchTables.foreach( name=>
      spark.catalog.dropTempView(name)
    )
  }
}