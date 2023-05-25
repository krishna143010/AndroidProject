package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.zip.Inflater
//krushna chinthada 700742199
class MainActivity : AppCompatActivity() {

    //created bundle for sending fmid AS AR ARGUMENT TO FRAGMENT TO HOLD THE FM Id
    val bundle=Bundle()
    var fmIDForTheSession:Long?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //incoming intents

        val incomingIntent=intent.extras?:null
        if (incomingIntent != null) {
            //checks for existance of intents
            fmIDForTheSession = incomingIntent.getString("fmId")?.toLong()

            //println("fmIDForTheSession assigned with"+fmIDForTheSession.toString())
            //checks for fmID session exustance
            if (fmIDForTheSession != null) {
                bundle.putLong("fmIdFromAct", fmIDForTheSession!!)
                //assign to bundle
            }
        }


        if(incomingIntent!=null){


            //if intent is coming from custumAdapter and asking to refresh the RV after delete
            if(incomingIntent.getInt("refreshRV")==1) {
                //println("If loop")
                //instantiate RV Frag
                val rvFragment=RecentTransactionRVFragment()
                //supply args
                rvFragment.arguments=bundle
                val userTransaction=supportFragmentManager.beginTransaction()
                userTransaction.replace(R.id.mainactivitylayout, rvFragment)
                userTransaction.addToBackStack(null)
                userTransaction.commit() //commit
            }else if(incomingIntent.getBoolean("editTxn")) {
                //in case of edit txn
                val userTransaction=supportFragmentManager.beginTransaction()
                val frag=TransactionFragment()
                //instantiate frag
                frag.arguments=incomingIntent //supply args
                userTransaction.replace(R.id.mainactivitylayout, frag)
                //commit
                userTransaction.commit()
            }else{
                //println("FM ID for this session:$fmIDForTheSession")
                //if no refresh or edit reciedved just display the home frag

                val dashboardFragment = DashBoardFragment() //inst frag

                dashboardFragment.arguments = bundle //supply args
                val userTransaction = supportFragmentManager.beginTransaction()
                userTransaction.replace(R.id.mainactivitylayout, dashboardFragment)
                userTransaction.addToBackStack(null)
                userTransaction.commit() //commit
            }
        }else{
            println("Else loop")

        }
        //bottom nav insta
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNav.setOnNavigationItemSelectedListener(navListener) //when clicked supply the navListner

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val myInflater=menuInflater
        myInflater.inflate(R.menu.top_nav_bar,menu) //inflate top navs
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //On One of top option clicked
        lateinit var selectedFragment: Fragment //Created common Frag Var
        //switch to corresponding item
        when(item.itemId){
            R.id.addClient -> {
                //println("Add client clicked")
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
                selectedFragment = SummaryFragment()
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