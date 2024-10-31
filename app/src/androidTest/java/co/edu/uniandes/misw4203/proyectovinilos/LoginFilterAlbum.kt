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
import org.hamcrest.CoreMatchers.containsString
import android.os.Handler
import android.os.Looper


@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginFilterAlbum {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loginFilterAlbum() {
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

        val appCompatEditText = onView(
            allOf(
                withId(R.id.searchAlbumEditText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("poeta"), closeSoftKeyboard())

        Handler(Looper.getMainLooper()).postDelayed({
            val textView = onView(
                allOf(
                    withId(R.id.albumName),
                    withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                    isDisplayed()
                )
            )
            // Aserción: Verificar que el texto del álbum contiene "POETA"
            textView.check(matches(withText(containsString("POETA"))))
        }, 3000)  // Tiempo de espera en milisegundos (3 segundos)
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
