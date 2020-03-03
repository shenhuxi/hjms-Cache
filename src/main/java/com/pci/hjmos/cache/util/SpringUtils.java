package com.pci.hjmos.cache.util;

import org.springframework.context.ApplicationContext;

public class SpringUtils {

	private static ApplicationContext ac = null;

	public static ApplicationContext getAc() {
		return ac;
	}

	public static void setAc(ApplicationContext ac) {
		SpringUtils.ac = ac;
	}
	
	
	
}
