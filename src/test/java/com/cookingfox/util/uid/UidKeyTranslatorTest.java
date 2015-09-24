package com.cookingfox.util.uid;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for UidKeyTranslator class.
 */
public class UidKeyTranslatorTest {

    private static final Uid EXAMPLE_UID_A = Uid.create();
    private static final Uid EXAMPLE_UID_B = Uid.create();
    private static final Uid EXAMPLE_UID_C = Uid.create();
    private static final int EXAMPLE_INT_A = 1;
    private static final int EXAMPLE_INT_B = 2;
    private static final int EXAMPLE_INT_C = 3;

    private UidKeyTranslator<Integer> translator;

    //----------------------------------------------------------------------------------------------
    // TESTS SETUP / TEARDOWN
    //----------------------------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        translator = new UidKeyTranslator<Integer>();

        Map<Uid, Integer> exampleMap = new LinkedHashMap<Uid, Integer>();
        exampleMap.put(EXAMPLE_UID_A, EXAMPLE_INT_A);
        exampleMap.put(EXAMPLE_UID_B, EXAMPLE_INT_B);
        exampleMap.put(EXAMPLE_UID_C, EXAMPLE_INT_C);

        translator.addToDictionary(exampleMap);
    }

    @After
    public void tearDown() throws Exception {
        translator = null;
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: ADD TO DICTIONARY
    //----------------------------------------------------------------------------------------------

    @Test
    public void addToDictionary_should_accept_new_values() throws Exception {
        Uid uidD = Uid.create();
        Uid uidE = Uid.create();
        Uid uidF = Uid.create();

        int keyD = 4;
        int keyE = 5;
        int keyF = 6;

        Map<Uid, Integer> map = new LinkedHashMap<Uid, Integer>();
        map.put(uidD, keyD);
        map.put(uidE, keyE);
        map.put(uidF, keyF);

        translator.addToDictionary(map);
    }

    @Test(expected = UidKeyTranslatorException.class)
    public void addToDictionary_should_throw_if_duplicate_key() throws Exception {
        Map<Uid, Integer> map = new LinkedHashMap<Uid, Integer>();
        map.put(EXAMPLE_UID_A, 12345);

        translator.addToDictionary(map);
    }

    @Test
    public void addToDictionary_should_accept_duplicate_values() throws Exception {
        Uid uidD = Uid.create();
        Uid uidE = Uid.create();
        Uid uidF = Uid.create();

        Map<Uid, Integer> map = new LinkedHashMap<Uid, Integer>();
        map.put(uidD, EXAMPLE_INT_A);
        map.put(uidE, EXAMPLE_INT_B);
        map.put(uidF, EXAMPLE_INT_C);

        translator.addToDictionary(map);
    }

    @Test(expected = UidKeyTranslatorException.class)
    public void addToDictionary_should_throw_if_null_key() throws Exception {
        Map<Uid, Integer> map = new LinkedHashMap<Uid, Integer>();
        map.put(null, 12345);

        translator.addToDictionary(map);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: FROM UID
    //----------------------------------------------------------------------------------------------

    @Test
    public void fromUid_should_return_expected() throws Exception {
        int result = translator.fromUid(EXAMPLE_UID_A);

        assertEquals(EXAMPLE_INT_A, result);
    }

    @Test(expected = UidKeyTranslatorException.class)
    public void fromUid_should_throw_if_uid_not_in_dictionary() throws Exception {
        translator.fromUid(Uid.create());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: FROM UID MAP
    //----------------------------------------------------------------------------------------------

    @Test
    public void fromUidMap_should_return_expected() throws Exception {
        String value_a = "a";
        String value_b = "b";
        String value_c = "c";

        Map<Uid, Object> values = new LinkedHashMap<Uid, Object>();
        values.put(EXAMPLE_UID_A, value_a);
        values.put(EXAMPLE_UID_B, value_b);
        values.put(EXAMPLE_UID_C, value_c);

        Map<Integer, Object> expected = new LinkedHashMap<Integer, Object>();
        expected.put(EXAMPLE_INT_A, value_a);
        expected.put(EXAMPLE_INT_B, value_b);
        expected.put(EXAMPLE_INT_C, value_c);

        Map<Integer, Object> result = translator.fromUidMap(values);

        assertEquals(expected, result);
    }

    @Test(expected = UidKeyTranslatorException.class)
    public void fromUidMap_should_throw_if_uid_not_in_dictionary() throws Exception {
        Map<Uid, Object> values = new LinkedHashMap<Uid, Object>();
        values.put(EXAMPLE_UID_A, "a");
        values.put(Uid.create(), "b");
        values.put(EXAMPLE_UID_C, "c");

        translator.fromUidMap(values);
    }

    //----------------------------------------------------------------------------------------------
    // TEST: TO UID
    //----------------------------------------------------------------------------------------------

    @Test
    public void toUid_should_return_expected() throws Exception {
        Uid result = translator.toUid(EXAMPLE_INT_A);

        assertEquals(EXAMPLE_UID_A, result);
    }

    @Test(expected = UidKeyTranslatorException.class)
    public void toUid_should_throw_if_key_not_in_dictionary() throws Exception {
        translator.toUid(-1);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: TO UID MAP
    //----------------------------------------------------------------------------------------------

    @Test
    public void toUidMap_should_return_expected() throws Exception {
        String value_a = "a";
        String value_b = "b";
        String value_c = "c";

        Map<Integer, Object> values = new LinkedHashMap<Integer, Object>();
        values.put(EXAMPLE_INT_A, value_a);
        values.put(EXAMPLE_INT_B, value_b);
        values.put(EXAMPLE_INT_C, value_c);

        Map<Uid, Object> expected = new LinkedHashMap<Uid, Object>();
        expected.put(EXAMPLE_UID_A, value_a);
        expected.put(EXAMPLE_UID_B, value_b);
        expected.put(EXAMPLE_UID_C, value_c);

        Map<Uid, Object> result = translator.toUidMap(values);

        assertEquals(expected, result);
    }

    @Test(expected = UidKeyTranslatorException.class)
    public void toUidMap_should_throw_if_key_not_in_dictionary() throws Exception {
        Map<Integer, Object> values = new LinkedHashMap<Integer, Object>();
        values.put(EXAMPLE_INT_A, "a");
        values.put(-1, "b");
        values.put(EXAMPLE_INT_C, "c");

        translator.toUidMap(values);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: TO STRING
    //----------------------------------------------------------------------------------------------

    @Test
    public void toString_should_return_dictionary_string_representation() throws Exception {
        translator = new UidKeyTranslator<Integer>();

        Map<Uid, Integer> exampleMap = new LinkedHashMap<Uid, Integer>();
        exampleMap.put(EXAMPLE_UID_A, EXAMPLE_INT_A);
        exampleMap.put(EXAMPLE_UID_B, EXAMPLE_INT_B);
        exampleMap.put(EXAMPLE_UID_C, EXAMPLE_INT_C);

        translator.addToDictionary(exampleMap);

        String toString = translator.toString();

        assertEquals(exampleMap.toString(), toString);
    }

}
