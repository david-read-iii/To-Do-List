package com.davidread.todolist;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
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
     * Writes a {@link File} to the device's internal storage containing the to-dos stored in
     * {@link #mTaskList}.
     */
    public void saveToFile() throws IOException {
        FileOutputStream outputStream = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }

    /**
     * Populates {@link #mTaskList} with to-dos stored in a {@link File} written on the device's
     * internal storage using {@link #saveToFile()}.
     */
    public void readFromFile() throws IOException {

        BufferedReader reader = null;

        try {
            FileInputStream inputStream = mContext.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            mTaskList.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                mTaskList.add(line);
            }
        } catch (FileNotFoundException e) {
            // Do nothing if FileNotFoundException.
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Writes a {@link File} to the device's external storage Downloads directory containing the
     * to-dos stored in {@link #mTaskList}.
     *
     * @return Whether external storage is availiable to be written to.
     */
    public boolean downloadFile() throws IOException {

        // Return with fail if the device's external storage is unavailable.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        }

        // Get reference to the Downloads directory.
        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Create Downloads directory if it doesn't exist.
        if (!downloadsDirectory.exists()) {
            downloadsDirectory.mkdirs();
        }

        // Write file to Downloads directory.
        File file = new File(downloadsDirectory, FILENAME);
        writeListToStream(new FileOutputStream(file));

        return true;
    }

    /**
     * Writes each to-do stored in {@link #mTaskList} to the given {@link FileOutputStream}.
     *
     * @param outputStream {@link FileOutputStream} where the to-dos should be written.
     */
    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : mTaskList) {
            writer.println(item);
        }
        writer.close();
    }
}
