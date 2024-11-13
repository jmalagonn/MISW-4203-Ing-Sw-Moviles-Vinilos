package co.edu.uniandes.misw4203.proyectovinilos

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
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
class ViewDetailAlbumAdmin {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown() {
        mActivityScenarioRule.scenario.close()
    }

    @Test
    fun viewDetailAlbumAdmin() {
        // Ingresar texto en el campo de usuario
        onView(withId(R.id.userInputEditText))
            .perform(replaceText("admin"))

        // Ingresar texto en el campo de contraseña
        onView(withId(R.id.passwordInputEditText))
            .perform(replaceText("password"))

        // Hacer clic en el botón de login
        onView(allOf(withId(R.id.loginButton), withText("Login")))
            .perform(click())

        // Selección aleatoria del índice del álbum entre 1 y 15
        val randomAlbumPosition = Random.nextInt(1, 9)

        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(randomAlbumPosition, click()))

        Thread.sleep(3000)

        // Verificar la existencia de los elementos, album, fecha de lanzamiento, disquera, albumcover y descripcion
        onView(withId(R.id.albumTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.recordLabel)).check(matches(isDisplayed()))
        onView(withId(R.id.albumCover)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))

        // Verificar la existencia de la tabla
        onView(withId(R.id.track_recycler_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        }
    }