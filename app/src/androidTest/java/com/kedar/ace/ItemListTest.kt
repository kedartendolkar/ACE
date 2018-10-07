package com.kedar.ace

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.kedar.ace.ui.ItemListActivity
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ItemListTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<ItemListActivity> = ActivityTestRule(ItemListActivity::class.java)

    @Test
    fun onSuccess() {
        Thread.sleep(5000)
        onView(withId(R.id.rec_item_list)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_item_list)).check(matches(not(isDisplayed())));
        onView(withId(R.id.txt_no_data_item_list)).check(matches(not(isDisplayed())));
    }

    @Test
    @Throws(InterruptedException::class)
    fun onFailure() {
        Thread.sleep(5000)
        onView(withId(R.id.rec_item_list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.progress_item_list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.txt_no_data_item_list)).check(matches(isDisplayed()))
    }
}