package com.datastax.powertools.migui.resources;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.dse.DseSession;
import com.datastax.powertools.migui.managed.Dse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sebastianestevez on 3/26/18.
 */

@Path("/api/v0/migui")
public class SparkResource {

    private final Dse dse;
    private final DseSession session;


    public SparkResource(Dse dse) {
        this.dse = dse;
        this.session = dse.getSession();
    }

    private String buildSparkResourceManagerCall(String jarUrl, String mem, String cores, String supervise,
                                                 String mainClass, String args, String env, String cp,
                                                 String libs, String jvmOpts, String props){
        return String.format("call DseResourceManager.submitDriver(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
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
    }


    @GET
    @Timed
    @Path("/submitSparkJob")
    @Produces(MediaType.APPLICATION_JSON)
    public String submitSparkJob() {

        try {

        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;

    }

    @GET
    @Timed
    @Path("/cpJarToDsefs")
    @Produces(MediaType.APPLICATION_JSON)
    public String cpJarToDsefs() {

        try {

        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;

    }


    @GET
    @Timed
    @Path("/dsefsjars")
    @Produces(MediaType.APPLICATION_JSON)
    public String listDsefsJars() {

        try {

        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;

    }

}
