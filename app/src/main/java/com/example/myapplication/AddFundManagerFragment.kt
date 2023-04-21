package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

class AddFundManagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_fund_manager, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addFundManagerButtonView= view.findViewById<Button>(R.id.addFundManagerButtonView)
        val fmNameEntered=view.findViewById<EditText>(R.id.fmNameTextView)
        val emailEntered=view.findViewById<EditText>(R.id.emailEditTextView)
        val phoneEntered=view.findViewById<EditText>(R.id.phoneEditTextView)
        val gotoLogin=view.findViewById<ImageButton>(R.id.gotoLogin)

        gotoLogin.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_addFundManagerFragment_to_entryToDashboardFragment)

        }
        addFundManagerButtonView.setOnClickListener(){
            println("fmNameEntered entered is:"+fmNameEntered.text.toString())
            val regex = Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]")
            if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(fmNameEntered.text.toString())){

                //println("Else Loop Should not be empy from Println")
                fmNameEntered.setError("Name invalid")
            }else if(!Regex("^[a-zA-Z0-9.!#\$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$").matches(emailEntered.text.toString())){
                emailEntered.setError("Supply Valid Email Address")
            }else if(!Regex("^(\\+\\d{1,3}[- ]?)?\\d{10}\$").matches(phoneEntered.text.toString())){
                phoneEntered.setError("+xx xxxxxxxxxx")
            }else{
                println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
                val insertStatus:Long=myDBHelper.insertFundManager(fmNameEntered.text.toString(),emailEntered.text.toString(),phoneEntered.text.toString(),true,false)
                if(insertStatus>0){
                    Snackbar.make(it,"Fund Manage \""+fmNameEntered.text.toString()+"\" add Success",Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(it,"Fund Manage \""+fmNameEntered.text.toString()+"\" add Failed",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}