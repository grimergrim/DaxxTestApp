package com.mysuperdispatch.test.daxxtestapp.post.list;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mysuperdispatch.test.daxxtestapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public ActivityTestRule<ListActivity> mActivityTestRule = new ActivityTestRule<>(ListActivity.class);

    @Test
    public void listActivityTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.reload_button),
                        childAtPosition(
                                allOf(withId(R.id.error_container),
                                        childAtPosition(
                                                withId(R.id.activity_list_main_layout),
                                                1)),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.eror_text), withText("Ooops, something is wrong. Please, try again"),
                        childAtPosition(
                                allOf(withId(R.id.error_container),
                                        childAtPosition(
                                                withId(R.id.activity_list_main_layout),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Ooops, something is wrong. Please, try again")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.eror_text), withText("Ooops, something is wrong. Please, try again"),
                        childAtPosition(
                                allOf(withId(R.id.error_container),
                                        childAtPosition(
                                                withId(R.id.activity_list_main_layout),
                                                1)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Ooops, something is wrong. Please, try again")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.reload_button), withText("Reload"),
                        childAtPosition(
                                allOf(withId(R.id.error_container),
                                        childAtPosition(
                                                withId(R.id.activity_list_main_layout),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.clear_button),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_container),
                                        childAtPosition(
                                                withId(R.id.detail_toolbar),
                                                0)),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.toolbar_container),
                        childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.activity_list_main_layout),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
