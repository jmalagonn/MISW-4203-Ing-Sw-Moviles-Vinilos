package co.edu.uniandes.misw4203.proyectovinilos


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.javafaker.Faker
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateTrackSussesful {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createTrackSussesful() {

        val faker = Faker()
        val trackName = faker.book().title()

        Thread.sleep(500)

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
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
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
        textInputEditText2.perform(replaceText("admin"), closeSoftKeyboard())

        val textInputEditText3 = onView(
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
        textInputEditText3.perform(replaceText("password"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.loginButton), withText("Login"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        Thread.sleep(700)

    // Selección aleatoria del índice del álbum entre 1 y 9
        val randomAlbumPosition = Random.nextInt(1, 9)

        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                isDisplayed()
            )
        )
        Thread.sleep(1000)
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(randomAlbumPosition, click()))

        Thread.sleep(1000)

        val materialButton2 = onView(
            allOf(
                withId(R.id.addTrackButton), withText("+ AGREGAR TRACK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        5
                    ),
                    2
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        onView(withId(R.id.trackNameEditText))
            .perform(scrollTo(), click())
            .perform(typeText(trackName), closeSoftKeyboard())

        onView(withId(R.id.trackDurationEditText))
            .perform(scrollTo(), click())
            .perform(typeText("04:43"), closeSoftKeyboard())

        // Validar existencia del boton agregar
        onView(withId(R.id.addTrackButton))
            .check(matches(isDisplayed()))
            .perform(click())

        // Aserción para validar que existe track creado
        Thread.sleep(2000)
        onView(withId(R.id.track_recycler_view))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(trackName))
                )
            )

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
