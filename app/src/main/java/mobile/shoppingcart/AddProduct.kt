package mobile.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mobile.kotlinlogin.model.LoginModel
import mobile.kotlinlogin.rest.ApiLogin
import kotlinx.android.synthetic.main.login.*
import kotlinx.coroutines.*
import mobile.kotlinlogin.rest.AppPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity() {
    var dataList = ArrayList<LoginModel>()
    var uemail:String=""
    var upass:String=""
    var successlogin:String = "0"
    private var typeText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        AppPreferences.init(this)
       if(AppPreferences.isLogin){
           val intent = Intent(this, MainActivity::class.java)
           // start your next activity
           startActivity(intent)

           finish()
       }

        val btn_login = findViewById(R.id.button2) as Button
// set on-click listener
        btn_login.setOnClickListener {

            uemail=useremail.text.toString()
            upass=  userpass.text.toString()

            getDat1a()

        }
    }
    private fun getDat1a() {

        val call: Call<List<LoginModel>> = ApiLogin.getClient.userlogin(uemail,upass)
        llProgressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<List<LoginModel>> {

            override fun onResponse(call: Call<List<LoginModel>>?, response: Response<List<LoginModel>>?) {
               successlogin= response?.body()?.get(0)?.success.toString()

                if(successlogin.equals("1")) {

                    AppPreferences.isLogin = true
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    // start your next activity
                    startActivity(intent)
                    llProgressBar.visibility = View.GONE
                    finish()

                }
                else
                {  llProgressBar.visibility = View.GONE
                    Toast.makeText(applicationContext,"Wrong login data", Toast.LENGTH_SHORT).show()}
            }

            override fun onFailure(call: Call<List<LoginModel>>?, t: Throwable?) {
                Toast.makeText(applicationContext,"something wrong", Toast.LENGTH_SHORT).show()

            }

        })
    }
}
