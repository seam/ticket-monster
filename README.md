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

* * *
Currently you can't deploy the project using _Run -> Run On Server_, we hope to get
this working soon
* * *
