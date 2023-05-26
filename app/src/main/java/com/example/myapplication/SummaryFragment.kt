package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class SummaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sessionFMID:Long?=null
        sessionFMID=arguments?.getLong("fmIdFromAct")
        val dbAccess=DBAccessClass(this.requireContext())
        val totHoldingsTV=view.findViewById<TextView>(R.id.totalHoldings)
        val netList:MutableList<NameInOutDataClass> = dbAccess.getNetBals(sessionFMID?.toInt())
        val clientSummaryStringList:MutableList<String> = mutableListOf()
        val netAccountList:MutableList<NameInOutDataClass> = dbAccess.getNetBalsOfAccounts(sessionFMID?.toInt())
        val accountSummaryStringList:MutableList<String> = mutableListOf()
        /*val users = arrayOf(
            "Virat Kohli", "Rohit Sharma", "Steve Smith",
            "Kane Williamson", "Ross Taylor"
        )
        val arrayAdapter: ArrayAdapter<String>*/
        for(netItem in netList){
            //println("Net Balances  Name:"+netItem.name+" inMoney"+netItem.inMoney+" out Money"+netItem.outMoney)
            if(netItem.name=="External"){
                //If External it calculates for Net FM Aval Monay
                totHoldingsTV.text="Total Holdings as of now is: "+(netItem.outMoney-netItem.inMoney)
            }else{
                clientSummaryStringList.add(netItem.name+" Amount: ₹"+(netItem.inMoney-netItem.outMoney))
            }

        }
        val arrayAdapter: ArrayAdapter<*>
        // access the listView from xml file
        var mListView = view.findViewById<ListView>(R.id.clientSummary)
        arrayAdapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_list_item_1, clientSummaryStringList)
        mListView.adapter = arrayAdapter

        for(netItem in netAccountList){
            //println("Net Balances  Name:"+netItem.name+" inMoney"+netItem.inMoney+" out Money"+netItem.outMoney)
            if(netItem.name=="External"){
                //If External it calculates for Net FM Aval Monay
                //totHoldingsTV.text="Total Holdings as of now is: "+(netItem.outMoney-netItem.inMoney)
            }else{
                accountSummaryStringList.add(netItem.name+" Amount: ₹"+(netItem.inMoney-netItem.outMoney))
            }

        }
        val arrayAccountAdapter: ArrayAdapter<*>
        // access the listView from xml file
        var accountListView = view.findViewById<ListView>(R.id.accountSummary)
        arrayAccountAdapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_list_item_1, accountSummaryStringList)
        accountListView.adapter = arrayAccountAdapter

    }


}