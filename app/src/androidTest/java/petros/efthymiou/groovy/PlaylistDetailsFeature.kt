package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.Matchers.allOf
import org.junit.Test
import petros.efthymiou.groovy.playlist.idlingResource

class PlaylistDetailsFeature : BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails() {
        navigateToPlaylistDetails()

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n " +
                "• Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n " +
                "• Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n " +
                "• Knockin' on Heavens Door")
    }


    @Test
    fun displayLoaderWhileFetchingThePlaylistDetails(){
        IdlingRegistry.getInstance().unregister(idlingResource)

        Thread.sleep(3000)

        navigateToPlaylistDetails()


        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hideLoader(){
        navigateToPlaylistDetails()

        assertNotDisplayed(R.id.details_loader)
    }


    private fun navigateToPlaylistDetails() {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))
            )
        )
            .perform(click())
    }


}