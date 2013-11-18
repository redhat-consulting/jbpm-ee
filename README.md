jbpm-ee
=======
Enterprise Services for jBPM 6.

Note too that you need to update the arquillian.xml to point to a JBoss EAP 6.1 server, and need to setup the server to include a MySQL driver.  You also need MySQL installed in order to run the tests.  Create the schema: jbpm-main.  Add the user: jbpm with password: jbpm.

* Copy the following files to your deployment directory:
     /jbpm-ee-services/resources/jbpm-ee-ds.xml
     /jbpm-ee-services/resources/jbpm-ee-jms.xml


