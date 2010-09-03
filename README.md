The TicketMonster example for Seam 3
====================================

JBoss AS 6
----------

To deploy the application to JBoss AS 6, run:

  mvn -U clean package jboss:hard-deploy -Pjboss-6

To undeploy, run

  mvn jboss:hard-undeploy
  
  
JBoss EAP 5.1
-------------

To deploy the application to JBoss EAP 5.1, run:

  mvn -U clean package jboss:hard-deploy -Pjboss-6

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

You should enable the maven profile for the target server. Go to _Properties -> Maven -> Active Profiles_;
to work with work with JBoss EAP 5.1 enter "jboss-eap51", or for JBoss AS 6 enter "jboss-6".

Unfortunately, m2eclipse doesn't support alternative locations for web.xml or additional web
resources, so we have to tell eclipse about this ourselves. Go to  _Properties -> Module Assembly_
and then hit _Add Folder..._. If you are using JBoss EAP 5.1, you need to add "src/main/webapp-jboss-eap51"
or for JBoss AS 6, you need to add "src/main/webapp-jboss-6".

You can easily deploy the application to a JBoss AS server in Eclipse.

* Set up the JBoss AS server
* Tell JBoss Tools to deploy the ticketmonster-ds.xml file (_Mark as Deployable_ and then 
  (_Run As -> Run on Server_)
* Tell JBoss Tools to deploy the example (_Run As -> Run on Server_)

_Currently we are experiencing some problems deploying the app on eap51 from Eclipse, so please use
the command line!_