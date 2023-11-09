package com.Models;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public void add(T item) {
        Node<T> newNode = new Node<T>(item);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
        size++;
    }

    public void remove(T item) {
        if (head == null) {
            throw new NoSuchElementException();
        }
        if (head.data.equals(item)) {
            head = head.next;
            size--;
            return;
        }
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.next.data.equals(item)) {
                currentNode.next = currentNode.next.next;
                size--;
                return;
            }
            currentNode = currentNode.next;
        }
        throw new NoSuchElementException();
    }

    public void update(T oldItem, T newItem) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.data.equals(oldItem)) {
                currentNode.data = newItem;
                return;
            }
            currentNode = currentNode.next;
        }
        throw new NoSuchElementException();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> currentNode;

        public LinkedListIterator() {
            currentNode = head;
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node<T> {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public void sort(Comparator<T> comparator) {
        head = mergeSort(head, comparator);
    }

    private Node<T> mergeSort(Node<T> head, Comparator<T> comparator) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<T> middle = getMiddle(head);
        Node<T> nextOfMiddle = middle.next;
        middle.next = null;

        Node<T> left = mergeSort(head, comparator);
        Node<T> right = mergeSort(nextOfMiddle, comparator);

        return merge(left, right, comparator);
    }

    private Node<T> merge(Node<T> left, Node<T> right, Comparator<T> comparator) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        Node<T> result;
        if (comparator.compare(left.data, right.data) <= 0) {
            result = left;
            result.next = merge(left.next, right, comparator);
        } else {
            result = right;
            result.next = merge(left, right.next, comparator);
        }

        return result;
    }

    private Node<T> getMiddle(Node<T> head) {
        if (head == null) {
            return null;
        }

        Node<T> slow = head;
        Node<T> fast = head.next;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }

        return slow;
    }

}
