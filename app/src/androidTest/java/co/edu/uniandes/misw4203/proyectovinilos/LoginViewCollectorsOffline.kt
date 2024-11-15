package co.edu.uniandes.misw4203.proyectovinilos

import androidx.test.platform.app.InstrumentationRegistry
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
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class LoginViewCollectorsOffline {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown() {
        mActivityScenarioRule.scenario.close()
    }

    @Test
    fun viewAlbumDetailVisitor() {

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

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

        // Ir al fragment de collecionistas
        Thread.sleep(700)
        onView(withId(R.id.navigation_collector)).perform(click())

        // abrir panel de navegaciones
        device.openNotification()

        // Realiza deslizamiento hacia abajo para abrir el panel completo de configuraciones rápidas
        val displayHeight = device.displayHeight
        val displayWidth = device.displayWidth
        device.swipe(displayWidth / 2, displayHeight / 4, displayWidth / 2, displayHeight / 2, 10)
        Thread.sleep(700)
        device.swipe(displayWidth / 2, displayHeight / 4, displayWidth / 2, displayHeight / 2, 10)

        val airplaneModeToggle = device.findObject(UiSelector().descriptionContains("Airplane Mode"))

        // si identifica la opcion de modo avion la activa
        if (airplaneModeToggle.exists() && airplaneModeToggle.isEnabled) {
            airplaneModeToggle.click()
        }

        // cerrar panel de navegaciones
        device.swipe(displayWidth / 2, displayHeight / 2, displayWidth / 2, displayHeight / 4, 10)
        Thread.sleep(700)
        device.swipe(displayWidth / 2, displayHeight / 2, displayWidth / 2, displayHeight / 4, 10)

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
        onView(withId(R.id.favorite_performers_recycler_view)).check(
            matches(
                withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE)
            )
        )

        // desactivar modo avion
        device.openNotification()
        device.swipe(displayWidth / 2, displayHeight / 4, displayWidth / 2, displayHeight / 2, 10)
        Thread.sleep(700)
        device.swipe(displayWidth / 2, displayHeight / 4, displayWidth / 2, displayHeight / 2, 10)

        if (airplaneModeToggle.exists() && airplaneModeToggle.isEnabled) {
            airplaneModeToggle.click()
        }

        device.swipe(displayWidth / 2, displayHeight / 2, displayWidth / 2, displayHeight / 4, 10)
        Thread.sleep(700)
        device.swipe(displayWidth / 2, displayHeight / 2, displayWidth / 2, displayHeight / 4, 10)

    }
}