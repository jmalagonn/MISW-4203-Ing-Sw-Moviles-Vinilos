
import co.edu.uniandes.misw4203.proyectovinilos.AddTrackAdmin
import co.edu.uniandes.misw4203.proyectovinilos.AddTrackVisitor
import co.edu.uniandes.misw4203.proyectovinilos.DetailAlbumBackButtom
import co.edu.uniandes.misw4203.proyectovinilos.ViewDetailAlbumAdmin
import co.edu.uniandes.misw4203.proyectovinilos.ViewDetailAlbumVisitor
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    DetailAlbumBackButtom::class,
    ViewDetailAlbumVisitor::class,
    ViewDetailAlbumAdmin::class,
    AddTrackVisitor::class,
    AddTrackAdmin::class,


)

class AllTestsSuiteHU02