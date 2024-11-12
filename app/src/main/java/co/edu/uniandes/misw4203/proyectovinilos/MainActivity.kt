package co.edu.uniandes.misw4203.proyectovinilos

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.app.Dialog
import co.edu.uniandes.misw4203.proyectovinilos.database.VinylRoomDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText;
    private lateinit var loginButton: Button;
    private lateinit var loginAltButton: Button

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
        loginAltButton = findViewById<Button>(R.id.guestButton)

        loginButton.setOnClickListener {
            //goToAlbumsList(true)
            navigateToBottomBar(isAdmin = true)

        }

        loginAltButton.setOnClickListener {
            //goToAlbumsList(false)
            navigateToBottomBar(isAdmin = false)
        }

    }

    private fun navigateToBottomBar(isAdmin: Boolean) {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (isAdmin) {
            if (username == "admin" && password == "password") {
                val intent = Intent(this, BottomBarActivity::class.java).apply {
                    putExtra("isAdmin", true)
                }
                startActivity(intent)
                finish()
            } else {
                //Toast.makeText(applicationContext, "Datos incorrectos", Toast.LENGTH_LONG).show()
                showNotificationDialog("Datos incorrectos")
            }
        } else {
            val intent = Intent(this, BottomBarActivity::class.java).apply {
                putExtra("isAdmin", false)
            }
            startActivity(intent)
            finish()  // Cierra la actividad de login
        }
    }

    private fun showNotificationDialog(message: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.notification_dialog)
        dialog.setCancelable(true)

        val notificationMessage = dialog.findViewById<TextView>(R.id.notificationMessage)
        val closeButton = dialog.findViewById<Button>(R.id.closeNotificationButton)

        notificationMessage.text = message

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    class VinylsApplication: Application()  {
        val database by lazy { VinylRoomDatabase.getDatabase(this) }
    }



}