package br.com.veronezitecnologia.pingatech.view.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private val newUserRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        //if (mAuth.currentUser != null) {
        //    goToHome()
        //}

        btLogin.setOnClickListener {
            if(inputLoginEmail.text.isEmpty() && inputLoginPassword.text.isEmpty()) {
                Toast.makeText(this@LoginActivity,this.getString(R.string.error_account), Toast.LENGTH_LONG).show()
            } else {
                mAuth.signInWithEmailAndPassword(
                    inputLoginEmail.text.toString(),
                    inputLoginPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        goToHome()
                    } else {
                        Toast.makeText(this@LoginActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btSignup.setOnClickListener {
            startActivityForResult(Intent(this, RegisterActivity::class.java), newUserRequestCode)
        }
    }
    private fun goToHome() {
        //val intent = Intent(this, RegisterActivity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //startActivity(intent)
        //finish()
        //startActivityForResult(Intent(this, HomeActivity::class.java), newUserRequestCode)
        val nextScreen = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(nextScreen)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
    }

}
