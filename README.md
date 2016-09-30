##dse-cluster-migrator

This spark job will move a table from one datastax cluster to another

###Build:

    sbt package

###RUN:
To run in interactive mode:

    dse spark-submit --class phact.MigrateTable target/scala-2.10/dse-cluster-migration_2.10-0.1.jar

    dse spark-submit --class phact.MigrateTable --conf spark.dse.cluster.migration.fromClusterHost='127.0.0.1' --conf spark.dse.cluster.migration.toClusterHost='192.168.1.3' --conf spark.dse.cluster.migration.keyspace='test' --conf spark.dse.cluster.migration.table='test' target/scala-2.10/dse-cluster-migration_2.10-0.1.jar
