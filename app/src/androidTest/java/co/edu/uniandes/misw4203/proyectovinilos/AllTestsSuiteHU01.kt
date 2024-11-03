import co.edu.uniandes.misw4203.proyectovinilos.LoginFailTest
import co.edu.uniandes.misw4203.proyectovinilos.LoginFilterAlbum
import co.edu.uniandes.misw4203.proyectovinilos.LoginSussesfulAdmin
import co.edu.uniandes.misw4203.proyectovinilos.LoginSussesfulVisitor
import co.edu.uniandes.misw4203.proyectovinilos.LoginViewAlbums
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginFailTest::class,
    LoginSussesfulVisitor::class,
    LoginSussesfulAdmin::class,
    LoginViewAlbums::class,
    LoginFilterAlbum::class,
)
class AllTestsSuiteHU01
