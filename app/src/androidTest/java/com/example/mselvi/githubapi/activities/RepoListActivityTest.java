package com.example.mselvi.githubapi.activities;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.mselvi.githubapi.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class RepoListActivityTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(RepoListActivity.class);

    @Test
    public void itemClickTest() throws InterruptedException {
      /*  onView(withId(R.id.repo_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));

        onView(withId(R.id.card_view))
                .check(matches(withText("27")))
                .check(matches(isDisplayed()));*/

        onView(withId(R.id.repo_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(4, MyViewAction.clickChildViewWithId(R.id.save)));
        onView(withId(R.id.repo_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(4, MyViewAction.clickChildViewWithId(R.id.delete)));

    }
    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}
