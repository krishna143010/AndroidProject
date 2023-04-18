package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EntryOrRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_or_register)
        val entryFragment=EntryToDashboardFragment()
        val addFMFragment=AddFundManagerFragment()
        val addClientFragment=AddClientFragment()
        val addAccountFragment=AddAccountFragment()
        val addTxnFragment=TransactionFragment()
        val rvFragment=RecentTransactionRVFragment()

        val fmButton=findViewById<Button>(R.id.button)
        val clientButton=findViewById<Button>(R.id.button2)
        val accountButton=findViewById<Button>(R.id.button3)
        val txnButton=findViewById<Button>(R.id.button4)
        val rvButton=findViewById<Button>(R.id.button5)

        fmButton.setOnClickListener() {

            val userTransaction=supportFragmentManager.beginTransaction()
            userTransaction.replace(R.id.layoutContainer, addFMFragment)
            userTransaction.addToBackStack(null)
            userTransaction.commit()
        }
        clientButton.setOnClickListener() {

            val userTransaction=supportFragmentManager.beginTransaction()
            userTransaction.replace(R.id.layoutContainer, addClientFragment)
            userTransaction.addToBackStack(null)
            userTransaction.commit()
        }
        accountButton.setOnClickListener() {

            val userTransaction=supportFragmentManager.beginTransaction()
            userTransaction.replace(R.id.layoutContainer, addAccountFragment)
            userTransaction.addToBackStack(null)
            userTransaction.commit()
        }
        txnButton.setOnClickListener() {

            val userTransaction=supportFragmentManager.beginTransaction()
            userTransaction.replace(R.id.layoutContainer, addTxnFragment)
            userTransaction.addToBackStack(null)
            userTransaction.commit()
        }
        rvButton.setOnClickListener() {

            val userTransaction=supportFragmentManager.beginTransaction()
            userTransaction.replace(R.id.layoutContainer, rvFragment)
            userTransaction.addToBackStack(null)
            userTransaction.commit()
        }

    }
}