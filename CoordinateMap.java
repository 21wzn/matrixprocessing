package matrixprocessing;

import java.util.HashMap;
import java.util.Map;

public class CoordinateMap<K1,K2,V> {

    private final Map<K1, Map<K2, V>> aMap;

    public CoordinateMap() {
        aMap = new HashMap<K1, Map<K2, V>>();
    }

    public V get(K1 first,K2 second){
        if(aMap.containsKey(first)) {
            Map<K2,V> inner = (HashMap<K2, V>) aMap.get(first);
            if(inner.containsKey(second)) {
                return inner.get(second);
            }
        }
        return null;
    }

    public V put(K1 first,K2 second, V value) {
        if(aMap.containsKey(first)) {
            aMap.get(first).put(second, value);
        }
        else {
            Map<K2,V> newMap = new HashMap<K2, V>();
            newMap.put(second, value);
            aMap.put(first, newMap);
        }
        return value;
    }

    public boolean containsKeys(K1 first, K2 second) {
        return aMap.containsKey(first) && aMap.get(first).containsKey(second);
    }

    public void clear() {
        aMap.clear();
    }
}
