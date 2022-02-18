package com.davidread.todolist;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

import android.content.Context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * {@link ToDoListUnitTests} has some example unit tests that utilize Mockito for this project.
 * These will execute on the development machine.
 */
@RunWith(MockitoJUnitRunner.class)
public class ToDoListMockitoUnitTests {

    /**
     * Mocked {@link Context} for using the {@link Context#openFileOutput(String, int)} method.
     */
    @Mock
    Context mMockContext;

    /**
     * {@link TemporaryFolder} for providing a temporary file directory for storing files created in
     * these tests.
     */
    @Rule
    public TemporaryFolder mTempFolder = new TemporaryFolder();

    /**
     * {@link String} array of to-dos used in these tests.
     */
    public final String[] TODOS = {"mow lawn", "feed dog"};

    /**
     * Returns a {@link File} in {@link #mTempFolder} containing some to-dos.
     *
     * @return A {@link File} in {@link #mTempFolder} containing some to-dos.
     * @throws IOException Thrown when an exception occurs due to failed or interrupted IO
     *                     operations.
     */
    private File createTempFile() throws IOException {

        // Create temp file that holds the todos
        File tempFile = mTempFolder.newFile("tempfile");
        PrintWriter writer = new PrintWriter(tempFile);
        for (String item : TODOS) {
            writer.println(item);
        }
        writer.close();
        return tempFile;
    }

    /**
     * Unit test that verifies that {@link ToDoList#saveToFile()} properly stores its to-do list in
     * a {@link File}.
     *
     * @throws Exception Thrown when any exception occurs.
     */
    @Test
    public void testSaveToFile() throws Exception {

        // Create a file for the todos in the temporary folder
        File writeFile = mTempFolder.newFile(ToDoList.FILENAME);

        // Provide alternative behavior when openFileOutput() is called
        when(mMockContext.openFileOutput(ToDoList.FILENAME, Context.MODE_PRIVATE))
                .thenReturn(new FileOutputStream(writeFile));

        // Add the todos to the list and save the file
        ToDoList list = new ToDoList(mMockContext);
        for (String item : TODOS) {
            list.addItem(item);
        }
        list.saveToFile();

        // Create temp file with same todos
        File tempFile = createTempFile();

        // Verify files are identical
        byte[] expectedBytes = Files.readAllBytes(Paths.get(tempFile.getAbsolutePath()));
        byte[] actualBytes = Files.readAllBytes(Paths.get(writeFile.getAbsolutePath()));
        assertArrayEquals(expectedBytes, actualBytes);
    }
}