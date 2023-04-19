package com.example.myapplication

import java.util.Date

data class GetTxnsToRV(val transId:Int,
                       val dateOfTxn:String,
                       val remarks:String,
                       val fromClient:String,
                       val toClient:String,
                       val fromAccount:String,
                       val toAccount:String)
