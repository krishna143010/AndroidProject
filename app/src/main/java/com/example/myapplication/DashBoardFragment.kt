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
        val dbAccess=DBAccessClass(this.requireContext())
        val fmDetails:FundManagerEntity?=    dbAccess.getFmDetails(sessionFMID?.toInt())
        if (fmDetails != null) {
            heading.text="Hello: "+fmDetails.fmName+"!"
        }

        val netList:MutableList<NameInOutDataClass> = dbAccess.getNetBals(sessionFMID?.toInt())
        for(netItem in netList){
            println("Net Balances  Name:"+netItem.name+" inMoney"+netItem.inMoney+" out Money"+netItem.outMoney)
        }


        //fmIDTV.text=sessionFMID.toString()
       // println("Args in Frag:"+arguments?.getString("sampleTest") +" fmIdFromAct"+arguments?.getLong("fmID").toString())
    }

}