package phact 

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.{CassandraConnector, TableDef}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.cassandra._

// For DSE it is not necessary to set connection parameters for spark.master (since it will be done
// automatically)
object MigrateTable extends App {

  val spark = SparkSession.builder
    .appName("dse-cluster-migration")
    .enableHiveSupport()
    .getOrCreate()

  var conf = spark.sparkContext.getConf


  var clusterHostOne        = conf.get("spark.dse.cluster.migration.fromClusterHost", null)
  var clusterHostTwo        = conf.get("spark.dse.cluster.migration.toClusterHost", null)
  var fromKeyspace          = conf.get("spark.dse.cluster.migration.fromKeyspace", null)
  var fromTable             = conf.get("spark.dse.cluster.migration.fromTable", null)
  var toKeyspace            = conf.get("spark.dse.cluster.migration.toKeyspace", fromKeyspace)
  var toTable               = conf.get("spark.dse.cluster.migration.toTable", fromTable)
  var newTableFlag          = conf.get("spark.dse.cluster.migration.newtableflag", "false").toBoolean
  var fromuser              = conf.get("spark.dse.cluster.migration.fromuser", null)
  var frompassword          = conf.get("spark.dse.cluster.migration.frompassword", null)
  var touser                = conf.get("spark.dse.cluster.migration.touser", null)
  var topassword            = conf.get("spark.dse.cluster.migration.topassword", null)

  var connectorToClusterOne : CassandraConnector = _
  var connectorToClusterTwo : CassandraConnector  = _

  import spark.implicits._

  if (fromuser != null && frompassword != null) {
    connectorToClusterOne = CassandraConnector(spark.sparkContext.getConf.set("spark.cassandra.connection.host", clusterHostOne).set("spark.cassandra.auth.username", fromuser).set("spark.cassandra.auth.password", frompassword))
  }else{
    connectorToClusterOne = CassandraConnector(spark.sparkContext.getConf.set("spark.cassandra.connection.host", clusterHostOne))
  }
  if (touser != null && topassword != null) {
    connectorToClusterTwo = CassandraConnector(spark.sparkContext.getConf.set("spark.cassandra.connection.host", clusterHostTwo).set("spark.cassandra.auth.username", touser).set("spark.cassandra.auth.password", topassword))
  }else{
    connectorToClusterTwo = CassandraConnector(spark.sparkContext.getConf.set("spark.cassandra.connection.host", clusterHostTwo))
  }


  val rddFromClusterOne = {
    // Sets connectorToClusterOne as default connection for everything in this code block
    implicit val c = connectorToClusterOne
    spark.sparkContext.cassandraTable(fromKeyspace,fromTable)
  }

  {
    //Sets connectorToClusterTwo as the default connection for everything in this code block
    implicit val c = connectorToClusterTwo
    if (newTableFlag) {
      var tableDef = TableDef(toKeyspace,toTable, rddFromClusterOne.tableDef.partitionKey, rddFromClusterOne.tableDef.clusteringColumns, rddFromClusterOne.tableDef.regularColumns, rddFromClusterOne.tableDef.indexes, false)
      //rddFromClusterOne.saveAsCassandraTable(keyspace, table)
      //rddFromClusterOne.saveAsCassandraTable(keyspace, table, SomeColumns(rddFromClusterOne.selectedColumnRefs.mkString(",")))
      rddFromClusterOne.saveAsCassandraTableEx(tableDef)
    } else {
      rddFromClusterOne.saveToCassandra(toKeyspace, toTable)
    }
  }

  spark.stop()
  sys.exit(0)
}
