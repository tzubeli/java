package com.theladders.solid.lsp;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by atzubeli on 6/2/14.
 */
public interface EnvironmentInterface<K, V> {

    

    public Object get(Object key);

    public Object getForNullKey();

    public Set<Map.Entry<Object, Object>> entrySet();

    public Set<Object> keySet();

    public Collection<Object> values();

    public V put(K key, V value);

    //new methods
    public String getAdminEmail();

    public String getString(String key);





}
