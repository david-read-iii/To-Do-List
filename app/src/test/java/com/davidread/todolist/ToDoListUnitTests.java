package com.davidread.todolist;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * {@link ToDoListUnitTests} has some example unit tests for this project. These will execute on the
 * development machine.
 */
public class ToDoListUnitTests {

    /**
     * Unit test that verifies that {@link ToDoList#addItem(String)} properly adds items to a
     * {@link ToDoList}.
     */
    @Test
    public void testAddItems() {

        ToDoList list = new ToDoList(null);

        String[] todos = {"mow lawn", "feed dog", "clean room"};
        for (String item : todos) {
            list.addItem(item);
        }

        String[] items = list.getItems();
        assertArrayEquals(todos, items);
    }

    /**
     * Unit test that verifies that {@link ToDoList#clear()} properly removes items from a
     * {@link ToDoList}.
     */
    @Test
    public void testClear() {

        ToDoList list = new ToDoList(null);

        list.addItem("test");
        list.clear();

        assertEquals(0, list.getItems().length);
    }
}