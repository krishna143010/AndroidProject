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
import com.example.myapplication.DBAccessClass
import com.example.myapplication.EntryOrRegisterActivity
import com.example.myapplication.GetTxnsDataClass
import com.example.myapplication.R
import java.text.SimpleDateFormat


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
        holder.txnAmount.text = ItemsViewModel.txnAmount.toString()
//        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        holder.dateOfTxn.text = SimpleDateFormat("MM/dd/yyyy").format(ItemsViewModel.dateOfTxn).toString()
//        holder.dateOfTxn.text = ItemsViewModel.dateOfTxn.toString()
        if(ItemsViewModel.fromAccount=="External"){
            holder.imageView.setImageResource(R.drawable.outgoingiconfor_foreground)
        }else if(ItemsViewModel.toAccount=="External"){
            holder.imageView.setImageResource(R.drawable.outgoingiconfor_foreground)
        }else{
            holder.imageView.setImageResource(R.drawable.transfericonfor_foreground)
        }

        holder.deleteTxn.setOnClickListener {
            println("Delete clicked")
            val myDBHelper= DBAccessClass(it.context)
            val deleteStatus:Long=myDBHelper.deleteTxn(ItemsViewModel.transId.toLong())
            if(deleteStatus>0){
                Toast.makeText(it.context,"Delete for "+ItemsViewModel.transId+" Success",Toast.LENGTH_LONG).show()
                /*val activityAsComInterface: CallBackToTxFragmentInterface=EntryOrRegisterActivity()
                activityAsComInterface.refreshRV()*/
                val i = Intent(it.context, EntryOrRegisterActivity::class.java)
                i.putExtra("refreshRV", 1)
                startActivity(it.context,i, Bundle())
            }else{
                Toast.makeText(it.context,"Delete for "+ItemsViewModel.transId+" Failed",Toast.LENGTH_LONG).show()
            }
        }
        holder.editTxn.setOnClickListener {
            val i = Intent(it.context, EntryOrRegisterActivity::class.java)
            i.putExtra("editTxn", true)
            i.putExtra("fromClient", ItemsViewModel.fromClient)
            i.putExtra("toClient", ItemsViewModel.toClient)
            i.putExtra("fromAccount", ItemsViewModel.fromAccount)
            i.putExtra("toAccount", ItemsViewModel.toAccount)
            i.putExtra("remarks", ItemsViewModel.remarks)
            i.putExtra("txnAmount", ItemsViewModel.txnAmount)
            i.putExtra("txnDate", holder.dateOfTxn.text.toString())
            i.putExtra("txnId", ItemsViewModel.transId)

            val bundle=Bundle()
            bundle.putBoolean("editTxn", true)
            bundle.putString("fromClient", ItemsViewModel.fromClient)
            bundle.putString("toClient", ItemsViewModel.toClient)
            bundle.putString("fromAccount", ItemsViewModel.fromAccount)
            bundle.putString("toAccount", ItemsViewModel.toAccount)
            bundle.putString("remarks", ItemsViewModel.remarks)
            bundle.putLong("txnAmount", ItemsViewModel.txnAmount)
            bundle.putString("txnDate", holder.dateOfTxn.text.toString())
            bundle.putInt("txnId", ItemsViewModel.transId)
//            val frag=TransactionFragment()
//            frag.arguments=bundle
            startActivity(it.context,i, bundle)
        }
        holder.shareTxn.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is the text you want to share")
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")
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
