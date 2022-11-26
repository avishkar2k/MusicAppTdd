package tdd.app.musicapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tdd.app.musicapp.modules.idlingResource
import tdd.app.musicapp.ui.MainActivity
import tdd.app.musicapp.utils.BaseUiTest

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlaylistFeature : BaseUiTest() {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlist")
    }

    @Test
    fun displayListOfPlaylistItems() {

        //check the count of item in the list
        assertRecyclerViewItemCount(R.id.rv_playlist, 10)

        //match the playlist title at 0th item values
        onView(
            allOf(
                withId(R.id.tv_playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        )
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))


        //match the playlist category at 0th item value
        onView(
            allOf(
                withId(R.id.tv_playlist_category),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        )
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        //match the playlist images
        onView(
            allOf(
                withId(R.id.iv_playlist_icon),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 1))
            )
        )
            .check(matches(withDrawable(R.mipmap.playlist)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingPlaylists() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoaderAfterFetchingPlaylists() {
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun displaysRockImageForRockCategory() {

        //match the playlist title at 0th item values
        onView(
            allOf(
                withId(R.id.iv_playlist_icon),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        )
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

        //check rock category image at position
        onView(
            allOf(
                withId(R.id.iv_playlist_icon),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 3))
            )
        )
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

    }


    @Test
    fun navigateToDetailsScreen() {
        onView(
            allOf(
                withId(R.id.iv_playlist_icon),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        ).perform(click())

        assertDisplayed(R.id.playlist_detail_container)
    }

}