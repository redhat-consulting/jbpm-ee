package org.jbpm.ee.persistence.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.jbpm.ee.persistence.provider.JbpmDatabasePropertyProvider.JbpmDatabaseProperties;

/***
 * Generates the DDL for the jBPM Database based on a provided database type.
 * @author bradsdavis
 *
 */
public class DDLGeneratorUtil {

	public static void main(String[] args) throws IOException {
		executeForAll(args[0]);
	}

	public static void executeForAll(String destination) throws IOException {
		for(JbpmDatabaseProperties property : JbpmDatabaseProperties.values()) {
			execute(property, destination);
		}
	}
	
	public static void execute(JbpmDatabaseProperties databaseType, String destination) throws IOException {
		File destinationPath = new File(destination);
		if(!destinationPath.exists()) {
			FileUtils.forceMkdir(destinationPath);
		}
		
		String sqlName = "jbpm-"+databaseType.name().toLowerCase()+".sql";
		File destinationSQLFile = new File(destinationPath, sqlName);
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", databaseType.getHibernateDialect());
		Ejb3Configuration hibernateConfiguration = new Ejb3Configuration();
		InputStream io = DDLGeneratorUtil.class.getResourceAsStream("/META-INF/persistence.xml");

		hibernateConfiguration.addInputStream(io);
		hibernateConfiguration.setProperties(properties);
		hibernateConfiguration.configure("org.jbpm.persistence.jpa", properties);
		
		SchemaExport schemaExport = new SchemaExport(hibernateConfiguration.getHibernateConfiguration());
		schemaExport.setOutputFile(destinationSQLFile.getAbsolutePath());
		schemaExport.setFormat(true);
		schemaExport.setDelimiter(";");
		
		schemaExport.execute(false, false, false, false);
		System.out.println("Schema exported to " + destinationSQLFile.getAbsolutePath());
	}
}