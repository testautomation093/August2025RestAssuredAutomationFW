package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkExceptions.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream fp;

	public Properties initProperties() {
		prop = new Properties();

		// mvn clean install -Denv="uat";

		String envName = System.getProperty("env");

		System.out.println("Environment Name is : " + envName);

		try {

			if (envName == null) {
				System.out.println("Since the envName is not provided running the test suite on default env.");

				fp = new FileInputStream("src/test/resources/config/config.properties");
			} 
			else {
				switch (envName.trim().toLowerCase()) {
				case "dev":

					System.out.println("Started running the framework on " + envName);
					fp = new FileInputStream("src/test/resources/config/config_dev.properties");

					break;

				case "uat":
					System.out.println("Started running the framework on " + envName);

					fp = new FileInputStream("src/test/resources/config/config_uat.properties");

					break;

				case "stage":
					System.out.println("Started running the framework on " + envName);

					fp = new FileInputStream("src/test/resources/config/config_stage.properties");

					break;

				default:
				
					throw new APIFrameworkException("===Invalid Environemt Passed===");
	
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			prop.load(fp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

}
