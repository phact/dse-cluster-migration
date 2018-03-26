package com.datastax.powertools.migui.managed;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.powertools.migui.MigUIConfig;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by sebastianestevez on 3/26/18.
 */

public class Dse implements Managed {
    private DseSession session;

    public DseCluster getCluster() {
        return cluster;
    }

    public DseSession getSession() {
        return session;
    }

    private DseCluster cluster;
    final private String host;
    final private int port;

    public Dse(MigUIConfig conf){
        this.port = conf.getToPort();
        this.host = conf.getToHost();
    }
    public void start() throws Exception {
        cluster = DseCluster.builder().
                addContactPoints(host).
                withPort(port).
                //withCredentials("cassandra", "cassandra").
                build();
        session = cluster.connect();
    }

    public void stop() throws Exception {
        session.close();
        cluster.close();
    }
}
