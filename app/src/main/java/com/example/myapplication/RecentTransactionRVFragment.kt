package com.example.myapplication

import CustomAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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


        // getting the recyclerview by its id
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        val myDBHelper=DBAccessClass(this.requireContext())
        val txnList:MutableList<GetTxnsDataClass> = myDBHelper.getTransactions()
        val adapter = CustomAdapter(txnList)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        fun refreshRV() {
            // getting the recyclerview by its id
            val recyclerview = requireView().findViewById<RecyclerView>(R.id.recyclerview)
            // this creates a vertical layout Manager
            recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
            val myDBHelper=DBAccessClass(this.requireContext())
            val txnList:MutableList<GetTxnsDataClass> = myDBHelper.getTransactions()
            val adapter = CustomAdapter(txnList)
            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
        }
    }
}