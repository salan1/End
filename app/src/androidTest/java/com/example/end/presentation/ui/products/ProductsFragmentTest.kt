package com.example.end.presentation.ui.products


import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.example.end.R
import com.example.end.Stubs
import com.example.end.domain.interactors.GetProductsUseCase
import com.example.end.domain.models.Result
import com.example.end.espressoDaggerMockRule
import com.example.end.presentation.ui.MainActivity
import com.example.end.utils.RecyclerViewMatcher.Companion.withRecyclerView
import com.example.end.utils.RecyclerViewUtils.performItemClick
import com.example.end.utils.RecyclerViewUtils.scrollTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class ProductsFragmentTest {

    /*@Rule
    @JvmField
    var rule = espressoDaggerMockRule()*/

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

   // val getProductsUseCase: GetProductsUseCase = mock()

    @Before
    fun setUp() {
    }

    @Test
    fun givenGrid_RecyclerView() {
        /*val productsModel = Stubs.productsModelStub()
        whenever(getProductsUseCase.execute(any())).thenReturn(
            Single.just(
                Result.Success(
                    productsModel
                )
            )
        )*/

        // Launch activity
        activityRule.launchActivity(null)

        // TODO replace with IdlingResource
        Thread.sleep(2000)

        // Assert recyclerView has 50 items
        onView(withId(R.id.textView_items)).check(matches(withText("50 items")))
        performItemClick(R.id.recyclerView, 0)
        scrollTo(R.id.recyclerView, 50)
    }

    @Test
    fun givenLinear_RecyclerView() {
        /*val productsModel = Stubs.productsModelStub()
        whenever(getProductsUseCase.execute(any())).thenReturn(
            Single.just(
                Result.Success(
                    productsModel
                )
            )
        )*/

        // Launch activity
        activityRule.launchActivity(null)

        // TODO replace with IdlingResource
        Thread.sleep(2000)

        // Change recyclerView layout to vertical single items
        onView(withId(R.id.spinner_view)).perform(click())
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.spinner_view)).check(matches(withSpinnerText("Large")))

        Thread.sleep(2000)
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.textView_title))
            .check(matches(allOf(withText("Test Shirt"))))
    }

}