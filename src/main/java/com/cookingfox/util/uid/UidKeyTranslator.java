package com.cookingfox.util.uid;

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

    //----------------------------------------------------------------------------------------------
    // PROPERTIES
    //----------------------------------------------------------------------------------------------

    /**
     * A map of unique ids to a user-defined data type.
     */
    protected final Map<Uid, T> dictionary = new LinkedHashMap<>();

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public UidKeyTranslator() {
    }

    /**
     * Creates a new instance, calling {@link #addToDictionary(Map)} for the provided map.
     *
     * @param map The values to add to the dictionary.
     * @throws UidKeyTranslatorException when the map contains invalid keys or values.
     */
    public UidKeyTranslator(Map<Uid, T> map) throws UidKeyTranslatorException {
        addToDictionary(map);
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Adds a UID-key map to the dictionary.
     *
     * @param map The values to add to the dictionary.
     * @throws UidKeyTranslatorException when the map contains invalid keys or values.
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public void addToDictionary(Map<Uid, T> map) throws UidKeyTranslatorException {
        for (Map.Entry entry : map.entrySet()) {
            final Object uid = entry.getKey();
            final Object value = entry.getValue();

            // validate Uid in map
            if (!Uid.class.isInstance(uid)) {
                String type = (uid == null ? null : uid.getClass().getSimpleName());

                throw new UidKeyTranslatorException(String.format("The map can only contain keys " +
                        "that are Uid instances (%s)", type));
            } else if (dictionary.containsKey(uid)) {
                throw new UidKeyTranslatorException("The following Uid is already present in the " +
                        "dictionary: " + uid);
            }

            // validate value in map
            if (value == null) {
                throw new UidKeyTranslatorException(String.format("The map cannot contain `null` " +
                        "values (error for %s)", uid));
            } else if (dictionary.containsValue(value)) {
                throw new UidKeyTranslatorException("The following 'key' (dictionary value) is " +
                        "already present in the dictionary: " + value);
            }
        }

        dictionary.putAll(map);
    }

    /**
     * Returns the key that is mapped to this unique id.
     *
     * @param uid The uid to translate.
     * @return The key that is mapped to the provided uid.
     * @throws UidKeyTranslatorException when the provided uid is not in the dictionary.
     */
    public T fromUid(Uid uid) throws UidKeyTranslatorException {
        T key = dictionary.get(uid);

        if (null == key) {
            throw new UidKeyTranslatorException("Provided Uid is not in the dictionary: " + uid);
        }

        return key;
    }

    /**
     * Accepts a map of unique ids to values and returns a new map which replaces the unique ids
     * with the keys from the dictionary.
     *
     * @param map Map of Uid -> value.
     * @return Map of Key -> value.
     * @throws UidKeyTranslatorException when a provided uid is not in the dictionary.
     */
    public Map<T, ?> fromUidMap(Map<Uid, ?> map) throws UidKeyTranslatorException {
        Map<T, Object> result = new LinkedHashMap<>();

        for (Map.Entry<Uid, ?> entry : map.entrySet()) {
            result.put(fromUid(entry.getKey()), entry.getValue());
        }

        return result;
    }

    /**
     * Returns the unique id that is mapped to this key.
     *
     * @param key The key to translate to Uid.
     * @return The uid that is mapped to the provided key.
     * @throws UidKeyTranslatorException when the provided key is not in the dictionary.
     */
    public Uid toUid(T key) throws UidKeyTranslatorException {
        for (Map.Entry<Uid, T> entry : dictionary.entrySet()) {
            if (key.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new UidKeyTranslatorException("Provided key is not in the dictionary: " + key);
    }

    /**
     * Accepts a map of keys to values and returns a new map which replaces the keys with the unique
     * ids from the dictionary.
     *
     * @param map Map of Key -> value.
     * @return Map of Uid -> value.
     * @throws UidKeyTranslatorException when the provided key is not in the dictionary.
     */
    public Map<Uid, ?> toUidMap(Map<T, Object> map) throws UidKeyTranslatorException {
        Map<Uid, Object> result = new LinkedHashMap<>();

        for (Map.Entry<T, ?> entry : map.entrySet()) {
            result.put(toUid(entry.getKey()), entry.getValue());
        }

        return result;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

}
