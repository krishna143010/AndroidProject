package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.util.Date


class TransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromClientId=view.findViewById<EditText>(R.id.fromClientId)
        val toClientId=view.findViewById<EditText>(R.id.toClientId)
        val fromAccountId=view.findViewById<EditText>(R.id.fromAccountId)
        val toAccountId=view.findViewById<EditText>(R.id.toAccountId)
        val txnDate=view.findViewById<EditText>(R.id.editTxnDate)
        val remarks=view.findViewById<EditText>(R.id.remarks)
        val subTxn=view.findViewById<Button>(R.id.submitTxn)

        subTxn.setOnClickListener(){
            if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(remarks.text.toString())){

                //println("Else Loop Should not be empy from Println")
                remarks.setError("No Space at Edges")
            }else{
                println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
                val insertStatus:Long=myDBHelper.insertTxn(remarks.text.toString(),txnDate.text.toString(),4,4,1,1)
                if(insertStatus>0){
                    Snackbar.make(it,"Txn add Success",
                        Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(it,"Txn add Failed",
                        Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }


}