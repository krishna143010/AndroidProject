package com.example.myapplication

import android.app.AlertDialog
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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
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
        var sessionFMID:Long?=null
        sessionFMID=arguments?.getLong("fmIdFromAct")

        var editTxn:Boolean=false


        val dbAccessClass=DBAccessClass(this.requireContext())
        val subTxn=view.findViewById<Button>(R.id.submitTxn)
        val dateSelectButton=view.findViewById<Button>(R.id.dateSelect)
        editTxn= arguments?.getBoolean("editTxn") ?: false

        if(editTxn){
            subTxn.text="Update Transaction"
            sessionFMID= arguments?.getString("fmId")?.toLong()
        }else{
            subTxn.text="Save Transaction"
            sessionFMID=arguments?.getLong("fmIdFromAct")
        }
        val clientsList:MutableList<NameAndId> = dbAccessClass.getClientNames(sessionFMID?.toInt())
        val accountsList:MutableList<NameAndId> = dbAccessClass.getAccountNames(sessionFMID?.toInt())
        val fromClient=view.findViewById<AutoCompleteTextView>(R.id.fromClientId)
        val toClient=view.findViewById<AutoCompleteTextView>(R.id.toClientId)
        val fromAccount=view.findViewById<AutoCompleteTextView>(R.id.fromAccountId)
        val toAccount=view.findViewById<AutoCompleteTextView>(R.id.toAccountId)
        val txnDate=view.findViewById<TextView>(R.id.editTxnDate)
        val remarks=view.findViewById<EditText>(R.id.remarks)
        val txnAmount=view.findViewById<EditText>(R.id.txnAmount)

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
        var txnId:Long?=null
        var txnAmountVar:Long?=null



        val message= arguments?.getString("remarks")
        fromClient.setText(arguments?.getString("fromClient"))
        toClient.setText(arguments?.getString("toClient"))
        fromAccount.setText(arguments?.getString("fromAccount"))
        toAccount.setText(arguments?.getString("toAccount"))
        remarks.setText(arguments?.getString("remarks"))
        if(arguments?.getLong("txnAmount")!=null) {
            txnAmountVar = arguments?.getLong("txnAmount")?.toLong()

            txnAmount.setText(txnAmountVar.toString())
        }else{
            txnAmount.setText("")
        }
        if(arguments?.getString("remarks")==null) {
            remarks.setText("")
        }
        txnDate.text = arguments?.getString("txnDate")
        txnId= arguments?.getInt("txnId")?.toLong()

        println("Edit txn status recieved is:"+arguments?.getBoolean("editTxn") ?: false)

        println("txn Id:$txnId")
        println("fm Id:$sessionFMID")

        //remarks.setText(message)

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

        dateSelectButton.setOnClickListener(){
            val calendar = Calendar.getInstance()
            val constraints: CalendarConstraints =CalendarConstraints.Builder().setStart(calendar.timeInMillis)
                    .setValidator(DateValidatorPointBackward.now()).build()
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraints)
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

        fun findIdFromName(givenNameIdList:List<NameAndId>,namegiven:String):Long?{
            for (element in givenNameIdList) {
                println(element)
                if(element.definition==namegiven)
                    return element.id.toLong()
            }
            return null
        }



        subTxn.setOnClickListener(){
            fromClientId=findIdFromName(clientsList,fromClient.text.toString())
            toClientId=findIdFromName(clientsList,toClient.text.toString())
            fromAccountId=findIdFromName(accountsList,fromAccount.text.toString())
            toAccountId=findIdFromName(accountsList,toAccount.text.toString())
            if(!clientsList.contains(NameAndId(fromClient.text.toString(),fromClientId.toString()))){
                fromClient.setError("No Client with "+fromClient.text.toString()+" name available")
            }else if(!clientsList.contains(NameAndId(toClient.text.toString(),toClientId.toString()))){
                toClient.setError("No Client with "+toClient.text.toString()+" name available")
            }else if(!accountsList.contains(NameAndId(fromAccount.text.toString(),fromAccountId.toString()))){
                fromAccount.setError("No Client with "+fromAccount.text.toString()+" name available")
            }else if(!accountsList.contains(NameAndId(toAccount.text.toString(),toAccountId.toString()))){
                toAccount.setError("No Client with "+toAccount.text.toString()+" name available")
            }else if(!Regex("([0][1-9]|[1][0-2])/([0][1-9]|[1-2][0-9]|[3][0-1])/(202[0-9])").matches(txnDate.text.toString())){
                txnDate.setError("Date Invalid")
            }else if(!Regex("^[1-9]\\d*(\\.\\d+)?\$").matches(txnAmount.text.toString())){
                txnDate.error = null
                txnAmount.setError("Amount invalid")
            }else if(!Regex("[A-Za-z\\d]|[A-Za-z\\d][A-Z .a-z\\d]*[A-Za-z0-9.\\d]").matches(remarks.text.toString())){
                //println("Else Loop Should not be empy from Println")
                remarks.setError("Only A-Za-z 0-9. allowed")
            }else{
                println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
                if(!editTxn){
                    println("Inserting new txn")
                    val insertStatus:Long = myDBHelper.insertTxn(remarks.text.toString(),txnDate.text.toString(),
                        toAccountId?.toInt(),fromAccountId?.toInt(),toClientId?.toInt(),fromClientId?.toInt(),txnAmount.text.toString().toLong())
                    if(insertStatus>0){
                        Snackbar.make(it,"Txn add Success",
                            Snackbar.LENGTH_LONG).show()
                    }else{
                        Snackbar.make(it,"Txn add Failed",
                            Snackbar.LENGTH_LONG).show()
                    }
                }else{
                    val builder = AlertDialog.Builder(this.requireContext())
                    builder.setTitle("Updating of Txn Id:$txnId")
                    builder.setMessage("Are you sure to Update the Txn?")
                    builder.setPositiveButton("OK") { dialog, which ->
                        run{
                            println("Updating txn")
                            val insertStatus:Long = myDBHelper.updateTxn(txnId.toString(),remarks.text.toString(),txnDate.text.toString(),
                                toAccountId?.toInt(),fromAccountId?.toInt(),toClientId?.toInt(),fromClientId?.toInt(),txnAmount.text.toString().toLong())
                            if(insertStatus>0){
                                Snackbar.make(it,"Txn Update Success",
                                    Snackbar.LENGTH_LONG).show()
                            }else{
                                Snackbar.make(it,"Txn Update Failed",
                                    Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                    builder.setNegativeButton("Cancel") { dialog, which ->

                    }
                    builder.show()
                }


            }
        }

    }


}