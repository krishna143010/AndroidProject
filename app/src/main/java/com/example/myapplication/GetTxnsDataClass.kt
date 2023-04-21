package com.example.myapplication

import java.util.Date

data class GetTxnsDataClass(val transId:Int,
                            val dateOfTxn:Date,
                            val txnAmount:Long,
                            val remarks:String,
                            val fromClient:String,
                            val toClient:String,
                            val fromAccount:String,
                            val toAccount:String,
                            val fmId:Int?)
