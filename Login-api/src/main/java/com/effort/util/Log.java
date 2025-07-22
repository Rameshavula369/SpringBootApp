package com.effort.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Version;
import org.springframework.stereotype.Component;

@Component
public class Log {

	public static void info(Class<?> class1, String log){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		//Logger logger = LogManager.getLogger("HelloWorld");
		//Logger logger = LogManager.getFormatterLogger("Foo");
		logger.info(log);
		//logger.warn(log);
		//logger.error(log);
		
	}
	
	public static void info(Class<?> class1, String log, boolean logEnable, String empId){
		
		if(logEnable)
		{
			//Logger logger = Logger.getLogger(class1);
			//Logger logger = Logger.getLogger(class1);
			Logger logger = LogManager.getLogger(class1);
			if(!Api.isEmptyString(empId))
			{
				logger.info(new StringBuilder("EmpId : ").append(empId).append(". ").append(log).toString());
			}
			else
			{
				logger.info(log);
			}
			
		}
	}
	
	public static void info(Class<?> class1, String log, Throwable throwable){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.info(log, throwable);
	}
	
	public static void debug(Class<?> class1, String log){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.debug(log);
	}
	
	public static void debug(Class<?> class1, String log, Throwable throwable){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.debug(log, throwable);
	}
	
	public static void ignore(Class<?> class1, Throwable throwable){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.debug("Caught but ignore", throwable);
	}
	
	public static void error(Class<?> class1, String log, Throwable throwable){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.error(log, throwable);
	}
	
	public static void error(Class<?> class1, String log){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.error(log);
	}
	
	public static void ignore(Class<?> class1, String log){
		//Logger logger = Logger.getLogger(class1);
		Logger logger = LogManager.getLogger(class1);
		logger.debug("Caught but ignore: " + log);
	}
	
	public static void audit(String companyId, String empId, String key, String action, String newObj, String oldObj){
		//Logger logger = Logger.getLogger("effort-audit");
		Logger logger = LogManager.getLogger("effort-audit");
		logger.info(companyId + " " + empId + " " + key + " " + action + " " + newObj + " " + oldObj);
	}
	
	
}
