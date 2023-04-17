package com.example.myapplication
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.Date
import kotlin.properties. Delegates
class DBAccessClass(context: Context): SQLiteOpenHelper(context,"FundManagerDB",null,1) {
    override fun onCreate(myDB: SQLiteDatabase?) {
        println("onCreate Before FundManagerAccounts table creating")
        if (myDB != null) { //myDB already exists
            val fmtExists: Unit? = myDB?.execSQL("SELECT name FROM sqlite_master WHERE type='table' AND name = 'FundManagerAccounts'")
            if (fmtExists != null) {
                println("FundManagerAccounts table creating")
                //create table if it does not exist
                myDB.execSQL ("create table FundManagerAccounts (fmID INTEGER primary key AutoIncrement, fmName text UNIQUE NOT NULL,mobileNumber text,email text, activeStatus boolean, deleteStatus boolean,timestamp  DATETIME DEFAULT CURRENT_TIMESTAMP) ")
            }
            val clientExists: Unit? = myDB?.execSQL("SELECT name FROM sqlite_master WHERE type='table' AND name = 'Clients'")
            if (clientExists != null) {
                //create table if it does not exist
                myDB.execSQL ("create table Clients (clientID INTEGER primary key AutoIncrement, clientName text, activeStatus boolean, deleteStatus boolean,timestamp  DATETIME DEFAULT CURRENT_TIMESTAMP,fmIdAssociated INTEGER ,UNIQUE(fmIdAssociated, clientName) ON CONFLICT REPLACE,FOREIGN KEY(fmIdAssociated) REFERENCES FundManagerAccounts(fmID)) ")
            }
            val actExists: Unit? = myDB?.execSQL("SELECT name FROM sqlite_master WHERE type='table' AND name = 'Accounts'")
            if (actExists != null) {
                //create table if it does not exist
                myDB.execSQL ("create table Accounts (accountID INTEGER primary key AutoIncrement, accountNumber text, upiId text UNIQUE,activeStatus boolean,currencyType text, accountName text,timestamp  DATETIME DEFAULT CURRENT_TIMESTAMP,clientIDAssociated INTEGER ,UNIQUE(accountName, clientIDAssociated) ON CONFLICT REPLACE,FOREIGN KEY(clientIDAssociated) REFERENCES Clients(clientID)) ")
            }
            val txsExists: Unit? = myDB?.execSQL("SELECT name FROM sqlite_master WHERE type='table' AND name = 'TransactionsTable'")
            if (txsExists != null) {
                //create table if it does not exist
                //myDB.execSQL ("create table TransactionsTable (transId INTEGER primary key AutoIncrement, dateOfTxn date, remarks text,timestamp  DATETIME DEFAULT CURRENT_TIMESTAMP ,toAccountId INTEGER ,FOREIGN KEY(toAccountId) REFERENCES Accounts(accountID),FOREIGN KEY(toAccountId) REFERENCES Accounts(accountID)   ) ")
                myDB.execSQL ("create table TransactionsTable (transId INTEGER primary key AutoIncrement, dateOfTxn date,remarks text,timestamp  DATETIME DEFAULT CURRENT_TIMESTAMP,toAccountId INTEGER,fromAccountId INTEGER,toClientId INTEGER,fromClientId INTEGER,FOREIGN KEY(toAccountId) REFERENCES Accounts(accountID) ,FOREIGN KEY(fromAccountId) REFERENCES Accounts(accountID),FOREIGN KEY(toClientId) REFERENCES Clients(clientID),FOREIGN KEY(fromClientId) REFERENCES Clients(clientID)) ")
            }
        }//dbNull check
    }
    override fun onUpgrade(myDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        myDB!!.execSQL("drop table if exists FundManagerAccounts")
        myDB!!.execSQL("drop table if exists Clients")
        myDB!!.execSQL("drop table if exists Accounts")
        myDB!!.execSQL("drop table if exists TransactionsTable")
    }
    fun insertFundManager (fmName:String?,email:String?,mobileNumber:String?, activeStatus: Boolean?, deleteStatus: Boolean?): Long{
        println("Data Inserted into the table")
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("fmName", fmName)
        contentValues.put ("email", email)
        contentValues.put ("mobileNumber", mobileNumber)
        contentValues.put ("activeStatus", activeStatus)
        contentValues.put ("deleteStatus", deleteStatus)
        val result = myDB. insert ("FundManagerAccounts", null,contentValues) //nullcolumnhack is: when you want to instert an empty row except for the auto generated id, you will need nullcolumnHack, when content values will be null.
        //result will be -1 if the insert fails. The    datatype returned by the DB upon insert is a long: our return type is also long. return the result.
            return result
    }//Inserting data function
    fun insertClients (clientName:String?, activeStatus: Boolean?, deleteStatus: Boolean?, fmIdAssociated: Int?): Long{
        println("Data Inserted into the table")
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("clientName", clientName)
        contentValues.put ("activeStatus", activeStatus)
        contentValues.put ("deleteStatus", deleteStatus)
        contentValues.put ("fmIdAssociated", fmIdAssociated)
        val result = myDB. insert ("Clients", null,contentValues) //nullcolumnhack is: when you want to instert an empty row except for the auto generated id, you will need nullcolumnHack, when content values will be null.
        //result will be -1 if the insert fails. The    datatype returned by the DB upon insert is a long: our return type is also long. return the result.
        return result
    }//Inserting data function
    fun insertAccounts (accountName:String?,accountNumber:String?, upiId: String?, status: Boolean?, currencyType: String?, clientIDAssociated: Int?): Long{
        println("Data Inserted into the table")
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("accountName", accountName)
        contentValues.put ("accountNumber", accountNumber)
        contentValues.put ("upiId", upiId)
        contentValues.put ("status", status)
        contentValues.put ("currencyType", currencyType)
        contentValues.put ("clientIDAssociated", clientIDAssociated)
        val result = myDB. insert ("Accounts", null,contentValues) //nullcolumnhack is: when you want to instert an empty row except for the auto generated id, you will need nullcolumnHack, when content values will be null.
        //result will be -1 if the insert fails. The    datatype returned by the DB upon insert is a long: our return type is also long. return the result.
        return result
    }//Inserting data function
    fun insertTxn (remarks:String?,dateOfTxn:String?, toAccountId: Int?, fromAccountId: Int?, toClientId: Int?, fromClientId: Int?): Long{
        println("Data Inserted into the table")
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("dateOfTxn", dateOfTxn)
        contentValues.put ("remarks", remarks)
        contentValues.put ("toAccountId", toAccountId)
        contentValues.put ("fromAccountId", fromAccountId)
        contentValues.put ("toClientId", toClientId)
        contentValues.put ("fromClientId", fromClientId)
        val result = myDB. insert ("TransactionsTable", null,contentValues) //nullcolumnhack is: when you want to instert an empty row except for the auto generated id, you will need nullcolumnHack, when content values will be null.
        //result will be -1 if the insert fails. The    datatype returned by the DB upon insert is a long: our return type is also long. return the result.
        return result
    }//Inserting data function
    fun updateFundManager (fmID:String?,fmName:String?,email:String?,mobileNumber:String?, activeStatus: Boolean?, deleteStatus: Boolean?): Long{
        println("Data Updated into the table")
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("fmName", fmName)
        contentValues.put ("activeStatus", activeStatus)
        contentValues.put ("deleteStatus", deleteStatus)
        contentValues.put ("mobileNumber", mobileNumber)
        contentValues.put ("email", email)
        val cursor = myDB. rawQuery ("Select * from FundManagerAccounts where fmID = ?", arrayOf (fmID))
        if (cursor.count > 0) {//if the record exists
            result = myDB. update ("FundManagerAccounts",
                contentValues, "fmID =?", arrayOf (fmID)).toLong ()
        }
        cursor.close()
        return result
    } //updateMyCustomerData ()
    fun updateClients (clientID:String?,clientName:String?, activeStatus: Boolean?, deleteStatus: Boolean?, fmIdAssociated: Int?): Long{
        println("Data Updated into the table")
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("clientName", clientName)
        contentValues.put ("activeStatus", activeStatus)
        contentValues.put ("deleteStatus", deleteStatus)
        contentValues.put ("fmIdAssociated", fmIdAssociated)
        val cursor = myDB. rawQuery ("Select * from Clients where clientID = ?", arrayOf (clientID))
        if (cursor.count > 0) {//if the record exists
            result = myDB. update ("Clients",
                contentValues, "fmID =?", arrayOf (clientID)).toLong ()
        }
        cursor.close()
        return result
    } //updateClients ()
    fun updateAccounts (accountID:String?,accountName:String?,accountNumber:String?, upiId: String?, status: String?, currencyType: String?, clientIDAssociated: Int?): Long{
        println("Data Updated into the table")
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("accountName", accountName)
        contentValues.put ("accountNumber", accountNumber)
        contentValues.put ("upiId", upiId)
        contentValues.put ("status", status)
        contentValues.put ("currencyType", currencyType)
        contentValues.put ("clientIDAssociated", clientIDAssociated)
        val cursor = myDB. rawQuery ("Select * from Accounts where accountID = ?", arrayOf (accountID))
        if (cursor.count > 0) {//if the record exists
            result = myDB. update ("Clients",
                contentValues, "fmID =?", arrayOf (accountID)).toLong ()
        }
        cursor.close()
        return result
    } //updateClients ()
    fun updateTxn (transId:String?,remarks:String?,dateOfTxn:String?, toAccountId: Int?, fromAccountId: Int?, toClientId: Int?, fromClientId: Int?): Long{
        println("Data Updated into the table")
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase //referencing   with writable access.
        val contentValues = ContentValues () //values fOr    inserting into database
        //contentValues.put ("customerID", customerID)
        contentValues.put ("dateOfTxn", dateOfTxn)
        contentValues.put ("remarks", remarks)
        contentValues.put ("toAccountId", toAccountId)
        contentValues.put ("fromAccountId", fromAccountId)
        contentValues.put ("toClientId", toClientId)
        contentValues.put ("fromClientId", fromClientId)
        val cursor = myDB. rawQuery ("Select * from TransactionsTable where transId = ?", arrayOf (transId))
        if (cursor.count > 0) {//if the record exists
            result = myDB. update ("Clients",
                contentValues, "fmID =?", arrayOf (transId)).toLong ()
        }
        cursor.close()
        return result
    } //updateClients ()
    fun deleteFundManager (fmID: Long) : Long {
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase
        val cursor: Cursor = myDB.rawQuery(
            "Select * from Mycustomers where fmID = ?",
            arrayOf(fmID.toString())
        )
        if (cursor.count > 0) {
        //run the delete query: arguments are table     name, where clause column mane, and supplying value for    the where clause.
                result = myDB.delete("FundManagerAccounts", "fmID = ?", arrayOf(fmID.toString())).toLong()
        }
        //close the cursor
        cursor.close () //free up the cursor return result
        return result
    }
    fun deleteClient(clientID: Long) : Long {
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase
        val cursor: Cursor = myDB.rawQuery(
            "Select * from Mycustomers where fmID = ?",
            arrayOf(clientID.toString())
        )
        if (cursor.count > 0) {
            //run the delete query: arguments are table     name, where clause column mane, and supplying value for    the where clause.
            result = myDB.delete("Clients", "fmID = ?", arrayOf(clientID.toString())).toLong()
        }
        //close the cursor
        cursor.close () //free up the cursor return result
        return result
    }
    fun deleteAccount (accountID: Long) : Long {
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase
        val cursor: Cursor = myDB.rawQuery(
            "Select * from Mycustomers where fmID = ?",
            arrayOf(accountID.toString())
        )
        if (cursor.count > 0) {
            //run the delete query: arguments are table     name, where clause column mane, and supplying value for    the where clause.
            result = myDB.delete("Accounts", "fmID = ?", arrayOf(accountID.toString())).toLong()
        }
        //close the cursor
        cursor.close () //free up the cursor return result
        return result
    }
    fun deleteTxn (transId: Long) : Long {
        var result by kotlin.properties.Delegates.notNull<Long>()
        val myDB = this.writableDatabase
        val cursor: Cursor = myDB.rawQuery(
            "Select * from Mycustomers where fmID = ?",
            arrayOf(transId.toString())
        )
        if (cursor.count > 0) {
            //run the delete query: arguments are table     name, where clause column mane, and supplying value for    the where clause.
            result = myDB.delete("TransactionsTable", "fmID = ?", arrayOf(transId.toString())).toLong()
        }
        //close the cursor
        cursor.close () //free up the cursor return result
        return result
    }

    fun getDataCount () : String {
        val myDB = this. readableDatabase // read access
        val cursor : Cursor = myDB. rawQuery ("Select * from MyCustomers", null)
            val number0fColumns = cursor. columnCount //get the number of columns count.
        val number0fRows = cursor.count // just the  number of record
        //val colNames = cursor. columnNames. joinToString " } //getting comma separated values of column names.
        //Ithat is all we need> close the cursor. return    the above variables as a string.
        cursor.close ()
        return "There are SnumberOfRows rows in myCustomers table and $number0fColumns columns in each row."
    }
}