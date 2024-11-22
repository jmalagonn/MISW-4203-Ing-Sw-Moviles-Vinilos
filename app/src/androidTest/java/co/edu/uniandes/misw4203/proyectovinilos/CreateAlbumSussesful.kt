package co.edu.uniandes.misw4203.proyectovinilos


import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random
import com.github.javafaker.Faker
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.core.IsInstanceOf

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumSussesful {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createAlbumSussesful() {

        val faker = Faker()
        val albumName = faker.book().title()
        val albumDescription = faker.lorem().paragraph().take(25)

        // Listas de valores para género y disquera
        val genres = listOf("Classical", "Salsa", "Rock", "Folk")
        val recordLabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")

        // Seleccionar valores dinámicos de las listas
        val selectedGenre = genres[Random.nextInt(genres.size)]
        val selectedRecordLabel = recordLabels[Random.nextInt(recordLabels.size)]

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

        val materialButton2 = onView(
            allOf(
                withId(R.id.addAlbumButton), withText("+ AGREGAR ÁLBUM"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        onView(withId(R.id.albumNameInput))
            .perform(scrollTo(), click())
            .perform(typeText(albumName), closeSoftKeyboard())

        onView(withId(R.id.albumCoverInput))
            .perform(scrollTo(), click())
            .perform(typeText("https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg"), closeSoftKeyboard())

        onView(withId(R.id.albumReleaseDateInput))
            .perform(scrollTo(), click())
            .perform(typeText("1984-08-01"), closeSoftKeyboard())

        onView(withId(R.id.albumDescriptionInput))
            .perform(scrollTo(), click())
            .perform(typeText(albumDescription), closeSoftKeyboard())

        onView(withId(R.id.albumGenreInput))
            .perform(scrollTo(), click())
            .perform(typeText(selectedGenre), closeSoftKeyboard())

        onView(withId(R.id.albumRecordLabelInput))
            .perform(scrollTo(), click())
            .perform(typeText(selectedRecordLabel), closeSoftKeyboard())

        Thread.sleep(1000)

        onView(withId(R.id.saveButton))
            .check(matches(isDisplayed()))
            .perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.searchAlbumEditText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        Thread.sleep(1000)
        appCompatEditText.perform(scrollTo(), replaceText(albumName), closeSoftKeyboard())
        Thread.sleep(1000)

        Handler(Looper.getMainLooper()).postDelayed({
            val textView = onView(
                allOf(
                    withId(R.id.albumName),
                    withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                    isDisplayed()
                )
            )
            // Aserción: Verificar que el texto del álbum contiene albumName
            textView.check(matches(withText(containsString(albumName))))
        },3000)
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
