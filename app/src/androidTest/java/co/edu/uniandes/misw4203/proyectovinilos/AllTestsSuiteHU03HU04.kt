package co.edu.uniandes.misw4203.proyectovinilos

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginViewArtist::class,
    ViewDetailArtist::class,
    LoginFilterArtist::class,
    DetailArtistBackButtom::class,
    LoginViewArtistOffline::class,
    )

class AllTestsSuiteHU03HU04