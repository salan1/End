package com.example.end.utils


import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher

object MatcherUtils {
    fun getParentViewById(view: View, parentViewId: Int): View? {
        if (view.id == parentViewId) {
            return view
        } else if (view.parent != null && view.parent is ViewGroup) {
            return getParentViewById(view.parent as View, parentViewId)
        }
        return null
    }

    fun isInViewHierarchy(view: View, viewToFind: View): Boolean {
        if (view === viewToFind) {
            return true
        } else if (view.parent != null && view.parent is ViewGroup) {
            return isInViewHierarchy(view.parent as View, viewToFind)
        }
        return false
    }

    fun matchToolbarTitle(title: CharSequence): ViewInteraction {
        return onView(isAssignableFrom(Toolbar::class.java))
            .check(ViewAssertions.matches(withToolbarTitle(`is`(title))))
    }

    fun withToolbarTitle(
        textMatcher: Matcher<CharSequence>): Matcher<Any> {
        return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
            public override fun matchesSafely(toolbar: Toolbar): Boolean {
                return textMatcher.matches(toolbar.title)
            }

            override fun describeTo(description: Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }
        }
    }

    fun getMatchingParent(view: View?, matcher: Matcher<View>): View? {
        if (view == null) {
            return null
        }
        if (matcher.matches(view)) {
            return view
        } else if (view.parent != null && view.parent is ViewGroup) {
            return getMatchingParent(view.parent as View, matcher)
        }
        return null
    }

    /**
     * Returns a matcher that matches a descendant of [TextView] that is displaying the string
     * associated with the given resource id.
     *
     * @param resourceId the string resource the text view is expected to hold.
     */
    fun containsStringRes(resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View, TextView>(TextView::class.java) {
            private var resourceName: String? = null
            private var expectedText: String? = null

            override fun describeTo(description: Description) {
                description.appendText("contains string from resource id: ")
                description.appendValue(resourceId)
                if (resourceName != null) {
                    description.appendText("[")
                    description.appendText(resourceName)
                    description.appendText("]")
                }
                if (expectedText != null) {
                    description.appendText(" value: ")
                    description.appendText(expectedText)
                }
            }

            public override fun matchesSafely(textView: TextView): Boolean {
                if (expectedText == null) {
                    try {
                        expectedText = textView.resources.getString(resourceId)
                        resourceName = textView.resources.getResourceEntryName(resourceId)
                    } catch (ignored: Resources.NotFoundException) {
                        /* view could be from a context unaware of the resource id. */
                    }

                }
                val actualText = textView.text
                // FYI: actualText may not be string ... its just a char sequence convert to string.
                return expectedText != null && actualText != null &&
                        actualText.toString().contains(expectedText!!)
            }
        }
    }

}