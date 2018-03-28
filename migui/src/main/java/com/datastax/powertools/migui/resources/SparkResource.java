package com.datastax.powertools.migui.resources;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.dse.DseSession;
import com.datastax.powertools.migui.MigUIConfig;
import com.datastax.powertools.migui.managed.Dse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.io.File;
import javafx.util.Pair;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sebastianestevez on 3/26/18.
 */

@Path("/v0/migui")
public class SparkResource {

    private final Dse dse;
    private final DseSession session;
    private final MigUIConfig config;


    public SparkResource(Dse dse, MigUIConfig config) {
        this.dse = dse;
        this.config = config;
        this.session = dse.getSession();
    }

    private String buildSparkResourceManagerCall(String jarUrl, String mem, String cores, String supervise,
                                                 String mainClass, String args, String env, String cp,
                                                 String libs, String jvmOpts, String props){
        String query = String.format("call DseResourceManager.submitDriver(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                jarUrl,
                mem,
                cores,
                supervise,
                mainClass,
                args,
                env,
                cp,
                libs,
                jvmOpts,
                props);
        return query;
    }

    private String buildSparkJobStatusCall(String driverId){
        String query = String.format("call DseResourceManager.requestDriverStatus('%s');", driverId);
        return query;
    }

    private String buildSparkJobKillCall(String driverId) {
        String query = String.format("call DseResourceManager.killDriver('%s');", driverId);
        return query;
    }


    @GET
    @Timed
    @Path("/submitSparkJob")
    @Produces(MediaType.APPLICATION_JSON)
    public Pair<String, String> submitSparkJob() {

        ResultSet result = null;
        String output = "";
        try {
            //TODO: update jar name
            String jar = "dse-cluster-migration-0.1.jar";
            String jarUrl = "'dsefs:/"+ jar +"'";
            String mem = "1024";
            String cores = "1";
            String supervise = "false";
            String mainClass = "'org.apache.spark.deploy.worker.DseDriverWrapper'";
                    //phact.MigrateTable
            String args = String.format("['{{WORKER_URL}}', " +
                    "'{{USER_JAR}}', " +
                    "'phact.MigrateTable', " +
                    "'" +
                    "--conf spark.dse.cluster.migration.fromClusterHost=%s " +
                    "--conf spark.dse.cluster.migration.toClusterHost=%s " +
                    "--conf spark.dse.cluster.migration.keyspace=%s " +
                    "--conf spark.dse.cluster.migration.table=%s " +
                    "--conf spark.dse.cluster.migration.newtableflag=%s " +
                    "--conf spark.dse.cluster.migration.fromuser=%s " +
                    "--conf spark.dse.cluster.migration.frompassword=%s " +
                    "--conf spark.dse.cluster.migration.touser=%s " +
                    "--conf spark.dse.cluster.migration.topassword=%s" +
                    "'" +
                    "]",
                    config.getFromHost(),
                    config.getToHost(),
                    config.getFromKeyspace(),
                    config.getFromTable(),
                    true,
                    config.getFromUser(),
                    config.getFromPassword(),
                    config.getToUser(),
                    config.getToPassword()
                    );
            String env = "{}";
            String cp = "[]";
            String libs = "[]";
            String jvmOpts = "[" +
                    "'-Dspark.master=dse://?',\n" +
                    "'-Dspark.cassandra.connection.factory=com.datastax.bdp.spark.DseCassandraConnectionFactory',\n" +
                    "'-Dspark.hadoop.dse.client.configuration.impl=com.datastax.bdp.transport.client.HadoopBasedClientConfiguration',\n" +
                    "'-Dspark.cassandra.sql.pushdown.additionalClasses=org.apache.spark.sql.cassandra.DsePredicateRules,org.apache.spark.sql.cassandra.SolrPredicateRules',\n" +
                    "'-Dspark.shuffle.service.enabled=true',\n" +
                    "'-Dspark.extraListeners=com.datastax.bdp.spark.reporting.DseSparkListener',\n" +
                    "'-Dspark.jars=dsefs:/test.jar',\n" +
                    "'-Dspark.app.name=com.datastax.dse.demo.loss.Spark10DayLoss',\n" +
                    "'-Dspark.rpc.askTimeout=10',\n" +
                    "'-Dspark.cassandra.auth.conf.factory=com.datastax.bdp.spark.DseAuthConfFactory',\n" +
                    "'-Dspark.SparkHadoopUtil=org.apache.hadoop.security.DseSparkHadoopUtil',\n" +
                    "'-Dspark.cassandra.dev.customFromDriver=com.datastax.spark.connector.types.DseTypeConverter',\n" +
                    "'-Dspark.submit.deployMode=cluster',\n" +
                    "'-Dspark.shuffle.service.port=7437',\n" +
                    "'-Dspark.hadoop.cassandra.config.loader=com.datastax.bdp.config.DseConfigurationLoader',\n" +
                    "'-Dspark.authenticate.enableSaslEncryption=false',\n" +
                    "'-Dspark.ui.confidentialKeys=token,password',\n" +
                    "'-Dspark.sql.hive.metastore.sharedPrefixes=com.typesafe.scalalogging,com.datastax.driver,com.datastax.bdp.hadoop.hive.metastore.CassandraClientManager,com.datastax.bdp.hadoop.hive.metastore.CassandraClientConfiguration',\n" +
                    "'-Dspark.sql.catalogImplementation=hive',\n" +
                    "'-Dspark.cassandra.connection.host=127.0.0.1'\n" +
                    "]";
            String props = "{}";
            String query = buildSparkResourceManagerCall(jarUrl, mem, cores, supervise, mainClass, args, env, cp, libs, jvmOpts, props);
            result = session.execute(query);

            Pair<String, String> driverStatus = null;
            Boolean success;
            for (Row row : result) {
                driverStatus = new Pair(row.getString(0), row.getString(1));
                if (!row.getBool(2)){
                    throw new Exception("Submission failed");
                };
            }

            return driverStatus;

        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Timed
    @Path("/sparkJobStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public String sparkJobStatus(String driverId) {

        ResultSet result = null;
        String output = "";
        try {
            String query = buildSparkJobStatusCall(driverId);
            result = session.execute(query);

            for (Row row : result) {
                output += row.toString();
            }

            return output;

        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Timed
    @Path("/sparkJobKill")
    @Produces(MediaType.APPLICATION_JSON)
    public String sparkJobKill(String driverId) {

        ResultSet result = null;
        String output = "";
        try {
            String query = buildSparkJobKillCall(driverId);
            result = session.execute(query);

            for (Row row : result) {
                output += row.toString();
            }

            return output;

        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }
    }


    @GET
    @Timed
    @Path("/cpJarToDsefs")
    @Produces(MediaType.APPLICATION_JSON)
    public String cpJarToDsefs() {

        try {
            //curl -L -X PUT -T logfile.txt '127.0.0.1:5598/webhdfs/v1/fs/log?op=CREATE&overwrite=true&blocksize=50000&rf=1'

            HttpResponse<JsonNode> request = Unirest.put("http://"+config.getToHost()+":5598/webhdfs/v1/fs/log?op=CREATE&overwrite=true")
                    .field("parameter", "value")
                    .field("file", new File("../target/dse-cluster-migration-0.1.jar"))
                    .asJson();

            String result = request.getBody().toString();

            return result;
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }

    }


    @GET
    @Timed
    @Path("/dsefsjars")
    @Produces(MediaType.APPLICATION_JSON)
    public String listDsefsJars() {

        try {
            //curl -L -X PUT -T logfile.txt '127.0.0.1:5598/webhdfs/v1/fs/log?op=CREATE&overwrite=true&blocksize=50000&rf=1'

            HttpResponse<JsonNode> request = Unirest.put(config.getToHost()+":5598/webhdfs/v1/fs/log?op=CREATE&overwrite=true")
                    .field("parameter", "value")
                    .field("file", new File("../target/dse-cluster-migration-0.1.jar"))
                    .asJson();

            String result = request.toString();

            return result;
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }

    }

}
