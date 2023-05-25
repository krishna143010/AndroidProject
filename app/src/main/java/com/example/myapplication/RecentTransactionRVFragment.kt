package com.example.myapplication

import CustomAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//Krushna Chinthada
class RecentTransactionRVFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_transaction_r_v, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sessionFMID:Long?=null
        sessionFMID=arguments?.getLong("fmIdFromAct")

        // getting the recyclerview by its id

        val sortSpinner: Spinner = view.findViewById(R.id.sortBySpinnerView)
        val filterSpinner: Spinner = view.findViewById(R.id.filterSpinnerView)
        val dbAccessClass=DBAccessClass(this.requireContext())
        val clientsList:MutableList<NameAndId> = dbAccessClass.getClientNames(sessionFMID?.toInt())
        clientsList.add(0, NameAndId("All",""))
        val adapterForClients = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item,clientsList.map { it.definition } /*filterArray.toArray()*/)
        filterSpinner.adapter=adapterForClients
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.sort_criteria,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sortSpinner.adapter = adapter
        }
        //var clientIDSelected: Int?=null;
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                displayRV(view,sessionFMID,filterSpinner.selectedItem.toString(),sortSpinner.selectedItem.toString());
            }

            override fun onItemSelected(parent: AdapterView<*>?, viewL: View?, position: Int, id: Long) {
                displayRV(view,sessionFMID,filterSpinner.selectedItem.toString(),sortSpinner.selectedItem.toString());
            }

        }

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //clientIDSelected=null
                displayRV(view,sessionFMID,filterSpinner.selectedItem.toString(),sortSpinner.selectedItem.toString());
            }

            override fun onItemSelected(parent: AdapterView<*>?, viewL: View?, position: Int, id: Long) {
                //clientIDSelected= clientsList[position].id.toIntOrNull()
                displayRV(view,sessionFMID,filterSpinner.selectedItem.toString(),sortSpinner.selectedItem.toString());
            }

        }
        displayRV(view,sessionFMID,"All",sortSpinner.selectedItem.toString());

    }

    private fun displayRV(view:View,sesID:Long?,filterOption:String,sortOption:String) {
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        val myDBHelper=DBAccessClass(this.requireContext())
        val txnList:MutableList<GetTxnsDataClass> = myDBHelper.getTransactions(sesID?.toInt(),filterOption,sortOption)
        for (item in txnList) {
            println("FM ID"+item.fmId)
        }
        val adapter = CustomAdapter(txnList) //adapter
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}