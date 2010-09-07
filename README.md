The TicketMonster example for Seam 3
====================================

JBoss AS 6
----------

To deploy the application to JBoss AS 6, run:

    mvn -U clean package jboss:hard-deploy -Pjboss-6

To undeploy, run

    mvn jboss:hard-undeploy
  
You can then [access the application](http://localhost:8080/ticket-monster/)


JBoss EAP 5.1
-------------

To deploy the application to JBoss EAP 5.1, run:

    mvn -U clean package jboss:hard-deploy -Peap-51

To undeploy, run

    mvn jboss:hard-undeploy
  
You can then [access the application](http://localhost:8080/ticket-monster/)

  
EC2
---

You'll need to create yourself an AWS account, and [sign up for EC2](see http://aws.amazon.com).
Having done this, it's best to register an Elastic IP (so that you can always get the same
IP address when creating an instance - however an Elastic IP will cost you more). You also need
to create a security group that allows access to JBoss AS on port 8080, and you'll also want ssh
access. Call this group "jboss-as-single". Next, create a key-pair to allow ssh access, and 
download this to your home directory.

To make it easier to find the machine on the net, it's nice to create a `ticketmonster` alias 
in your `hosts` file.

It also helps to set up an SSH alias. For example:

    Host ticketmonster
      IdentityFile # /path/to/your/key-pair.pem
      User jboss
      CheckHostIP no
      StrictHostKeyChecking no`
   
You can then easily ssh to the instance 

    ssh ticketmonster

If this doesn't work to start with, try again in a couple of minutes.

**Don't forget to terminate the instance when you are done with the demo - you can do this through
the EC2 control panel**


JBoss EAP 5.1 on EC2
--------------------

You can easily start an EC2 instance, pre-configured with EAP 5.1. You need to configure some
(user-specific) settings. Add these profiles to your `/.m2/settings.xml`:

      <profile>
         <id>ticketmonster-aws</id>
         <activation>
            <property>
               <name>ticketmonster-aws</name>
               <value>!false</value>
            </property>
         </activation>
         <properties>
            <!-- The Elastic IP you want associated with the instance -->
            <ticketmonster.elasticIp></ticketmonster.elasticIp>
            <!-- The name of the key that the instance should use -->
            <ticketmonster.keyName></ticketmonster.keyName>
         </properties>
      </profile>
      <profile>
         <id>aws</id>
         <activation>
            <property>
               <name>aws</name>
               <value>!false</value>
            </property>
         </activation>
         <properties>
            <!-- Your AWS Access Key ID -->
            <aws.accessKeyId>AKIAIVECQR7NIHPUTLXA</aws.accessKeyId>
            <!-- Your AWS Secret Access Key-->
            <aws.secretAccessKey>wAMNIWYStJnCOQ5CrRHTAbnSAlcDFKrwrIqUy/aP</aws.secretAccessKey>
         </properties>
      </profile>

  
You can then start the EC2 instance using:

    mvn ec2:start
  
The plugin will wait for the instance to start. JBoss AS is installed as a service, so will start
automatically.

The easiest way to transfer the app to your EC2 instance is to use `scp`. First, we need to generate
a zip to copy across:

    mvn clean package -Pdist,eap-51

Then, we need transfer this to the instance:

    scp target/ticket-monster-war.zip ticketmonster:
  
Next, we need to unzip this and move it into the JBoss AS deploy directory. Having ssh'd to the 
machine:

    unzip ticket-monster-war.zip -d /tmp/ticketmonster 
     mv /tmp/ticketmonster /opt/jboss-eap-5.1.0.Beta/jboss-as/server/default/deploy/
  
And then, check to see whether the app has started:

    tail -f /opt/jboss-eap-5.1.0.Beta/jboss-as/server/default/logs/server.log
  
The `deploy-on-ec2.sh` script, in the project directory, will package up the zip, transfer it to
the EC2 instance, unzip, and start the JBoss instance for you.

You can then [access the application](http://ticket:8080/ticket-monster/)

Note, you can safely kill the script with Crtl-C, this will leave the JBoss server running.

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
to work with work with JBoss EAP 5.1 enter "eap-51", or for JBoss AS 6 enter "jboss-6".

Unfortunately, m2eclipse doesn't support alternative locations for `web.xml` or additional web
resources, so we have to tell eclipse about this ourselves. Go to  _Properties -> Module Assembly_
and then hit _Add Folder..._. If you are using JBoss EAP 5.1, you need to add `src/eap-51/webapp`
or for JBoss AS 6, you need to add `src/jboss-6/webapp`.

You can easily deploy the application to a JBoss AS server in Eclipse.

* Set up the JBoss AS server
* Tell JBoss Tools to deploy the `ticketmonster-ds.xml` file (_Mark as Deployable_ and then 
  (_Run As -> Run on Server_)
* Tell JBoss Tools to deploy the example (_Run As -> Run on Server_)

Note that with EAP 5.1 you may encounter JBPAPP-4490 - publishing to the server when it is stopped seems
to work around this.
