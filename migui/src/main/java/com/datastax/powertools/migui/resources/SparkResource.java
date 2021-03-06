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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String[] submitSparkJob(@QueryParam("fromHost") String fromHost,
                                               @QueryParam("toHost") String toHost,
                                               @QueryParam("fromUser") String fromUser,
                                               @QueryParam("toUser") String toUser,
                                               @QueryParam("fromPw") String fromPw,
                                               @QueryParam("toPw") String toPw,
                                               @QueryParam("fromTable") String fromTable,
                                               @QueryParam("toTable") String toTable,
                                               @QueryParam("fromKs") String fromKs,
                                               @QueryParam("toKs") String toKs
                                               ) {

        ResultSet result = null;
        String output = "";
        try {
            config.setFromHost(fromHost);
            config.setToHost(toHost);
            config.setFromUser(fromUser);
            config.setToUser(toUser);
            config.setFromPassword(fromPw);
            config.setToPassword(toPw);
            config.setFromTable(fromTable);
            config.setToTable(toTable);
            config.setFromKeyspace(fromKs);
            config.setToKeyspace(toKs);

            String jar = "dse-cluster-migration-0.1.jar";
            String jarUrl = "'dsefs:/"+ jar +"'";
            String mem = "1024";
            String cores = "1";
            String supervise = "false";
            String mainClass = "'org.apache.spark.deploy.worker.DseDriverWrapper'";
                    //phact.MigrateTable
            String args = String.format("['{{WORKER_URL}}', " +
                    "'{{USER_JAR}}', " +
                    "'phact.MigrateTable'" +
                    "]"
                    );
            String env = "{}";
            String cp = "[]";
            String libs = "[]";
            String jvmOpts = String.format("[" +
                    //Required args
                    "'-Dspark.master=dse://?',\n" +
                    "'-Dspark.cassandra.connection.factory=com.datastax.bdp.spark.DseCassandraConnectionFactory',\n" +
                    "'-Dspark.hadoop.dse.client.configuration.impl=com.datastax.bdp.transport.client.HadoopBasedClientConfiguration',\n" +
                    "'-Dspark.cassandra.sql.pushdown.additionalClasses=org.apache.spark.sql.cassandra.DsePredicateRules,org.apache.spark.sql.cassandra.SolrPredicateRules',\n" +
                    "'-Dspark.shuffle.service.enabled=true',\n" +
                    "'-Dspark.extraListeners=com.datastax.bdp.spark.reporting.DseSparkListener',\n" +
                    "'-Dspark.jars=dsefs:/%s',\n" +
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
                    "'-Dspark.cassandra.connection.host=127.0.0.1',\n" +
                    //Args for the migration job
                    "'-Dspark.dse.cluster.migration.fromClusterHost=%s',\n" +
                    "'-Dspark.dse.cluster.migration.toClusterHost=%s',\n" +
                    "'-Dspark.dse.cluster.migration.fromKeyspace=%s',\n" +
                    "'-Dspark.dse.cluster.migration.fromTable=%s',\n" +
                    "'-Dspark.dse.cluster.migration.toKeyspace=%s',\n" +
                    "'-Dspark.dse.cluster.migration.toTable=%s',\n" +
                    "'-Dspark.dse.cluster.migration.newtableflag=%s',\n" +
                    "'-Dspark.dse.cluster.migration.fromuser=%s',\n" +
                    "'-Dspark.dse.cluster.migration.frompassword=%s',\n" +
                    "'-Dspark.dse.cluster.migration.touser=%s',\n" +
                    "'-Dspark.dse.cluster.migration.topassword=%s'\n" +
                    "]",
                    jar,
                    config.getFromHost(),
                    config.getToHost(),
                    config.getFromKeyspace(),
                    config.getFromTable(),
                    config.getToKeyspace(),
                    config.getToTable(),
                    true,
                    config.getFromUser(),
                    config.getFromPassword(),
                    config.getToUser(),
                    config.getToPassword()
            );
            String props = "{}";
            String query = buildSparkResourceManagerCall(jarUrl, mem, cores, supervise, mainClass, args, env, cp, libs, jvmOpts, props);
            result = session.execute(query);

            String[] driverStatus = new String[2];
            Boolean success;
            for (Row row : result) {
                driverStatus[0] = row.getString(0);
                driverStatus[1] = row.getString(1);
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
            //curl -L -X PUT -T logfile.txt '127.0.0.1:5598/webhdfs/v1/fs/logfile.txt?op=CREATE&overwrite=true&blocksize=50000&rf=1'

            File jarFile = new File("../target/dse-cluster-migration-0.1.jar");

            HttpResponse<JsonNode> request = Unirest.put("http://" + config.getToHost() + ":5598/webhdfs/v1/dse-cluster-migration-0.1.jar?op=CREATE&overwrite=true")
                    .field("parameter", "value")
                    .field("file", jarFile)
                    .asJson();

            String result = request.getBody().toString();
            int statusCode = request.getStatus();
            if (statusCode == 307){
                String location = request.getHeaders().get("Location").get(0);
                request = Unirest.put(location)
                        .field("parameter", "value")
                        .field("file", jarFile)
                        .asJson();
                result = request.getBody().toString();

            }else {
                return null;
            }
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
