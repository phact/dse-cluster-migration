package phact 

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql._
import org.apache.spark.{SparkConf, SparkContext}

// For DSE it is not necessary to set connection parameters for spark.master (since it will be done
// automatically)
object MigrateTable extends App {

  val conf = new SparkConf()
      .setAppName("dse-cluster-migration")

  // A SparkContext
  val sc = new SparkContext(conf)

  var clusterHostOne        = conf.get("spark.dse.cluster.migration.fromClusterHost", null)
  var clusterHostTwo        = conf.get("spark.dse.cluster.migration.toClusterHost", null)
  var keyspace              = conf.get("spark.dse.cluster.migration.keyspace", null)
  var table                 = conf.get("spark.dse.cluster.migration.table", null)
  var newTableFlag          = conf.get("spark.dse.cluster.migration.newtableflag", "false").toBoolean

  val connectorToClusterOne = CassandraConnector(sc.getConf.set("spark.cassandra.connection.host", clusterHostOne))
  val connectorToClusterTwo = CassandraConnector(sc.getConf.set("spark.cassandra.connection.host", clusterHostTwo))

  val rddFromClusterOne = {
    // Sets connectorToClusterOne as default connection for everything in this code block
    implicit val c = connectorToClusterOne
    sc.cassandraTable(keyspace,table)
  }

  {
    //Sets connectorToClusterTwo as the default connection for everything in this code block
    implicit val c = connectorToClusterTwo
    if (newTableFlag) {
      rddFromClusterOne.saveAsCassandraTable(keyspace, table)
    } else {
      rddFromClusterOne.saveToCassandra(keyspace, table)
    }
  }
}
