package mobile.shoppingcart.adapter
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import mobile.shoppingcart.AddProductToBase
import mobile.shoppingcart.R
import mobile.shoppingcart.model.ProductBase
import java.util.ArrayList
import android.view.ContextMenu.ContextMenuInfo
import android.view.*


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
          //  context.startActivity(i)
        }
        holder.buttonViewOption?.setOnClickListener(View.OnClickListener {

           /* val popup = holder.buttonViewOption?.let { it1 -> PopupMenu(context, it1) }

            popup?.inflate(R.menu.optionsshoppingcart)

            popup?.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.menu1 ->
                            //handle menu1 click
                            return true
                        R.id.menu2 ->
                            //handle menu2 click
                            return true
                        R.id.menu3 ->
                            //handle menu3 click
                            return true
                        else -> return false
                    }
                }
            })*/

          //  popup?.show()
           // Toast.makeText(context,storeproduct.id.toString(), Toast.LENGTH_SHORT).show()
          /*  val popup = PopupMenu(context, holder.buttonViewOption)

            popup.menuInflater.inflate(R.menu.optionsshoppingcart, popup.menu)
            popup?.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.menu1 ->
                            //handle menu1 click
                            return true
                        R.id.menu2 ->
                            //handle menu2 click
                            return true
                        R.id.menu3 ->
                            //handle menu3 click
                            return true
                        else -> return false
                    }
                }
            })
            popup.show()*/

                // V is View variable and tv is name of textView

                val pop= PopupMenu(context,it)
                pop.inflate(R.menu.optionsshoppingcart)

                pop.setOnMenuItemClickListener {item->

                    when(item.itemId)

                    {
                        R.id.menu1->{Toast.makeText(context,storeproduct.id.toString(), Toast.LENGTH_SHORT).show() }

                        R.id.menu3->{ }


                    }
                    true
                }
                pop.show()
                true


        })



    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name) as TextView
        var buttonViewOption: Button = view.findViewById(R.id.textViewOptions) as Button

        var list_item: RelativeLayout = view.findViewById(R.id.list_item) as RelativeLayout

    }


}