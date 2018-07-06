DEPRECATED
==========
This has moved into core functionality of BPMS 6.1.

For more information, please see:
https://access.redhat.com/products/red-hat-process-automation-manager/


jbpm-ee
=======
Enterprise Services for jBPM 6 - DEPRECATED since BPMS 6.1.

1) A "black box" approach to jBPM internals - The approach tries to black box jBPM, and provide 4 simple interfaces for integration: Process Services, Task Services, Work Item Services, and Rule Services.  These same interfaces are shared and exposed as: Remote EJB, Local EJB, Rest, and SOAP.
  * Integration Mode - Embedded: https://github.com/redhat-consulting/jbpm-ee/wiki/Embedded-Mode
  * Integration Mode - Standalone: https://github.com/redhat-consulting/jbpm-ee/wiki/Standalone-Modes


2) A simplified scale-out model - Because jBPM becomes a blackbox, and the interfaces to integrate with jBPM through the service layer are the same no matter if you call them remotely or locally, it allows jBPM to be used remotely near as easily as locally.  In large enterprise (such as consumer banking or logistics) you might have a huge array of machines to serve largely user experience, and then BPM is needed to only serve a small subset of functionality.  In the model we are delivering, it is easy to create a cluster of jBPM service machines, and then separate that from the larger application.  This allows the customers to more effectively scale the different tiers of their application while controlling cost.
  * Integration Mode - Standalone: https://github.com/redhat-consulting/jbpm-ee/wiki/Standalone-Modes


3) A simplified deployment model - Setting up jBPM requires two system properties to be provided.  One auto"magically" setups up the system for proper JTA, coordinated transactions against the database.  The other selects either LDAP or File based Role Resolution for users.  We also lock down the database to 2 phase commit databases, since no enterprise should be using H2 in production 
  * Database: https://github.com/redhat-consulting/jbpm-ee/wiki/Databases
  * User Groups: https://github.com/redhat-consulting/jbpm-ee/wiki/User-Group-Configuration


4) A simplified development experience - Integrating with either the Embedded or Standalone "blackbox" that is the jBPM services are even further simplified by providing Maven Archetypes to generate sample applications that leverage each model.  This simplifies getting started by setting up proper dependencies and providing samples of how to integrate immediately upon creating your project in Eclipse.  Additionally, the blackbox sets up transactions properly, persistence contexts properly, human task services properly, which makes leveraging the jBPM service layer consistent and predictable.  It is also pre-setup for clustering! (black box, or black magic?)
  * https://github.com/redhat-consulting/jbpm-ee/wiki/Transactions
  * https://github.com/redhat-consulting/jbpm-ee/wiki/Clustering


5) Separation of responsibility - We pre-generate the DDLs for Oracle, DB2, SQL Server, Postgres, and MySQL.  Because of points (1) and (2), we also simplify the enterprise setup.  This allows the DBA team to install the data layer, the operations team to install the jBPM service layer / application server, and the development team to focus on development.


