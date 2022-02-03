package com.example.todolist;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ToDoList} is a model class for a list of to-dos. {@link #mTaskList} serves as a
 * temporary storage for to-dos. Persisted to-dos may be retrieved using {@link #readFromFile()}
 * and saved using {@link #saveToFile()}.
 */
public class ToDoList {

    /**
     * {@link String} name of the file holding the persisted list of to-dos.
     */
    public static final String FILENAME = "todolist.txt";

    /**
     * {@link Context} for reading and writing files to internal storage.
     */
    private Context mContext;

    /**
     * {@link List} of {@link String} objects for each to-do. Is a temporary storage place for
     * to-dos.
     */
    private List<String> mTaskList;

    /**
     * Constructs a new {@link ToDoList}.
     *
     * @param context {@link Context} for reading and writing files to internal storage.
     */
    public ToDoList(Context context) {
        mContext = context;
        mTaskList = new ArrayList<>();
    }

    /**
     * Adds a new {@link String} to-do to {@link #mTaskList}.
     *
     * @param item {@link String} to-do to add.
     */
    public void addItem(String item) throws IllegalArgumentException {
        mTaskList.add(item);
    }

    /**
     * Returns a {@link String} array of to-dos stored in {@link #mTaskList}.
     *
     * @return {@link String} array of to-dos.
     */
    public String[] getItems() {
        String[] items = new String[mTaskList.size()];
        return mTaskList.toArray(items);
    }

    /**
     * Clears {@link #mTaskList}.
     */
    public void clear() {
        mTaskList.clear();
    }

    /**
     * Saves the to-dos stored in {@link #mTaskList} to a  file for persistent storage.
     */
    public void saveToFile() throws IOException {
        FileOutputStream outputStream = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : mTaskList) {
            writer.println(item);
        }
        writer.close();
    }

    /**
     * Populates {@link #mTaskList} with the to-dos stored in the file.
     */
    public void readFromFile() throws IOException {
        FileInputStream inputStream = mContext.openFileInput(FILENAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            mTaskList.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                mTaskList.add(line);
            }
        } catch (FileNotFoundException ex) {
            // Do nothing if FileNotFoundException.
        }
    }
}
