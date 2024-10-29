package co.edu.uniandes.misw4203.proyectovinilos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        navController.navigate(R.id.navigation_album, bundle)
    }
}