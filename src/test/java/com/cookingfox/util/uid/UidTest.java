package com.cookingfox.util.uid;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link Uid}.
 */
public class UidTest {

    @Test
    public void create_should_return_unique_instance() throws Exception {
        Uid a = Uid.create();
        Uid b = Uid.create();

        assertFalse(a.equals(b));
    }

    @Test
    public void create_should_use_id_for_toString() throws Exception {
        String toString = Uid.create().toString();

        assertTrue(toString.matches("Uid\\{\\d+\\}"));
    }

    @Test
    public void create_should_accept_name_and_use_for_toString() throws Exception {
        String toString = Uid.create("myUid").toString();

        assertEquals("Uid{'myUid'}", toString);
    }

    @Test
    @SuppressWarnings("all")
    public void equals_should_use_id() throws Exception {
        Uid a = Uid.create();
        Uid b = Uid.create();
        Uid c = a;

        assertFalse(a.equals(null));
        assertFalse(a.equals(123));
        assertFalse(a.equals(UUID.randomUUID()));
        assertFalse(a.equals(b));
        assertTrue(a.equals(a));
        assertTrue(a.equals(c));
    }

}