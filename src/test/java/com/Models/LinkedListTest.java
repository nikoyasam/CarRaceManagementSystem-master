package com.Models;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
class LinkedListTest {
    @Test
    public void testAdd() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(1), list.iterator().next());
    }

    @Test
    public void testRemove() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(2);
        assertEquals(2, list.size());
        Iterator<Integer> iterator = list.iterator();
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
    }

    @Test
    public void testUpdate() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.update(2, 4);
        assertEquals(3, list.size());
        Iterator<Integer> iterator = list.iterator();
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(4), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
    }

    @Test
    public void testSort() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(5);
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(4);
        list.sort(Comparator.naturalOrder());
        assertEquals(5, list.size());
        Iterator<Integer> iterator = list.iterator();
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(4), iterator.next());
        assertEquals(Integer.valueOf(5), iterator.next());
    }
}