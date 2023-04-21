package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

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
        val fmNameEntered=view.findViewById<AutoCompleteTextView>(R.id.fmNameEditTextView)
        val dbAccessClass=DBAccessClass(this.requireContext())
        val fmList:MutableList<NameAndId> = dbAccessClass.getFMNames()
        val adapterForClientName = CustomFilterAdapter(this.requireContext(),android.R.layout.select_dialog_singlechoice,fmList)
        fmNameEntered.setAdapter(adapterForClientName)
        var fmId: Long? =null
        val searchButton=view.findViewById<Button>(R.id.searchFMButtonView)
        val callAddFMFrag=view.findViewById<TextView>(R.id.callRegisterFragmentLabelView)
        fmNameEntered.setOnItemClickListener { parent, view, position, id -> (
                run {
                    fmId=id
                    println("fmId Id is $fmId")
                }
                )}
        searchButton.setOnClickListener(){
            println("Clicked on enter Here")
            if(!fmList.contains(NameAndId(fmNameEntered.text.toString(),fmId.toString()))){
                fmNameEntered.setError("No Fund Manager with "+fmNameEntered.text.toString()+" name available")
            }else{
                    val activityInterface=activity as CallMainActivity
                fmId?.let { it1 -> activityInterface.callMainActivity(it1) }
            }
        }
        callAddFMFrag.setOnClickListener(){
            println("Clicked on add Here")
            //Toast.makeText(it.context,"Calling Add F<M Fragment",Toast.LENGTH_LONG)
            //val addFM=AddFundManagerFragment()
            Navigation.findNavController(view).navigate(R.id.action_entryToDashboardFragment_to_addFundManagerFragment)

        }
    }
}