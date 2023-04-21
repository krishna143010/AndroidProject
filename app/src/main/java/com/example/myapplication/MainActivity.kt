package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    val bundle=Bundle()
    var fmIDForTheSession:Long?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val incomingIntent=intent.extras?:null
        if (incomingIntent != null) {
            fmIDForTheSession = incomingIntent.getString("fmId")?.toLong()

            println("fmIDForTheSession assigned with"+fmIDForTheSession.toString())
            if (fmIDForTheSession != null) {
                bundle.putLong("fmIdFromAct", fmIDForTheSession!!)
            }
        }


        if(incomingIntent!=null){


            //fmIDForTheSession= incomingIntent.getStringExtra("fmId")?.toLong()
            if(incomingIntent.getInt("refreshRV")==1) {
                println("If loop")
                val rvFragment=RecentTransactionRVFragment()
                rvFragment.arguments=bundle
                val userTransaction=supportFragmentManager.beginTransaction()
                userTransaction.replace(R.id.mainactivitylayout, rvFragment)
                userTransaction.addToBackStack(null)
                userTransaction.commit()
            }else if(incomingIntent.getBoolean("editTxn")) {

                println("After Custum Adapter in Main Act fmID Recieved is"+incomingIntent.getString("fmId"))

                //val bundle=Bundle()
                /*bundle.putInt("editTxn", 1)
                bundle.putString("fromClient", ItemsViewModel.fromClient)
                bundle.putString("toClient", ItemsViewModel.toClient)
                bundle.putString("fromAccount", ItemsViewModel.fromAccount)
                bundle.putString("toAccount", ItemsViewModel.toAccount)*/
               // bundle.putString("remarks", "Test Remarks")
//                bundle.putString("txnDate", holder.dateOfTxn.text.toString())
//                bundle.putInt("txnId", ItemsViewModel.transId)

                val userTransaction=supportFragmentManager.beginTransaction()
                val frag=TransactionFragment()
                frag.arguments=incomingIntent
                userTransaction.replace(R.id.mainactivitylayout, frag)
                //userTransaction.addToBackStack(null)
                userTransaction.commit()
            }else{
                println("FM ID for this session:$fmIDForTheSession")

                val dashboardFragment = DashBoardFragment()

                dashboardFragment.arguments = bundle
                val userTransaction = supportFragmentManager.beginTransaction()
                userTransaction.replace(R.id.mainactivitylayout, dashboardFragment)
                userTransaction.addToBackStack(null)
                userTransaction.commit()
            }
        }else{
            println("Else loop")

        }


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNav.setOnNavigationItemSelectedListener(navListener)








    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val myInflater=menuInflater
        myInflater.inflate(R.menu.top_nav_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lateinit var selectedFragment: Fragment
        when(item.itemId){
            R.id.addClient -> {
                println("Add client clicked")
                selectedFragment=AddClientFragment()
            }
            R.id.addAccount->{
                 selectedFragment=AddAccountFragment()
            }

        }
        selectedFragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.mainactivitylayout, selectedFragment).commit()
        return true
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        // By using switch we can easily get the
        // selected fragment by using there id
        lateinit var selectedFragment: Fragment
        when (item.itemId) {
            R.id.homePage -> {
                selectedFragment = DashBoardFragment()
            }
            R.id.add -> {
                selectedFragment = TransactionFragment()
            }
            R.id.summary -> {
                selectedFragment = RecentTransactionRVFragment()
            }
            R.id.history->{
                selectedFragment = RecentTransactionRVFragment()
            }
            R.id.profile->{
                selectedFragment = ProfileFragment()
            }

        }
        // It will help to replace the
        // one fragment to other.
        selectedFragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.mainactivitylayout, selectedFragment).addToBackStack(null).commit()
        true
    }






}