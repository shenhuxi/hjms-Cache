package com.pci.hjmos.cache.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class SpringUtils  implements ApplicationContextAware {
	public static <T> T getBean(String name) {
		return (T) ac.getBean(name);
	}
	private static ApplicationContext ac = null;

	public static ApplicationContext getAc() {
		return ac;
	}

	public static void setAc(ApplicationContext ac) {
		SpringUtils.ac = ac;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}
}
