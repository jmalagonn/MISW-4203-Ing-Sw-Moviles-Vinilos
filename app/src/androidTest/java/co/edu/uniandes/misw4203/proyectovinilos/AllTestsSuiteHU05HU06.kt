package co.edu.uniandes.misw4203.proyectovinilos

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginViewCollectors::class,
    LoginViewCollectorDetail::class,
    LoginFIlterCollector::class,
    GoBackToCollectorsList::class,
    LoginViewCollectorsOffline::class
)

class AllTestsSuiteHU05HU06 {
}