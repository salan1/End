package com.example.end.utils

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.CoreMatchers.allOf


class RecyclerViewMatcher(private val recyclerViewMatcher: Matcher<View>) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            internal var resources: Resources? = null
            internal var childView: View? = null

            override fun describeTo(description: Description) {
                recyclerViewMatcher.describeTo(description)
                description.appendText(" at position $position")
                if (targetViewId != -1) {
                    var idDescription = targetViewId.toString()
                    if (resources != null) {
                        try {
                            idDescription = resources!!.getResourceName(targetViewId)
                        } catch (var4: Resources.NotFoundException) {
                            idDescription = "$targetViewId (resource name not found)"
                        }

                    }
                    description.appendText(" on view with id $idDescription")
                }
            }

            public override fun matchesSafely(view: View): Boolean {
                resources = view.resources

                if (childView == null) {
                    val parent = MatcherUtils.getMatchingParent(view, recyclerViewMatcher)
                    if (parent == null || parent !is RecyclerView) {
                        return false
                    }
                    val recyclerView = parent as RecyclerView?
                    val viewHolder = recyclerView!!
                        .findViewHolderForAdapterPosition(position) ?: return false
                    childView = viewHolder.itemView
                }

                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    view === targetView
                }

            }
        }
    }

    companion object {

        fun withRecyclerView(recyclerViewMatcher: Matcher<View>): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewMatcher)
        }

        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(allOf(withId(recyclerViewId), isDisplayed()))
        }

        fun withNestedRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(
                allOf(
                    withId(recyclerViewId),
                    withParent(withParent(isDisplayed()))
                )
            )
        }
    }
}