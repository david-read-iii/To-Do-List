package com.davidread.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

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