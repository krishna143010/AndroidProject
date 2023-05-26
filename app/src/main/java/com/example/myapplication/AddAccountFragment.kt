package com.example.myapplication

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
//krushna chinthada
class AddAccountFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_account, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sessionFMID:Long?=null
        sessionFMID=arguments?.getLong("fmIdFromAct") //for FmID from Activity

        val dbAccessClass=DBAccessClass(this.requireContext()) //Inst DBClass to access Database
        ////get clients list
        val clientsList:MutableList<NameAndId> = dbAccessClass.getClientNames(sessionFMID?.toInt())

        val clientNameACTV: AutoCompleteTextView=view.findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteView)

        val adapterForClientName = CustomFilterAdapter(this.requireContext(),android.R.layout.select_dialog_singlechoice,clientsList)
        clientNameACTV.setAdapter(adapterForClientName)
        var clientId: Long? =null
        val addAccountButtonView= view.findViewById<Button>(R.id.addAccountButtonView)
        val accountNameEditTextView=view.findViewById<EditText>(R.id.accountNameEditTextView)
        val accountIdentifierEditTextView=view.findViewById<EditText>(R.id.accountIdentifierEditTextView)
        val preferredPaymentMethod=view.findViewById<EditText>(R.id.preferredPaymentMethodEditTextView)
        //spinner
        val spinner: Spinner = view.findViewById(R.id.accountCurrencyTypeSpinner)
        var currencyTypeSelected:String=""
        //supply adapter to spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.currency_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        //save client Id
        clientNameACTV.setOnItemClickListener { parent, view, position, id -> (
            run {
                clientId=id
                //println("Client Id is $clientId")
            }
        )}
        //on submit
        addAccountButtonView.setOnClickListener(){
            val spinnerErrorTV=view.findViewById<TextView>(R.id.spinnerError)
            spinnerErrorTV.text=""
            spinnerErrorTV.error=null
            println("Client Id is $clientId and Its validity is:"+clientsList.contains(NameAndId(clientNameACTV.text.toString(),clientId.toString())))
            currencyTypeSelected=spinner.selectedItem.toString()
           if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(accountNameEditTextView.text.toString())){

                //println("Else Loop Should not be empy from Println")
                accountNameEditTextView.setError("Account Name should Contain Only Alphabets and Space in between")
            }else if(!Regex("[0-9]*").matches(accountIdentifierEditTextView.text.toString())){
                accountIdentifierEditTextView.setError("Account Number Should Contain Only the digits")
            }else if(!Regex("[A-Za-z0-9@]*").matches(preferredPaymentMethod.text.toString())){
                preferredPaymentMethod.setError("Payment method invalid")
            }else if(!clientsList.contains(NameAndId(clientNameACTV.text.toString(),clientId.toString()))){
                clientNameACTV.setError("No Client with "+clientNameACTV.text.toString()+" name available")
            }else if(currencyTypeSelected!="INR"){


                spinnerErrorTV.error = "Only INR is allowed for Currency Type"
                spinnerErrorTV.text="Only INR is allowed for Currency Type"
            }else{
                //println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
               //insert account
                val insertStatus:Long=myDBHelper.insertAccounts(accountNameEditTextView.text.toString(),accountIdentifierEditTextView.text.toString(),preferredPaymentMethod.text.toString(),true, currencyTypeSelected.toString(),clientId.toString().toInt(),sessionFMID?.toInt())
                if(insertStatus>0){
                    Snackbar.make(it,"Account \""+accountNameEditTextView.text.toString()+"\" add Success",
                        Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(it,"Account \""+accountNameEditTextView.text.toString()+"\" already Exists",
                        Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }

    /*private fun checkClientExistance(toString: String):Long{

    }*/

}