package co.edu.uniandes.misw4203.proyectovinilos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.View

class BottomBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_bar)
        navView.setupWithNavController(navController)

        val isAdmin = intent.getBooleanExtra("isAdmin", false)

        // Navegar al fragmento inicial con argumentos
        val bundle = Bundle().apply {
            putBoolean("isAdmin", isAdmin)
        }
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_album -> {
                    navController.navigate(R.id.navigation_album, bundle)
                    true
                }
                R.id.navigation_artist -> {
                    navController.navigate(R.id.navigation_artist, bundle)
                    true
                }
                R.id.navigation_collector -> {
                    navController.navigate(R.id.navigation_collector, bundle)
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            navController.navigate(R.id.navigation_album, bundle)
        }

        // Ocultar BottomNavigationView en fragmentos de detalle
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hiddenDestinations = listOf(R.id.albumDetailFragment, R.id.artistDetailFragment,
                R.id.collectorDetailFragment, R.id.createAlbumFragment, R.id.addTrackFragment,
                R.id.addAlbumArtistFragment)

            navView.visibility = if (destination.id in hiddenDestinations) View.GONE else View.VISIBLE
        }

    }
}