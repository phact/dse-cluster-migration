package com.datastax.powertools.migui;

import com.datastax.powertools.migui.managed.Dse;
import com.datastax.powertools.migui.resources.SparkResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by sebastianestevez on 3/26/18.
 */
public class MigUIApplication extends Application<MigUIConfig> {

    public static void main(String[] args) throws Exception{
        new MigUIApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MigUIConfig> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/assets/","/", "index.html"));

        super.initialize(bootstrap);
    }

    public void run(MigUIConfig migUIConfig, Environment environment) throws Exception {

        Dse dse = new Dse(migUIConfig);
        dse.start();


        SparkResource sparkResource= new SparkResource(dse, migUIConfig);
        environment.jersey().register(sparkResource);
    }
}
