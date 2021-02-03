package qworks.dataserver.util;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

/**
 * Implementation of a LRU cache
 * 
 * @param <K>
 * @param <V>
 *
* @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 */
public class LRUCache<K, V> extends AbstractMap<K, V>{
 
	private static final Logger LOG = LoggerFactory.getLogger(LRUCache.class);
	
	private static final int MAX_ITEMS = 1000;
	private static final int CACHE_TIMER_MILLIS = 60*1000;
	
	private long cacheTimerMillis;
	private long ttlMillis;
	private int maxItems = MAX_ITEMS;
    private Map<K,TtlObject> lruMap;

    
    /**
     * Wrapper class for cache objects
     */
    protected class TtlObject {
        private long lastAccess = System.currentTimeMillis();
        private V object;
 
        protected TtlObject(V value) {
            this.object = value;
        }
        
        /**
         * Retrieve the timed object and update the last access time
         * @return
         */
        protected V get() {
        	lastAccess = System.currentTimeMillis();
        	LOG.trace("New access to object {}:  {}", object);
        	return object;
        }
        
        /**
         * Retrieve the timed object without updating the last access time
         * @return
         */
        protected V peek() {
        	return object;
        }
    }
 
    
    /**
     * LRU Cache with no defined ttl for items
     * @param maxItems Constrained maixmum
     */
    public LRUCache(int maxItems) {
    	this(-1, maxItems);
    }
        
    
    /**
     * @param ttl Time To Live (seconds) of the objects in the cache
     * @param maxItems
     */
    public LRUCache(long ttl,  int maxItems) {
    	this(ttl, maxItems, CACHE_TIMER_MILLIS);
    }
    
    /**
     * @param ttl Time To Live (seconds) of the objects in the cache
     * @param maxItems
     * @param cacheTimerMillis 
     */
    public LRUCache(long ttl,  int maxItems, long cacheTimerMillis) {
    	
    	this.maxItems = maxItems;
    	this.ttlMillis = ttl * 1000;
    	this.cacheTimerMillis = cacheTimerMillis;
    	this.lruMap = new HashMap<K,TtlObject>(maxItems);
 
        if (ttl > 0 && cacheTimerMillis > 0) {
 
            Thread t = new Thread(new Runnable() {
                @Override
				public void run() {
                    while (true) {
                        try {
                        	// TODO: Wait on monitor for lazy cleanup
                        	// this.wait(cacheTimerMillis);
                            Thread.sleep(cacheTimerMillis);
                        } catch (InterruptedException ex) {}
                        cleanup();
                        
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }
 
 
    /**
	 * @return the cacheTimerMillis
	 */
	public long getCacheTimerMillis() {
		return cacheTimerMillis;
	}


	/**
	 * @param cacheTimerMillis the cacheTimerMillis to set
	 */
	public void setCacheTimerMillis(long cacheTimerMillis) {
		this.cacheTimerMillis = cacheTimerMillis;
	}


	/**
     *
     */
    @Override
	public V put(K key, V value) {
    	
    	if (lruMap.size() == maxItems) {
    		cleanOldest();
    	}
    	
        synchronized (lruMap) {
        	LOG.trace("Caching object {}", key);
            lruMap.put(key, new TtlObject(value));
        }
        return value;
    }
 
    /**
     * @param key
     * @return
     */
    @Override
	public V get(Object key) {
        synchronized (lruMap) {
            TtlObject c = lruMap.get(key);
            if (c == null)
                return null;
            else {
                return c.get();
            }
        }
    }
 
    /**
     * @param key
     */
    @Override
	public V remove(Object key) {
    	TtlObject ret = null;
        synchronized (lruMap) {
           ret = lruMap.remove(key);
        }
        return ret.get();
    }
 
    /**
     * @return
     * @see java.util.AbstractMap#size()
     */
    @Override
	public int size() {
        synchronized (lruMap) {
            return lruMap.size();
        }
    }
 

	/**
	 * @return
	 * @see java.util.AbstractMap#entrySet()
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> ret = new LinkedHashSet<Entry<K, V>>();
		lruMap.entrySet().forEach(e -> ret.add(
				new AbstractMap.SimpleImmutableEntry<K, V>(e.getKey(), e.getValue().peek()))
			);
		return ret;
	}
	
	
	private void cleanup() {
		
        long now = System.currentTimeMillis();
        
 
        synchronized (lruMap) {
        	ArrayList<K> deleteKey = new ArrayList<K>((lruMap.size() / 2) + 1);
        	for (Entry<K,TtlObject> v: lruMap.entrySet()) {
        		K key = v.getKey();
        		TtlObject c = v.getValue();
        		if (c != null && (now > (ttlMillis + c.lastAccess))) {
                	LOG.trace("Key to remove: {}", key);
                    deleteKey.add(key);
                }
        	}
        	
        	for (K toDel:deleteKey) {
        		lruMap.remove(toDel);
        	}
        
            Thread.yield();
        }
 
        
    }
	
	private void cleanOldest() {
		 
		K deleteKey = null;
        long oldestAccess = Long.MAX_VALUE;
        
        synchronized (lruMap) {
        	for (Entry<K,TtlObject> v: lruMap.entrySet()) {
        		K key = v.getKey();
        		TtlObject c = v.getValue();
        		if (c != null && (c.lastAccess < oldestAccess)) {
                	LOG.trace("Key to remove: ", key);
                    deleteKey = (key);
                }
        	}
        	
        	 if (deleteKey != null) {
        		 lruMap.remove(deleteKey);
        	 }
        }
 
    }


}