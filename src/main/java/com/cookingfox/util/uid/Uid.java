package com.cookingfox.util.uid;

/**
 * A simple Unique ID class, using an incrementing integer.
 */
public final class Uid {

    /**
     * Keeps track of created UIDs to make sure we get a unique id.
     */
    private static int idCounter = 0;

    /**
     * Creates a new unique ID.
     */
    public static Uid create() {
        return create(null);
    }

    /**
     * Creates a new unique ID, with a name for easier identification. Used in {@link #toString()}.
     *
     * @param name Uid name, which is recommended to be unique, although not required.
     */
    public static Uid create(String name) {
        return new Uid(++idCounter, name);
    }

    /**
     * The unique id value of this instance.
     */
    private final int id;

    /**
     * The name of the Uid, for easier identification.
     */
    private final String name;

    /**
     * Private constructor, used by static `create()` method.
     */
    private Uid(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns whether the provided object is equal to this instance.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Uid && id == ((Uid) o).id;
    }

    /**
     * Returns a unique hash code for this instance.
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Returns a String representation of this instance.
     */
    @Override
    public String toString() {
        if (name != null) {
            return "Uid{'" + name + "'}";
        }

        return "Uid{" + id + '}';
    }

}
