package net.sarazan.statement

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.ActivityInstrumentationTestCase2
import android.test.suitebuilder.annotation.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Created by Aaron Sarazan on 7/26/15
 * Copyright(c) 2015 Level, Inc.
 */

@LargeTest
@RunWith(AndroidJUnit4::class)
public class BasicTest : ActivityInstrumentationTestCase2<MainActivity>(javaClass()) {

    @Rule
    public val mainActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(javaClass())

    public fun listIsEmpty() {
//        onView(withId(R.id.recycler)).check(matches(withChild(doesNotExist())))
        // Need to figure out espresso. Not thrilled so far.
    }

}