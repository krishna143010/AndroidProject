package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

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
        val cityList = mutableListOf(
            Cities("London", "1"),
            Cities("Miami", "2"),
            Cities("California", "3"),
            Cities("Los Angeles", "4"),
            Cities("Chicago", "5"),
            Cities("Houston", "6")
        )
        val clientNameACTV: AutoCompleteTextView=view.findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteView)
        val adapterForClientName = CustomFilterAdapter(this.requireContext(),android.R.layout.select_dialog_singlechoice,cityList)
        var clientId: Long? =null
        clientNameACTV.setOnItemClickListener { parent, view, position, id -> (
            run {
                println("ID is $id")
                clientId=id
            }
        )}

        val languages = resources.getStringArray(R.array.Languages)

        //val arrayAdapter=GetSearchAdapter(this.requireContext(),R.layout.fragment_add_account,android.R.layout.simple_list_item_1,arr)
        clientNameACTV.setAdapter(adapterForClientName)

        val addAccountButtonView= view.findViewById<Button>(R.id.addAccountButtonView)
        val clientNameEditTextView=view.findViewById<EditText>(R.id.clientNameEditTextView)
        val accountNameEditTextView=view.findViewById<EditText>(R.id.accountNameEditTextView)
        val accountIdentifierEditTextView=view.findViewById<EditText>(R.id.accountIdentifierEditTextView)
        val preferredPaymentMethod=view.findViewById<EditText>(R.id.preferredPaymentMethodEditTextView)
        val spinner: Spinner = view.findViewById(R.id.accountCurrencyTypeSpinner)
        var currencyTypeSelected:String=""

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.currency_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        addAccountButtonView.setOnClickListener(){
            currencyTypeSelected=spinner.selectedItem.toString()
            println("Currency Selected "+currencyTypeSelected)
            println("Account Name entered is:"+clientNameEditTextView.text.toString())
            if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(clientNameEditTextView.text.toString())){

                //println("Else Loop Should not be empy from Println")
                clientNameEditTextView.setError("Name invalid")
            }else if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(accountNameEditTextView.text.toString())){

                //println("Else Loop Should not be empy from Println")
                accountNameEditTextView.setError("Account Name invalid")
            }else if(!Regex("[0-9]*").matches(accountIdentifierEditTextView.text.toString())){

                //println("Else Loop Should not be empy from Println")
                accountIdentifierEditTextView.setError("Name invalid")
            }else if(!Regex("[A-Za-z0-9@]*").matches(preferredPaymentMethod.text.toString())){

                //println("Else Loop Should not be empy from Println")
                preferredPaymentMethod.setError("UPI invalid")
            }else{
                println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
                val insertStatus:Long=myDBHelper.insertAccounts(accountNameEditTextView.text.toString(),accountIdentifierEditTextView.text.toString(),preferredPaymentMethod.text.toString(),true, currencyTypeSelected.toString(),1)
                if(insertStatus>0){
                    Snackbar.make(it,"Account \""+accountNameEditTextView.text.toString()+"\" add Success",
                        Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(it,"Account \""+accountNameEditTextView.text.toString()+"\" add Failed",
                        Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }

}