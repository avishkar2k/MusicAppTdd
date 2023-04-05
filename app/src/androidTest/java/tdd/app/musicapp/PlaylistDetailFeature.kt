package tdd.app.musicapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.core.AllOf
import org.junit.Test
import org.junit.runner.RunWith
import tdd.app.musicapp.util.BaseUiTest

@RunWith(AndroidJUnit4::class)
class PlaylistDetailFeature : BaseUiTest() {

    @Test
    fun displaysPlaylistNameAndDetail() {
        navigateToDetailsScreen()

        //check title text view is displayed
        assertDisplayed(R.id.tv_playlist_title)

        //check desc text view is displayed
        assertDisplayed(R.id.tv_playlist_desc)

        //check the play list title matches with 0th position item
        onView(withId(R.id.tv_playlist_title))
            //.check(ViewAssertions.matches(ViewMatchers.withText("")))
            .check(ViewAssertions.matches(ViewMatchers.withText("Hard Rock Cafe")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //check the playlist desc matches with 0th position item
        onView(withId(R.id.tv_playlist_desc))
            //.check(ViewAssertions.matches(ViewMatchers.withText("")))
            .check(ViewAssertions.matches(ViewMatchers.withText("Rock your day with this timeless signature vibelist. \n\n • Poison \n • You shock me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        assertDisplayed("Rock your day with this timeless signature vibelist. \n\n • Poison \n • You shock me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    private fun navigateToDetailsScreen() {
        onView(
            AllOf(
                withId(R.id.iv_playlist_icon),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        ).perform(ViewActions.click())
    }
}