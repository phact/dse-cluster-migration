## Migration UI

NOTE: If you are looking to perform a migration across two clusters, make sure
you deploy two clusters!

This is a Demo for Migrating DSE and cassandra clusters (or even tables within the same cluster) using DSE Analytics / Spark.

### Motivation

Moving data across clusters for one time migrations or bulk migrations are relatively common. DSE Analytics Makes this process almost trivial for users that are well versed in Spark. In a [previous blog post](http://www.sestevez.com/cluster-migration-keeping-simple-things-simple/) I attempted to make this process simpler for users with minimal spark experience. This asset aims to make this process easy even for users that with no spark experience at all.

### What is included?

This field asset (demo) includes the following:

* dse-cluster-migration Spark Job
 * Performs migration using massively parallel Spark compute
 * Code is visible in the Che web IDE which ships with this asset
* MigUI (Migration UI) web appication
 * React Redux frontend
 * Dropwizard backend
 * Uses webhdfs to programatically upload the spark job jar to DSEFS
 * Uses the DSE only CQL Spark interface (currently an internal undocumented API) to submit the spark job to DSE

### Business Take Aways

DSE is the best distribution of Apache Cassandra and the easiest to use. By taking advantage of the migration capabilities in DSE analytics, projects can get off the ground faster and complex business requirements have a shorter time to Value.

### Technical Take Aways

In some DSE to DSE or c* to DSE scenarios, there are a few cases in which a cluster migration is easier to perform than an upgrade. When the source cluster needs to remain place (i.e. data migrations accross environments, DEV <-> SIT <-> UAT <-> PROD) DSE Analytics can be the right solution.

Look at this asset if you are interested in:
Using Spark to migrate data from one cluster to another
Using Spark to move a table to another table in the same cluster
Programatically writing to DSEFS from Java
Programatically using CQL to kick off DSE Analytics jobs (NOTE this is unsupported and undocumented at this time)
