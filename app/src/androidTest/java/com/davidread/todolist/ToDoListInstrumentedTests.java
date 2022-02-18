package com.davidread.todolist;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.File;

/**
 * {@link ToDoListInstrumentedTests} has some example instrumented tests for this project. These will
 * execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class ToDoListInstrumentedTests {

    /**
     * Instrumented test that verifies that {@link ToDoList#saveToFile()} creates a {@link File} in
     * the proper directory.
     *
     * @throws Exception Thrown when any exception occurs.
     */
    @Test
    public void testFileSaved() throws Exception {
        // Get Context for the application being instrumented.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Delete the file in case it exists.
        File file = new File(appContext.getFilesDir(), ToDoList.FILENAME);
        file.delete();
        assertFalse(file.exists());

        // Save the file.
        ToDoList list = new ToDoList(appContext);
        list.saveToFile();

        // Make sure the file now exists.
        assertTrue(file.exists());
    }
}