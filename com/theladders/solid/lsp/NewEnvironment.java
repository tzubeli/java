package com.theladders.solid.lsp;

import java.util.*;

/**
 * Created by atzubeli on 6/2/14.
 */
public class NewEnvironment<K, V> implements EnvironmentInterface {


    private Set<Map.Entry<K,V>> entrySet = null;

    private Collection<V> values = null;

    private Set<K>        keySet = null;

    private int modCount;

    static final Map.Entry<?,?>[] EMPTY_TABLE = {};

    private Map.Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;


    public V get(Object key) {

        Iterator<Map.Entry<K,V>> i = (Iterator<Map.Entry<K, V>>) entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Map.Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                Map.Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }

    public V getForNullKey() {

        return null;
    }

    public Set<Map.Entry<K,V>> entrySet() {

        return entrySet0();
    }

    private Set<Map.Entry<K,V>> entrySet0() {

        Set<Map.Entry<K,V>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
    }

    public Set<K> keySet() {

        if (keySet == null) {
            keySet = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<Map.Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

            };
        }
        return keySet;
    }

    public Collection<V> values() {

        if (values == null) {
            values = new AbstractCollection<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Map.Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }
            };
        }
        return values;
    }


    public Object put(Object key, Object value) {

        if (table == EMPTY_TABLE) {
            inflateTable(threshold);
        }
        if (key == null)
            return putForNullKey(value);
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }



    private V putForNullKey(V value) {

        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }

    public String getAdminEmail() {

        String user = getString("admin");
        String domain = getString("emaildomain");

        if (user.length() > 0 && domain.length() > 0)
            return user + "@" + domain;

        return "";

    }

    public String getString(String key) {

        Object val = get(key);

        if (val != null)
            return val.toString().trim();

        return "";

    }


}

