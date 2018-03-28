package com.datastax.powertools.migui;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sebastianestevez on 3/26/18.
 */
public class MigUIConfig extends Configuration{

    public String getFromHost() {
        return fromHost;
    }

    public void setFromHost(String fromHost) {
        this.fromHost = fromHost;
    }

    public Integer getFromPort() {
        return fromPort;
    }

    public void setFromPort(Integer fromPort) {
        this.fromPort = fromPort;
    }

    public String getToHost() {
        return toHost;
    }

    public void setToHost(String toHost) {
        this.toHost = toHost;
    }

    public Integer getToPort() {
        return toPort;
    }

    public void setToPort(Integer toPort) {
        this.toPort = toPort;
    }

    public String getFromTable() {
        return fromTable;
    }

    public void setFromTable(String fromTable) {
        this.fromTable = fromTable;
    }

    public String getFromKeyspace() {
        return fromKeyspace;
    }

    public void setFromKeyspace(String fromKeyspace) {
        this.fromKeyspace = fromKeyspace;
    }

    public String getToTable() {
        return toTable;
    }

    public void setToTable(String toTable) {
        this.toTable = toTable;
    }

    public String getToKeyspace() {
        return toKeyspace;
    }

    public void setToKeyspace(String toKeyspace) {
        this.toKeyspace = toKeyspace;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    @NotEmpty
    @JsonProperty
    private String fromHost = "localhost";

    @Min(1)
    @Max(65535)
    @JsonProperty
    private Integer fromPort = 9042;

    @NotEmpty
    @JsonProperty
    private String toHost = "localhost";

    @Min(1)
    @Max(65535)
    @JsonProperty
    private Integer toPort= 9042;


    @JsonProperty
    private String fromTable = "";

    @JsonProperty
    private String fromKeyspace= "";

    @JsonProperty
    private String toTable = "";

    @JsonProperty
    private String toKeyspace = "";

    @JsonProperty
    private String toUser = "";


    @JsonProperty
    private String toPassword = "";

    @JsonProperty
    private String fromPassword = "";

    @JsonProperty
    private String fromUser = "";


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToPassword() {
        return toPassword;
    }

    public void setToPassword(String toPassword) {
        this.toPassword = toPassword;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromPassword() {
        return fromPassword;
    }

    public void setFromPassword(String fromPassword) {
        this.fromPassword = fromPassword;
    }


    @NotEmpty
    @JsonProperty
    private String fromType = "DSE";

}
