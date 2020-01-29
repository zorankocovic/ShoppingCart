package mobile.shoppingcart.adapter
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import mobile.shoppingcart.AddProductToBase
import mobile.shoppingcart.R
import mobile.shoppingcart.model.ProductBase
import java.util.ArrayList
class ListProductBaseAdapter (productList: List<ProductBase>, internal var context: Context) : RecyclerView.Adapter<ListProductBaseAdapter.ViewHolder>() {

    internal var productList: List<ProductBase> = ArrayList()
    init {
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_product_base, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storeproduct = productList[position]
        holder.name.text = storeproduct.name

        holder.itemView.setOnClickListener {
            val i = Intent(context, AddProductToBase::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", storeproduct.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name) as TextView

        var list_item: LinearLayout = view.findViewById(R.id.list_item) as LinearLayout
    }

}