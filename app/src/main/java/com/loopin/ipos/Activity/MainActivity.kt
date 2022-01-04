package com.loopin.ipos.Activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.loopin.ipos.Fragment.InfoFragment
import com.loopin.ipos.R
import com.loopin.ipos.Utils.AppUpdateAsync
import com.loopin.ipos.Utils.ExtraUtils.appUpdateMethod
import com.loopin.ipos.Utils.IposConst
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.shopAddress
import kotlinx.android.synthetic.main.activity_main.shopName

class MainActivity : AppCompatActivity() {

    private var twice = false
    private var twiceBack = false
    private var popupMenu: PopupMenu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, Context.MODE_PRIVATE)
        IposConst.editor = IposConst.sharedPreferences.edit()

        shopName.text = IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_NAME, "")

        shopAddress.text = (IposConst.sharedPreferences.getString(IposConst.Keys.SHOP_ADDRESS, "")
                + ", " + IposConst.sharedPreferences.getString(IposConst.Keys.PIN_CODE, ""))

        saleCardView.setOnClickListener {
            reSetBg("sale", 0, Color.WHITE)
            startActivity(Intent(applicationContext, SaleViewActivity::class.java))
        }
        inventoryCardView.setOnClickListener {
            reSetBg("inventory", 0, Color.WHITE)
            startActivity(Intent(applicationContext, InventoryViewActivity::class.java))
        }
        purchaseCardView.setOnClickListener {
            reSetBg("purchase", 0, Color.WHITE)
            startActivity(Intent(applicationContext, PurchaseViewActivity::class.java))
        }
        registerCardView.setOnClickListener {
            reSetBg("register", 0, Color.WHITE)
            Toast.makeText(applicationContext, "Working", Toast.LENGTH_SHORT).show()
            //startActivity(Intent(applicationContext, RegisterViewActivity::class.java))
        }
        dailyCardView.setOnClickListener {
            reSetBg("daily", 0, Color.WHITE)
            startActivity(Intent(applicationContext, DailyViewActivity::class.java))
        }
        plCardView.setOnClickListener {
            reSetBg("pl", 0, Color.WHITE)
            startActivity(Intent(applicationContext, PLViewActivity::class.java))
        }
        accCardView.setOnClickListener {
            Toast.makeText(applicationContext, "Working", Toast.LENGTH_SHORT).show()
        }

        val dialog = AlertDialog.Builder(this)
        dotMenu.setOnClickListener {
            popupMenu = PopupMenu(this, dotMenu)
            popupMenu!!.menuInflater.inflate(R.menu.pop_menu, popupMenu!!.menu)
            popupMenu!!.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.logout -> {

                        dialog.setTitle("Logout!")
                        dialog.setMessage("\nDo you want to logout IPOS?")
                        dialog.setIcon(R.drawable.ic_ipos_logout)
                        dialog.setPositiveButton("Logout"){dialogInterface , which ->
                            IposConst.editor.clear().apply()
                            finish()
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                        }.setNegativeButton("Cancel"){dialogInterface, i ->
                            dialogInterface.cancel()
                        }

                        val alertDialog: AlertDialog = dialog.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()

                        return@OnMenuItemClickListener true
                    }
                    //break;
                    R.id.exit -> {
                        dialog.setTitle("Exit!")
                        dialog.setMessage("\nDo you want to exit from IPOS?")
                        dialog.setIcon(R.drawable.ic_exit)
                        dialog.setPositiveButton("Exit"){dialogInterface , which ->
                            finish()
                        }.setNegativeButton("Cancel"){dialogInterface, i ->
                            dialogInterface.cancel()
                        }

                        val alertDialog: AlertDialog = dialog.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                        return@OnMenuItemClickListener true
                    }

                    R.id.info ->{
                        InfoFragment().show(supportFragmentManager.beginTransaction(), "Info")
                    }
                }
                //break;
                true
            })
            popupMenu!!.show()


        }

//        backFromStore.setOnClickListener {
//            if (twice){
//                finish()
//            }else{
//                twice=true
//                Toast.makeText(applicationContext, "Click again to Exit!",Toast.LENGTH_SHORT).show()
//                Handler(Looper.getMainLooper()).postDelayed(Runnable { twice = false }, 3000)
//            }
//        }


    }

    override fun onResume() {
        super.onResume()
        appUpdateMethod(this)
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        if (twiceBack){
            finish()
        }else{
            twiceBack=true
            Toast.makeText(applicationContext, "Click again to Exit!",Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed(Runnable { twiceBack = false }, 3000)
        }
    }

    public fun updateIpos() {
        val packageManager = applicationContext.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        val currentVersion = packageInfo!!.versionName
        AppUpdateAsync(currentVersion, applicationContext).execute()
    }

     private fun reSetBg(v:String, col:Int, empty:Int){
         saleCardView.setCardBackgroundColor(empty)
         inventoryCardView.setCardBackgroundColor(empty)
         purchaseCardView.setCardBackgroundColor(empty)
         registerCardView.setCardBackgroundColor(empty)
         dailyCardView.setCardBackgroundColor(empty)
         plCardView.setCardBackgroundColor(empty)
        when(v){
            "sale"->{
                saleCardView.setCardBackgroundColor(col)
            }
            "inventory"->{
                inventoryCardView.setCardBackgroundColor(col)
            }
            "purchase"->{
                purchaseCardView.setCardBackgroundColor(col)
            }
            "register"->{
                registerCardView.setCardBackgroundColor(col)
            }
            "daily"->{
                dailyCardView.setCardBackgroundColor(col)
            }
            "pl"->{
                plCardView.setCardBackgroundColor(col)
            }
        }

    }
}
