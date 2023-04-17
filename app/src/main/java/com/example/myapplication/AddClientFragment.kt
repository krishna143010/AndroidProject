package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class AddClientFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addClientButtonView= view.findViewById<Button>(R.id.addClientButtonView)
        val clientNameEntered=view.findViewById<EditText>(R.id.clientName)
        addClientButtonView.setOnClickListener(){
            println("clientNameEntered entered is:"+clientNameEntered.text.toString())
            val regex = Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]")
            if(!Regex("[A-Za-z]|[A-Za-z][A-Z a-z]*[A-Za-z]").matches(clientNameEntered.text.toString())){

                //println("Else Loop Should not be empy from Println")
                clientNameEntered.setError("Name invalid")
            }else{
                println("iF Loop Should not be empy from Println")
                val myDBHelper=DBAccessClass(this.requireContext())
                val insertStatus:Long=myDBHelper.insertClients(clientNameEntered.text.toString(),true,false,1)
                if(insertStatus>0){
                    Snackbar.make(it,"Client \""+clientNameEntered.text.toString()+"\" add Success",
                        Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(it,"Client \""+clientNameEntered.text.toString()+"\" add Failed",
                        Snackbar.LENGTH_LONG).show()
                }
            }
        }


    }
}