package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongMapImplTest {

    private LongMap<String> map;

    @Before
    public void setUp() {
        map = new LongMapImpl<>(String.class);
    }

    @Test
    public void testSize() {
        assertEquals(0, map.size());

        map.put(1L, "One");
        map.put(2L, "Two");

        assertEquals(2, map.size());

        map.remove(1L);

        assertEquals(1, map.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(map.isEmpty());

        map.put(1L, "One");

        assertFalse(map.isEmpty());

        map.remove(1L);

        assertTrue(map.isEmpty());
    }

    @Test
    public void testContainsKey() {
        assertFalse(map.containsKey(1L));

        map.put(1L, "One");

        assertTrue(map.containsKey(1L));
        assertFalse(map.containsKey(2L));

        map.remove(1L);

        assertFalse(map.containsKey(1L));
    }

    @Test
    public void testContainsValue() {
        assertFalse(map.containsValue("One"));

        map.put(1L, "One");

        assertTrue(map.containsValue("One"));
        assertFalse(map.containsValue("Two"));

        map.remove(1L);

        assertFalse(map.containsValue("One"));
    }

    @Test
    public void testGet() {
        assertNull(map.get(1L));

        map.put(1L, "One");

        assertEquals("One", map.get(1L));

        map.remove(1L);

        assertNull(map.get(1L));
    }

    @Test
    public void testPut() {
        assertNull(map.put(1L, "One"));
        assertEquals("One", map.get(1L));

        // Update existing key
        assertEquals("One", map.put(1L, "New One"));
        assertEquals("New One", map.get(1L));

        // Add a new key
        assertNull(map.put(2L, "Two"));
        assertEquals("Two", map.get(2L));
    }

    @Test
    public void testRemove() {
        assertNull(map.remove(1L));

        map.put(1L, "One");
        assertEquals("One", map.remove(1L));

        assertNull(map.get(1L));
    }

    @Test
    public void testClear() {
        map.put(1L, "One");
        map.put(2L, "Two");

        assertFalse(map.isEmpty());

        map.clear();

        assertTrue(map.isEmpty());
    }

    @Test
    public void testKeys() {
        map.put(1L, "One");
        map.put(2L, "Two");

        long[] keys = map.keys();
        assertEquals(2, keys.length);
        assertTrue(keys[0] == 1L || keys[0] == 2L);
        assertTrue(keys[1] == 1L || keys[1] == 2L);
    }

    @Test
    public void testValues() {
        map.put(1L, "One");
        map.put(2L, "Two");

        String[] values = map.values();
        assertEquals(2, values.length);
        assertTrue(values[0].equals("One") || values[0].equals("Two"));
        assertTrue(values[1].equals("One") || values[1].equals("Two"));
    }
}