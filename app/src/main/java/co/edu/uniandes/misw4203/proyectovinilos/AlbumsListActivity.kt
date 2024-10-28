package co.edu.uniandes.misw4203.proyectovinilos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.uniandes.misw4203.proyectovinilos.ui.album.AlbumFragment

class AlbumsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_albums_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Check if user is admin
        val isAdmin = intent?.extras?.getBoolean("isAdmin")

        if (isAdmin == true) {
            Log.d("AlbumsListActivity", "PRUEBA:::" + isAdmin)
            Toast.makeText(applicationContext, "Soy el Admin", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("AlbumsListActivity", "PRUEBA:::" + isAdmin)
            Toast.makeText(applicationContext, "Soy invitado", Toast.LENGTH_SHORT).show()
        }

        // Load AlbumFragment
        val albumFragment = AlbumFragment()
        val bundle = Bundle()
        bundle.putBoolean("isAdmin", isAdmin ?: false) // Pass isAdmin value
        albumFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, albumFragment)
            .commit()
    }
}