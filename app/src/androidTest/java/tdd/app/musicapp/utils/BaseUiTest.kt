package tdd.app.musicapp.utils

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.IdlingRegistry
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import tdd.app.musicapp.modules.idlingResource

open class BaseUiTest {

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description?) {
                description?.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(item: View?): Boolean {
                if (item?.parent !is ViewGroup) return false
                val parent: ViewGroup = item.parent as ViewGroup
                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == item)
            }
        }
    }
}