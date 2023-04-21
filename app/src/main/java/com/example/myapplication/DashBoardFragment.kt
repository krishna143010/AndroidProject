package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class DashBoardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sessionFMID:Long?=null
        sessionFMID=arguments?.getLong("fmIdFromAct")
        val heading=view.findViewById<TextView>(R.id.dashboard)
        val noOfClientsTV=view.findViewById<TextView>(R.id.totalClients)
        val noOfAccountsTV=view.findViewById<TextView>(R.id.totalAccounts)
        val totalHoldingsTV=view.findViewById<TextView>(R.id.totalHoldings)
        val dbAccess=DBAccessClass(this.requireContext())
        val fmDetails:FundManagerEntity?=    dbAccess.getFmDetails(sessionFMID?.toInt())
        if (fmDetails != null) {
            heading.text="Hello "+fmDetails.fmName+"!"
        }

        val netList:MutableList<NameInOutDataClass> = dbAccess.getNetBals(sessionFMID?.toInt())
        for(netItem in netList){
            if(netItem.name=="External"){
                totalHoldingsTV.text="Total Holdings: ₹ "+(netItem.outMoney-netItem.inMoney)
            }
        }
        val noOfAccounts:Int = dbAccess.getNoOfAccounts(sessionFMID?.toInt())
        val noOfClients:Int = dbAccess.getNoOfClients(sessionFMID?.toInt())
        noOfClientsTV.text="No of Clients: "+noOfClients.toString()
        noOfAccountsTV.text="No of Accounts:"+noOfAccounts.toString()


        //fmIDTV.text=sessionFMID.toString()
       // println("Args in Frag:"+arguments?.getString("sampleTest") +" fmIdFromAct"+arguments?.getLong("fmID").toString())
    }

}