package co.edu.uniandes.misw4203.proyectovinilos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText;
    private lateinit var loginButton: Button;
    private lateinit var loginAltButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameEditText = findViewById<TextInputEditText>(R.id.userInputEditText)
        passwordEditText = findViewById<TextInputEditText>(R.id.passwordInputEditText)
        loginButton = findViewById<Button>(R.id.loginButton)
        loginAltButton = findViewById<TextView>(R.id.guestButton)

        loginButton.setOnClickListener {
            goToAlbumsList(true)
        }

        loginAltButton.setOnClickListener {
            goToAlbumsList(false)
        }

    }

    private fun goToAlbumsList(isLogged: Boolean) {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val intent: Intent


        if (isLogged) {
            if (username == "admin" && password == "password") {
                intent = Intent(this, AlbumsListActivity::class.java)
                intent.putExtra("isAdmin", isLogged)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        else {
            intent = Intent( this, AlbumsListActivity::class.java)
            intent.putExtra("isAdmin", isLogged)
            startActivity(intent)
        }
    }
}