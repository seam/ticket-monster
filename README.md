The TicketMonster example for Seam 3
====================================

JBoss AS 6
----------

To deploy the application to JBoss AS 6, run:

  mvn -U clean package jboss:hard-deploy

To undeploy, run

  mvn jboss:hard-undeploy
  

Eclipse
-------

You can easily import the example into Eclipse and take advantage of the CDI validations
and EL auto-complete. You need the latest releases of:

* Eclipse Helios
* JBoss Tools 3.2
* m2eclipse

With these installed, you can import ticket monster into Eclipse using 
_Import Projects -> Import Maven Project_ and the project will automatically set up the
classpath and have CDI and JSF support added.

You should enable the maven profile for the target server. For example, to work with JBoss EAP
5.1, go to _Properties -> Maven -> Active Profiles_ and enter "jboss-eap51"

You can easily deploy the application to a JBoss AS server in Eclipse.

* Set up the JBoss AS server
* Tell JBoss Tools to deploy the ticketmonster-ds.xml file (_Mark as Deployable_ and then (_Run As -> Run on Server_)
* Tell JBoss Tools to deploy the example (_Run As -> Run on Server_)
