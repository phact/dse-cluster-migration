package com.datastax.powertools.migui.resources;

import com.datastax.powertools.migui.MigUIConfig;
import com.datastax.powertools.migui.managed.Dse;

/**
 * Created by sebastianestevez on 3/27/18.
 */
public class SparkResourceTest {
    @org.testng.annotations.Test
    public void testSubmitSparkJob() throws Exception {

        MigUIConfig conf = new MigUIConfig();
        Dse dse = new Dse(conf);
        dse.start();
        SparkResource resource = new SparkResource(dse, conf);

        String [] result = resource.submitSparkJob("","","","","", "", "", "", "","");
        assert(result !=null);
        System.out.println(result.toString());

        String driverId = result[0];
        String output = resource.sparkJobStatus(driverId);
        assert(output !=null);
        System.out.println(output);

        output = null;
        output = resource.sparkJobKill(driverId);
        assert(output !=null);
        System.out.println(output);

    }

    @org.testng.annotations.Test
    public void testDSEFS() throws Exception {
        MigUIConfig conf = new MigUIConfig();
        Dse dse = new Dse(conf);
        dse.start();
        SparkResource resource = new SparkResource(dse, conf);

        String result = resource.cpJarToDsefs();
        assert(result != null);
        System.out.println(result);

    }
}