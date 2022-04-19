# 使用 TiSpark
1. 下载 tispark 符合版本的 jar 包
2. 配置 spark.defaults.conf
```
spark.master                      spark://{master}:7077
# spark.eventLog.enabled           true
# spark.eventLog.dir               hdfs://namenode:8021/directory
# spark.serializer                 org.apache.spark.serializer.KryoSerializer
# spark.driver.memory              5g
# spark.executor.extraJavaOptions  -XX:+PrintGCDetails -Dkey=value -Dnumbers="one two three"

spark.sql.extensions  org.apache.spark.sql.TiExtensions
spark.tispark.pd.addresses  {pd_address}
spark.sql.catalog.tidb_catalog org.apache.spark.sql.catalyst.catalog.TiCatalog
spark.sql.catalog.tidb_catalog.pd.addresses {pd_address}
# spark.executor.cores 1
# spark.executor.memory 1g
# spark.ui.port 3000
```
3. 启动 Spark-shell: bin/spark-shell --jars ${whereitis}/${tispark-jar}.jar