#!/bin/sh

REMOTE_HOST=ticketmonster
PROJECT_HOME=`dirname $0`

mvn clean package -Pdist,eap-51 -f $PROJECT_HOME/pom.xml
scp $PROJECT_HOME/target/ticket-monster-war.zip $REMOTE_HOST:

ssh $REMOTE_HOST "unzip ticket-monster-war.zip -d /tmp/ticketmonster && mv /opt/ticketmonster /opt/jboss-eap-5.1.0.Beta/jboss-as/server/default/deploy/ && tail -f /opt/jboss-eap-5.1.0.Beta/jboss-as/server/default/log/server.log"
