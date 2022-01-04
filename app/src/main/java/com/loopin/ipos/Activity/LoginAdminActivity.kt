package com.loopin.ipos .Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.loopin.ipos.Model.LoginResponse
import com.loopin.ipos.Model.ZoneModel
import com.loopin.ipos.R
import com.loopin.ipos.Utils.*
import kotlinx.android.synthetic.main.activity_login_admin.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.HashMap

class LoginAdminActivity : AppCompatActivity(), View.OnClickListener{

    private var zoneSpinner: Spinner? = null
    private var zoneList: ArrayList<String>? = null
    private lateinit var password:TextView
    private lateinit var login:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setTheme(android.R.style.Theme_Dialog)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login_admin)
//        this.setFinishOnTouchOutside(false)
        init()

        setZone()
        checkValid()

        zoneSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.backFromAdminLogin ->
                finish()
            R.id.login ->
                adminLogin(zoneSpinner?.selectedItem.toString(), password.text.toString())
        }
    }

    private fun init(){
        zoneSpinner = findViewById(R.id.zoneSpinner)
        zoneList = ArrayList<String>()
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)

        backFromAdminLogin.setOnClickListener(this)
        login.setOnClickListener(this)
        login.isEnabled = false
        login.alpha = 0.5f
    }

    private fun setZone() {
        zoneList?.clear()
        IposProgress.progressDialog(this).setMessage("All zones are loading...")
        val request = object : StringRequest(Method.POST, IposConst.volleyUrl, com.android.volley.Response.Listener { response ->
            try {
                val `object` = JSONObject(response)
                if (`object`.getString("error") == "0") {
                    IposProgress.stop()
                    val array = `object`.getJSONArray("data")
                    for (i in 0 until array.length()) {
                        val object1 = array.getJSONObject(i)
                        zoneList!!.add(object1.getString("ZONE"))
                    }
                    val zoneAdapter = ArrayAdapter(applicationContext, R.layout.custom_spinner, zoneList!!)
                    zoneAdapter.setDropDownViewResource(R.layout.zone_drop_down)
                    zoneSpinner!!.adapter = zoneAdapter
                } else {
                    IposProgress.stop()
                    Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show()
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                IposProgress.stop()
            }
        }, com.android.volley.Response.ErrorListener {
            IposProgress.stop()
            Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["API"] = "ZONE"
                return map
            }
        }
        VolleyController.getInstance(this).addToRequestQueue(request)
    }

   private fun adminLogin(zoneName: String, password: String) {
        if (!Internet.isNetAvailable(this)) {
            Toast.makeText(this, R.string.internet, Toast.LENGTH_SHORT).show()
        } else {
            IposProgress.progressDialog(this).setMessage("Please wait...")
            val controller = RetrofitController(this)
            val call = controller.zoneWiseLogin("ZONE_LOGIN", zoneName, password)
            call.enqueue(object : Callback<ZoneModel.ZoneResponse> {
                override fun onResponse(call: Call<ZoneModel.ZoneResponse>, response: Response<ZoneModel.ZoneResponse>) {
                    if (response.isSuccessful) {
                        IposProgress.stop()
                        assert(response.body() != null)
                        if (response.body()!!.error == "0") {
                            IposConst.editor.putString(IposConst.Keys.ZONE_ID, response.body()!!.data.zoneId!!.toString())
                            IposConst.editor.putString(IposConst.Keys.ZONE_NAME, response.body()!!.data.name)
                            IposConst.editor.commit()
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        IposProgress.stop()
                        Toast.makeText(applicationContext, R.string.wrong, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ZoneModel.ZoneResponse>, t: Throwable) {
                    t.message?.let { Log.d("error", it) }
                    IposProgress.stop()
                    Toast.makeText(applicationContext, R.string.server_error, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun checkValid(){
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable) {
                if (password.text.toString().isEmpty()) {
                    login.isEnabled = false
                    login.alpha = 0.5f
                    login.text = "Enter password"
                    //Toast.makeText(BookingInvoiceActivity.this, "Write some feedback", Toast.LENGTH_SHORT).show();
                } else {
                    login.isEnabled = true
                    login.alpha = 1f
                    login.text = "Login"
                }
            }
        })
    }

}
