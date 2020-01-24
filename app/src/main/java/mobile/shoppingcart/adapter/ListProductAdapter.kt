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
import mobile.shoppingcart.AddProduct
import mobile.shoppingcart.R
import mobile.shoppingcart.model.StoreProduct
import java.util.ArrayList
class ListProductAdapter (productList: List<StoreProduct>, internal var context: Context) : RecyclerView.Adapter<ListProductAdapter.TaskViewHolder>() {

    internal var productList: List<StoreProduct> = ArrayList()
    init {
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false)
        return TaskViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val storeproduct = productList[position]
        holder.store.text = storeproduct.store
        holder.product.text = storeproduct.product
        holder.price.text = storeproduct.price
       // if (storeproduct.completed == "Y")
       //     holder.list_item.background = ContextCompat.getDrawable(context, R.color.colorSuccess)
       // else
       //     holder.list_item.background = ContextCompat.getDrawable(context, R.color.colorUnSuccess)

        holder.itemView.setOnClickListener {
            val i = Intent(context, AddProduct::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", storeproduct.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var store: TextView = view.findViewById(R.id.store) as TextView
        var product: TextView = view.findViewById(R.id.product) as TextView
        var price: TextView = view.findViewById(R.id.price) as TextView
        var list_item: LinearLayout = view.findViewById(R.id.list_item) as LinearLayout
    }

}