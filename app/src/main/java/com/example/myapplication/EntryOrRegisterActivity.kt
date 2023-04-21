package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
class EntryOrRegisterActivity : AppCompatActivity(),CallMainActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_or_register)

        /*val entryToDashboardFragment=EntryToDashboardFragment()
        val userTransaction=supportFragmentManager.beginTransaction()
        userTransaction.replace(R.id.fragmentContainerView, entryToDashboardFragment)
        userTransaction.addToBackStack(null)
        userTransaction.commit()*/

        val incomingIntentExtras=intent.extras?:null
        /*val entryFragment=EntryToDashboardFragment()
        val addFMFragment=AddFundManagerFragment()
        val addClientFragment=AddClientFragment()
        val addAccountFragment=AddAccountFragment()*/
        val rvFragment=RecentTransactionRVFragment()
        /*val fmButton=findViewById<Button>(R.id.button)
        val clientButton=findViewById<Button>(R.id.button2)
        val accountButton=findViewById<Button>(R.id.button3)
        val txnButton=findViewById<Button>(R.id.button4)
        val rvButton=findViewById<Button>(R.id.button5)*/
        if(incomingIntentExtras!=null){
            if(incomingIntentExtras.getInt("refreshRV")==1) {
                println("If loop")
                val userTransaction=supportFragmentManager.beginTransaction()
                userTransaction.replace(R.id.layoutContainer, rvFragment)
                userTransaction.addToBackStack(null)
                userTransaction.commit()
            }else if(incomingIntentExtras.getBoolean("editTxn")) {

                val bundle=Bundle()
                /*bundle.putInt("editTxn", 1)
                bundle.putString("fromClient", ItemsViewModel.fromClient)
                bundle.putString("toClient", ItemsViewModel.toClient)
                bundle.putString("fromAccount", ItemsViewModel.fromAccount)
                bundle.putString("toAccount", ItemsViewModel.toAccount)*/
                bundle.putString("remarks", "Test Remarks")
//                bundle.putString("txnDate", holder.dateOfTxn.text.toString())
//                bundle.putInt("txnId", ItemsViewModel.transId)

                val userTransaction=supportFragmentManager.beginTransaction()
                val frag=TransactionFragment()
                frag.arguments=incomingIntentExtras
                userTransaction.replace(R.id.layoutContainer, frag)
                //userTransaction.addToBackStack(null)
                userTransaction.commit()
            }
        }else{
            println("Else loop")
        }
    }

    override fun callMainActivity(fmId:Long) {
        //println("fmId"+fmId)
        val explIntent=Intent(this,MainActivity::class.java)
        explIntent.putExtra("fmId",fmId.toString())
        startActivity(explIntent)
    }
}