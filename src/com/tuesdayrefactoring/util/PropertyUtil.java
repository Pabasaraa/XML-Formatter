package com.tuesdayrefactoring.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.io.IOException;

public class PropertyUtil {

	public static final Logger log = Logger.getLogger(PropertyUtil.class.getName());

	public static final Properties properties = new Properties();

	static {
		try {
			properties.load(QueryUtil.class.getResourceAsStream("../config/config.properties"));
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}
}

