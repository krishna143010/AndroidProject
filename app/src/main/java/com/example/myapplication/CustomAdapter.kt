import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class CustomAdapter(private val mList: MutableList<GetTxnsDataClass>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    //val context:Context = TODO()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class

        // sets the text to the textview from our itemHolder class
        holder.fromClient.text = ItemsViewModel.fromClient
        holder.toClient.text = ItemsViewModel.toClient
        holder.fromAccount.text = "("+ItemsViewModel.fromAccount+")"
        holder.toAccount.text = "("+ItemsViewModel.toAccount+")"
        holder.remarks.text = ItemsViewModel.remarks

        // Create a currency formatter
        // Create a currency formatter
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()

// Set the currency code to USD (United States Dollar)

// Set the currency code to USD (United States Dollar)
        formatter.currency = Currency.getInstance("INR")

// Format the amount

// Format the amount
        val formattedAmount: String = formatter.format(ItemsViewModel.txnAmount)
        holder.txnAmount.text = formattedAmount
//        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        holder.dateOfTxn.text = SimpleDateFormat("MM/dd/yyyy").format(ItemsViewModel.dateOfTxn).toString()
//        holder.dateOfTxn.text = ItemsViewModel.dateOfTxn.toString()
        if(ItemsViewModel.fromAccount=="External"){
            holder.imageView.setImageResource(R.drawable.incomingiconfor_foreground)
        }else if(ItemsViewModel.toAccount=="External"){
            holder.imageView.setImageResource(R.drawable.outgoingiconfor_foreground)
        }else{
            holder.imageView.setImageResource(R.drawable.transfericonfor_foreground)
        }

        holder.deleteTxn.setOnClickListener {
            println("Delete clicked")
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Deletion of Txn Id:"+ItemsViewModel.transId.toInt())
            builder.setMessage("Are you sure to Delete the Txn?")
            builder.setPositiveButton("Yes") { dialog, which ->
                run{
                    val myDBHelper= DBAccessClass(it.context)
                    val deleteStatus:Long=myDBHelper.deleteTxn(ItemsViewModel.transId.toLong())
                    if(deleteStatus>0){
                        Toast.makeText(it.context,"Delete for "+ItemsViewModel.transId+" Success",Toast.LENGTH_LONG).show()
                        val i = Intent(it.context, MainActivity::class.java)
                        i.putExtra("refreshRV", 1)
                        i.putExtra("fmId", ItemsViewModel.fmId.toString())
                        startActivity(it.context,i, Bundle())
                    }else{
                        Toast.makeText(it.context,"Delete for "+ItemsViewModel.transId+" Failed",Toast.LENGTH_LONG).show()
                    }

                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
            }
            builder.show()

        }
        holder.editTxn.setOnClickListener {
            val i = Intent(it.context, MainActivity::class.java)
            i.putExtra("editTxn", true)
            i.putExtra("fromClient", ItemsViewModel.fromClient)
            i.putExtra("toClient", ItemsViewModel.toClient)
            i.putExtra("fromAccount", ItemsViewModel.fromAccount)
            i.putExtra("toAccount", ItemsViewModel.toAccount)
            i.putExtra("remarks", ItemsViewModel.remarks)
            i.putExtra("txnAmount", ItemsViewModel.txnAmount)
            i.putExtra("txnDate", holder.dateOfTxn.text.toString())
            i.putExtra("txnId", ItemsViewModel.transId)
            i.putExtra("fmId", ItemsViewModel.fmId.toString())

            val bundle=Bundle()
            /*bundle.putBoolean("editTxn", true)
            bundle.putString("fromClient", ItemsViewModel.fromClient)
            bundle.putString("toClient", ItemsViewModel.toClient)
            bundle.putString("fromAccount", ItemsViewModel.fromAccount)
            bundle.putString("toAccount", ItemsViewModel.toAccount)
            bundle.putString("remarks", ItemsViewModel.remarks)
            bundle.putLong("txnAmount", ItemsViewModel.txnAmount)
            bundle.putString("txnDate", holder.dateOfTxn.text.toString())
            bundle.putInt("txnId", ItemsViewModel.transId)*/
//            val frag=TransactionFragment()
//            frag.arguments=bundle
            startActivity(it.context,i, bundle)
        }
        holder.shareTxn.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Transaction happened from "+ItemsViewModel.fromClient+" to "+ItemsViewModel.toClient+" for the Amount: "+holder.txnAmount.text.toString()+" on"+SimpleDateFormat("MM/dd/yyyy").format(ItemsViewModel.dateOfTxn).toString())
            sendIntent.type = "text/plain"
            startActivity(it.context,sendIntent, Bundle())
        }



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.txnTypeImage)
        val fromClient: TextView = itemView.findViewById(R.id.fromClient)
        val toClient: TextView = itemView.findViewById(R.id.toClient)
        val fromAccount: TextView = itemView.findViewById(R.id.fromAccount)
        val toAccount: TextView = itemView.findViewById(R.id.toAccount)
        val remarks: TextView = itemView.findViewById(R.id.remarks)
        val dateOfTxn: TextView = itemView.findViewById(R.id.dateOfTxn)
        val shareTxn: ConstraintLayout = itemView.findViewById(R.id.shareTxn)
        val deleteTxn: ConstraintLayout = itemView.findViewById(R.id.deleteTxn)
        val editTxn: ConstraintLayout = itemView.findViewById(R.id.editTxn)
        val txnAmount: TextView = itemView.findViewById(R.id.txnAmount)


    }
}
