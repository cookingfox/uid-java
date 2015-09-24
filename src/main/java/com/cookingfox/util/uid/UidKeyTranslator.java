package com.cookingfox.util.uid;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A class that can be used to manage a map of {@link Uid} to user-defined objects and 'translate'
 * from one to the other format. This is useful when an application uses unique ids that require
 * different data types in certain contexts. Use the {@link #addToDictionary(Map)} method to add
 * specifications for this format to the translator's dictionary.
 *
 * @param <T> Indicates the data type of the item (key) that the unique id is mapped to.
 */
public class UidKeyTranslator<T> {

    /**
     * A map of unique ids to a user-defined data type.
     */
    private final Map<Uid, T> dictionary = new LinkedHashMap<Uid, T>();

    /**
     * Adds a UID-key map to the dictionary. Throws if a key was previously added.
     */
    public void addToDictionary(Map<Uid, T> map) {
        if (map.containsKey(null)) {
            throw new UidKeyTranslatorException("The map cannot contain a `null` value for the Uid");
        } else if (containsDuplicateKeys(map, dictionary)) {
            throw new UidKeyTranslatorException("The map contains keys that are already present in the dictionary");
        }

        dictionary.putAll(map);
    }

    /**
     * Returns the key that is mapped to this unique id.
     */
    public T fromUid(Uid uid) {
        T key = dictionary.get(uid);

        if (null == key) {
            throw new UidKeyTranslatorException("Requested Uid is not in the dictionary");
        }

        return key;
    }

    /**
     * Accepts a map of unique ids to values and returns a new map which replaces the unique ids
     * with the keys from the dictionary.
     */
    public Map<T, Object> fromUidMap(Map<Uid, Object> map) {
        Map<T, Object> result = new LinkedHashMap<T, Object>();

        for (Map.Entry<Uid, Object> entry : map.entrySet()) {
            result.put(fromUid(entry.getKey()), entry.getValue());
        }

        return result;
    }

    /**
     * Returns the unique id that is mapped to this key.
     */
    public Uid toUid(T key) {
        for (Map.Entry<Uid, T> entry : dictionary.entrySet()) {
            if (key.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new UidKeyTranslatorException("Requested key is not in the dictionary");
    }

    /**
     * Accepts a map of keys to values and returns a new map which replaces the keys with the unique
     * ids from the dictionary.
     */
    public Map<Uid, Object> toUidMap(Map<T, Object> map) {
        Map<Uid, Object> result = new LinkedHashMap<Uid, Object>();

        for (Map.Entry<T, Object> entry : map.entrySet()) {
            result.put(toUid(entry.getKey()), entry.getValue());
        }

        return result;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    /**
     * Returns whether map `a` and `b` have items in common.
     *
     * @param a The target map to check against.
     * @param b The base map to check against.
     */
    private boolean containsDuplicateKeys(Map a, Map b) {
        return !Collections.disjoint(a.keySet(), b.keySet());
    }

}
