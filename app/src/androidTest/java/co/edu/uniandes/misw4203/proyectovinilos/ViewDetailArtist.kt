package co.edu.uniandes.misw4203.proyectovinilos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@LargeTest
@RunWith(AndroidJUnit4::class)
class ViewDetailArtist {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown() {
        mActivityScenarioRule.scenario.close()
    }

    @Test
    fun viewDetailArtist() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.userInputEditText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userInput),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("admin"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.passwordInputEditText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordInput),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("password"), closeSoftKeyboard())

        // Hacer clic en el botón de login
        onView(allOf(withId(R.id.loginButton), withText("Login")))
            .perform(click())

        // Selección aleatoria del índice del artista entre 1 y 3
        val randomArtistPosition = Random.nextInt(1, 3)

        Thread.sleep(700)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_artist), withContentDescription("Artistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.artistRv),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(randomArtistPosition, click()))

        Thread.sleep(3000)

        // Verificar la existencia de los elementos, nombre, cumpleaños, imagen,y descripcion
        onView(withId(R.id.artistName)).check(matches(isDisplayed()))
        onView(withId(R.id.birthDate)).check(matches(isDisplayed()))
        onView(withId(R.id.artistImage)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))

        // Verificar la existencia de la tabla
        onView(withId(R.id.album_recycler_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}