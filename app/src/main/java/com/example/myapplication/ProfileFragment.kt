package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sessionFMID:Long?=null
        sessionFMID=arguments?.getLong("fmIdFromAct")

        val exitButton=view.findViewById<Button>(R.id.exit)

        exitButton.setOnClickListener(){
            println("Exiting")
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle("Exit")
            builder.setMessage("Are you sure to go out of your Dash Board?")
            builder.setPositiveButton("OK") { dialog, which ->
                run{
                    println("Updating txn")
                    val explIntent= Intent(it.context,EntryOrRegisterActivity::class.java)
                    startActivity(explIntent)
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->

            }
            builder.show()

        }
    }

}