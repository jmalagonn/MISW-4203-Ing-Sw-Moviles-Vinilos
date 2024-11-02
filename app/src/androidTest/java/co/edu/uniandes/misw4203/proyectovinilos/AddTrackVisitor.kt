package co.edu.uniandes.misw4203.proyectovinilos

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddTrackVisitor {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown() {
        mActivityScenarioRule.scenario.close()
    }

    @Test
    fun addTrackVisitor() {
        val materialTextView = onView(
            allOf(
                withId(R.id.guestButton),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        // Selección aleatoria del índice del álbum entre 1 y 15
        val randomAlbumPosition = Random.nextInt(1, 15)

        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                isDisplayed()
            )
        )
        Thread.sleep(2000)
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(randomAlbumPosition, click()))
        Thread.sleep(2000)

        // Verificar que el botón "Agregar Track" no sea visible en la pantalla
        onView(withId(R.id.addTrackButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    }
}