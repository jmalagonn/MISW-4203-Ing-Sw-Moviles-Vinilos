package co.edu.uniandes.misw4203.proyectovinilos


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumStructureForm {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createAlbumStructureForm() {
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

        // Validar existencia de campo Album Name
        onView(withId(R.id.albumNameInput))
            .perform(scrollTo(), click())
            .perform(closeSoftKeyboard())
            .check(matches(isDisplayed()))


        // Validar existencia de campo Album Cover
        onView(withId(R.id.albumCoverInput))
            .perform(scrollTo(), click())
            .perform(closeSoftKeyboard())
            .check(matches(isDisplayed()))


        // Validar existencia de campo Album Release Date
        onView(withId(R.id.albumReleaseDateInput))
            .perform(scrollTo(), click())
            .perform(closeSoftKeyboard())
            .check(matches(isDisplayed()))


        // Validar existencia de campo Album Description
        onView(withId(R.id.albumDescriptionInput))
            .perform(scrollTo(), click())
            .perform(closeSoftKeyboard())
            .check(matches(isDisplayed()))


        // Validar existencia de campo Album Genre
        onView(withId(R.id.albumGenreInput))
            .perform(scrollTo(), click())
            .perform(closeSoftKeyboard())
            .check(matches(isDisplayed()))

        // Validar existencia de campo Album Record Label
        onView(withId(R.id.albumRecordLabelInput))
            .perform(scrollTo(), click())
            .perform(closeSoftKeyboard())
            .check(matches(isDisplayed()))

        // Validar existencia del boton cancelar
        val button = onView(
            allOf(
                withId(R.id.cancel_album_button), withText("Cancelar"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        // Validar existencia del boton Agregar
        val button2 = onView(
            allOf(
                withId(R.id.saveButton), withText("Agregar"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))
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
