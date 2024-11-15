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
class LoginViewCollectorDetail {


    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown() {
        mActivityScenarioRule.scenario.close()
    }

    @Test
    fun viewAlbumDetailVisitor() {
        val materialTextView = onView(
            allOf(
                withId(R.id.guestButton),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        // Acción para tocar el tab inferior 'navigation_collector'
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_collector),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        // Selección aleatoria del índice de la colección entre 1 y 2
        val randomCollector = Random.nextInt(1, 2)

        val recyclerView = onView(
            allOf(
                withId(R.id.collectorsRv),
                isDisplayed()
            )
        )
        Thread.sleep(2000)
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(randomCollector, click()))
        Thread.sleep(2000)

        // Verificar la existencia de los elementos del coleccionista
        onView(withId(R.id.collectorName)).check(matches(isDisplayed()))
        onView(withId(R.id.collectorTel)).check(matches(isDisplayed()))
        onView(withId(R.id.collectorEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.collectorAvatar)).check(matches(isDisplayed()))

        // Verificar la existencia de la tabla
        onView(withId(R.id.favorite_performers_recycler_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }
}