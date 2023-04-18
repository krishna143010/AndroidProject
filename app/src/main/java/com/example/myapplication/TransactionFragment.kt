package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
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

        val dbAccessClass=DBAccessClass(this.requireContext())
        val clientsList:MutableList<NameAndId> = dbAccessClass.getClientNames()
        val accountsList:MutableList<NameAndId> = dbAccessClass.getAccountNames()

        val fromClient=view.findViewById<AutoCompleteTextView>(R.id.fromClientId)
        val toClient=view.findViewById<AutoCompleteTextView>(R.id.toClientId)
        val fromAccount=view.findViewById<AutoCompleteTextView>(R.id.fromAccountId)
        val toAccount=view.findViewById<AutoCompleteTextView>(R.id.toAccountId)

        val adapterForClientName = CustomFilterAdapter(this.requireContext(),android.R.layout.select_dialog_singlechoice,clientsList)
        val adapterForAccountName = CustomFilterAdapter(this.requireContext(),android.R.layout.select_dialog_singlechoice,accountsList)
        fromClient.setAdapter(adapterForClientName)
        toClient.setAdapter(adapterForClientName)
        fromAccount.setAdapter(adapterForAccountName)
        toAccount.setAdapter(adapterForAccountName)

        var fromClientId: Long? =null
        var toClientId: Long? =null
        var fromAccountId: Long? =null
        var toAccountId: Long? =null

        fromClient.setOnItemClickListener { parent, view, position, id -> (
                run {
                    fromClientId=id
                    println("Client Id is $fromClientId")
                }
        )}
        toClient.setOnItemClickListener { parent, view, position, id -> (
            run {
                toClientId=id
                println("Client Id is $toClient")
            }
        )}
        fromAccount.setOnItemClickListener { parent, view, position, id -> (
                run {
                    fromAccountId=id
                    println("Client Id is $fromAccountId")
                }
                )}
        toAccount.setOnItemClickListener { parent, view, position, id -> (
                run {
                    toAccountId=id
                    println("Client Id is $toAccountId")
                }
                )}

        val txnDate=view.findViewById<TextView>(R.id.editTxnDate)
        val remarks=view.findViewById<EditText>(R.id.remarks)
        val subTxn=view.findViewById<Button>(R.id.submitTxn)
        val dateSelectButton=view.findViewById<Button>(R.id.dateSelect)
        dateSelectButton.setOnClickListener(){
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(this.parentFragmentManager,"")

            datePicker.addOnPositiveButtonClickListener(){
                val dateSelected: Date= Date(datePicker.headerText)

                val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
                val dateTime = simpleDateFormat.format(dateSelected).toString()
                println("Clicked OK:"+dateTime)
                txnDate.setText(dateTime)

            }

        }



        subTxn.setOnClickListener(){
            if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(remarks.text.toString())){

                //println("Else Loop Should not be empy from Println")
                remarks.setError("No Space at Edges")
            }else if(!clientsList.contains(NameAndId(fromClient.text.toString(),fromClientId.toString()))){
                fromClient.setError("No Client with "+fromClient.text.toString()+" name available")
            }else if(!clientsList.contains(NameAndId(toClient.text.toString(),toClientId.toString()))){
                toClient.setError("No Client with "+toClient.text.toString()+" name available")
            }else if(!accountsList.contains(NameAndId(fromAccount.text.toString(),fromAccountId.toString()))){
                fromAccount.setError("No Client with "+fromAccount.text.toString()+" name available")
            }else if(!accountsList.contains(NameAndId(toAccount.text.toString(),toAccountId.toString()))){
                toAccount.setError("No Client with "+toAccount.text.toString()+" name available")
            }/*else if(!Regex("([0][1-9]|[1][0-2]+)/([0][1-9]|[1-2][0-9]|[3][0-1]+)/(202[0-9])").matches(txnDate.text.toString())){
                txnDate.setError("Date Invalid")
            }*/else{
                println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
                val insertStatus:Long=myDBHelper.insertTxn(remarks.text.toString(),txnDate.text.toString(),
                    toAccountId?.toInt(),fromAccountId?.toInt(),toClientId?.toInt(),fromClientId?.toInt())
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