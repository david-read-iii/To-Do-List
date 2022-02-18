package com.davidread.todolist;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * {@link UiTest} has some example UI tests for this project. These will execute on an Android
 * device.
 */
public class UiTest {

    /**
     * Invoked before each test case. It constructs a new {@link ActivityScenarioRule} for starting
     * a new {@link MainActivity}.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * UI test that verifies that an item may be properly added to the to-do list in
     * {@link MainActivity}.
     */
    @Test
    public void testAddingAnItem() {

        // Click Clear button.
        onView(withText("Clear")).perform(click());

        // Enter "Feed dog" into the EditText.
        onView(withId(R.id.todo_item)).perform(typeText("Feed dog"));

        // Click Add button.
        onView(withId(R.id.add_button)).perform(click());

        // Verify item added to to-do list.
        onView(withId(R.id.item_list)).check(matches(withText("1. Feed dog\n")));
    }
}