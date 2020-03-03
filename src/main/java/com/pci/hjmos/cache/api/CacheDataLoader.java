package com.pci.hjmos.cache.api;

public interface CacheDataLoader<T> {

	public T loadData(String key);
}
