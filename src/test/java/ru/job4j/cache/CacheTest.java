package ru.job4j.cache;

import org.junit.Test;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddIsCorrect() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        assertTrue(cache.add(base));
        assertEquals(base, cache.getMemory().get(1));
    }

    @Test
    public void whenDeleteIsCorrect() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertNull(cache.getMemory().get(1));
    }

    @Test
    public void whenUpdateIsCorrect() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        base.setName("New name");
        assertTrue(cache.update(base));
        assertEquals(2, cache.getMemory().get(1).getVersion());
    }
}