package com.davidread.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /**
     * Int identifier for a request permissions result for external storage write access.
     */
    private final int REQUEST_WRITE_CODE = 0;

    /**
     * {@link ToDoList} used to get to-dos from persistent storage and save to-dos to persistent
     * storage.
     */
    private ToDoList mToDoList;

    /**
     * {@link EditText} used as a field for entering new to-dos.
     */
    private EditText mItemEditText;

    /**
     * {@link TextView} used to display each to-do.
     */
    private TextView mItemListTextView;

    /**
     * Callback method invoked when this activity is initially created. It simply initializes the
     * member variables of this class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToDoList = new ToDoList(this);
        mItemEditText = findViewById(R.id.todo_item);
        mItemListTextView = findViewById(R.id.item_list);
    }

    /**
     * Callback method invoked when this activity is in the foreground. It attempts to retrieve
     * to-dos from persistent storage and display them on the UI.
     */
    @Override
    protected void onResume() {
        super.onResume();

        try {
            mToDoList.readFromFile();
            displayList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Callback method invoked when this activity leaves the foreground. It attempts to save
     * to-dos to persistent storage.
     */
    @Override
    protected void onPause() {
        super.onPause();

        try {
            mToDoList.saveToFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Invoked after each {@link #requestPermissions(String[], int)} call.
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        /* If the result is from a request to write to external storage and the permission was
         * granted, then attempt to write to the Downloads directory again. */
        if (requestCode == REQUEST_WRITE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadButtonClick(null);
            }
        }
    }

    /**
     * Method invoked when the add {@link android.widget.Button} is clicked. It registers the
     * text in {@link #mItemEditText} as a to-do with {@link #mToDoList}.
     */
    public void addButtonClick(View view) {
        String toDoText = mItemEditText.getText().toString().trim();
        mItemEditText.setText("");

        if (toDoText.length() > 0) {
            mToDoList.addItem(toDoText);
            displayList();
        }
    }

    /**
     * Method invoked when the clear {@link android.widget.Button} is clicked. It clears all
     * to-dos stored in {@link #mToDoList}.
     */
    public void clearButtonClick(View view) {
        mToDoList.clear();
        displayList();
    }

    /**
     * Method invoked when the download {@link android.widget.Button} is clicked. It takes all
     * to-dos stored in {@link #mToDoList} and puts them in a file in the device's external
     * storage Downloads directory.
     */
    public void downloadButtonClick(View view) {
        if (PermissionsUtil.hasPermissions(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                R.string.write_rationale,
                REQUEST_WRITE_CODE
        )) {
            try {
                if (mToDoList.downloadFile()) {
                    Toast.makeText(this, R.string.download_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.download_failed, Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Toast.makeText(this, R.string.download_failed, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates {@link #mItemListTextView} with a line for each to-do stored in {@link #mToDoList}.
     */
    private void displayList() {
        StringBuffer itemText = new StringBuffer();
        String[] items = mToDoList.getItems();
        for (int i = 0; i < items.length; i++) {
            itemText.append(i + 1).append(". ").append(items[i]).append("\n");
        }
        mItemListTextView.setText(itemText);
    }
}