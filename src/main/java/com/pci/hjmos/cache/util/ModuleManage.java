package com.pci.hjmos.cache.util;

import java.util.*;

public abstract class ModuleManage {

	public static <T> T  getInstance(Class<T> cls) {
		T t = null;
		
		Collection<T> list = SpringUtils.getAc().getBeansOfType(cls).values();
		if (list != null) {
			List<T> tmpList = new ArrayList<>();
			tmpList.addAll(list);
			Collections.sort(tmpList, new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					org.springframework.core.annotation.Order order1 = o1.getClass().getAnnotation(org.springframework.core.annotation.Order.class);
					org.springframework.core.annotation.Order order2 = o2.getClass().getAnnotation(org.springframework.core.annotation.Order.class);
					int a = 0;
					if (order1 != null) {
						a = order1.value();
					}
					int b = 0;
					if (order2 != null) {
						b = order2.value();
					}
					
					if (a > b) {
						return -1;
					}
					else if (a < b) {
						return 1;
					}
					
					return 0;
				}
				
			});
			
			t = tmpList.get(0);
		}
		else {
			throw new RuntimeException("");
		}
		
		return t;
	}
}
