---
weight: 30
title: Migration UI
menu:
  main:
      parent: Migration UI
      identifier: migui
      weight: 301
---

The Migration UI is a Java web application built to interact with DSE using Java and Dropwizard on the backend and react / redux in the front end.

![migui pic](/migui/migui.png)

# Script

The following description is a guide on how to use the Migration UI demo and explain it to a user or an architect.

## What is Migration UI

Migration UI is a sample application that harnesses the power of DSE Analytics to migrate massive amounts of data in a short period. Migration UI can migrate data accross two tables in the same cluster, or it can migrate data accross separate clusters.

## Relevant uses


This functionality can be useful when users need to migrate data accross environments (SIT, UAT, PROD, etc.), for performing one time or recurring data migrations accross separate clusters that support different applications, in complex upgrade scenarios where migrating data to a new / different cluster makes more sense than upgrading the existing cluster, for cases where data needs to be migrated from one table to another on the same cluster, and others.

## How to Demo

### Point and click migration

Migration UI aims to simplify a cross cluster migration to the simple click of a button, just log into the web application available on port 8080 (once the startup scipts have been run), and click migrate. By default the startup scripts will create a table called `test` in a keyspace called `test` and load some data with `ebdse`.
Migration UI can move that data to a new table `test2` just by clicking migrate on the UI.

Once you click migrate on the UI, a simple

    select * from test.test2;

in cqlsh should reveal the migrated[ing] data.

### Tweaking the demo

See the shell script `.startup/runebdse` to see how the data for the demo is populated. Changing the `cycles` variable will increase or decrase the amount of data in the demo.

### What's under the hood?

Alternatively, we can use Migration UI to learn how to write Spark Analytics Jobs and expose via a web application UI.
To see the source code for both the spark job and the Migration UI itself, check out the next section on `Che` to learn how to access and use the IDE.

The code for the `dse-cluster-migration` spark job can be found in the `./src` directory. In short, the job establishes two spark contexts (one for each cluster) and uses the RDD operation `saveToCassandraTable` (or `saveAsCassandraTable` in the case where the destination table does not exist) to move the data.
Establishing simultaneous spark contexts require some implicit scala magic described in more detail in [Russ's post](http://www.russellspitzer.com/2016/01/14/Multiple-Clusters-Spark-Cassandra/).


