package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class EntryToDashboardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_to_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fmNameEntered=view.findViewById<EditText>(R.id.fmNameTextView)
        val searchButton=view.findViewById<Button>(R.id.searchFMButtonView)
        val callAddFMFrag=view.findViewById<TextView>(R.id.callRegisterFragmentLabelView)
        searchButton.setOnClickListener(){

        }
        callAddFMFrag.setOnClickListener(){
            println("Clicked on Click Here")
            //Toast.makeText(it.context,"Calling Add F<M Fragment",Toast.LENGTH_LONG)
            val addFM=AddFundManagerFragment()

        }
    }
}