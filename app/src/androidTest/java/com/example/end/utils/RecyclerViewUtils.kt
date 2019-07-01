package com.example.end.utils


import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*

object RecyclerViewUtils {

    fun performItemClick(@IdRes recyclerViewResId: Int, position: Int) {
        onView(withId(recyclerViewResId))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    fun scrollTo(@IdRes recyclerViewResId: Int, position: Int) {
        onView(withId(recyclerViewResId))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
    }

    fun scrollTo(@IdRes recyclerViewResId: Int, itemTitle: String) {
        onView(withId(recyclerViewResId))
            .perform(
                scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(itemTitle))
                )
            )
    }

}