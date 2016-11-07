##dse-cluster-migrator

This spark job will move a table from one datastax cluster to another

###Build:

    sbt package

###RUN:
To run in interactive mode:

    dse spark-submit --class phact.MigrateTable --conf spark.dse.cluster.migration.fromClusterHost='<from host>' --conf spark.dse.cluster.migration.toClusterHost='<to host>' --conf spark.dse.cluster.migration.keyspace='<keyspace>' --conf spark.dse.cluster.migration.table='<table>' target/scala-2.10/dse-cluster-migration_2.10-0.1.jar
    
###Download:
You can also pull the pre-built binary and run it without building yourself:

    wget https://github.com/phact/dse-cluster-migration/releases/download/v0.01/dse-cluster-migration_2.10-0.1.jar
    
    dse spark-submit --class phact.MigrateTable --conf spark.dse.cluster.migration.fromClusterHost='<from host>' --conf spark.dse.cluster.migration.toClusterHost='<to host>' --conf spark.dse.cluster.migration.keyspace='<keyspace>' --conf spark.dse.cluster.migration.table='<table>' ./dse-cluster-migration_2.10-0.1.jar
