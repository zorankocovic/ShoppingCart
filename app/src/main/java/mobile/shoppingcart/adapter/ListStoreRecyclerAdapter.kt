package mobile.shoppingcart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import mobile.shoppingcart.R
import mobile.shoppingcart.model.MainViewModel
import mobile.shoppingcart.model.Store

class ListStoreRecyclerAdapter (val viewModel: MainViewModel, val arrayList: ArrayList<Store>, val context: Context): RecyclerView.Adapter<ListStoreRecyclerAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListStoreRecyclerAdapter.NotesViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.list_store,parent,false)
        return NotesViewHolder(root)
    }

    override fun onBindViewHolder(holder: ListStoreRecyclerAdapter.NotesViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }

    override fun getItemCount(): Int {
        if(arrayList.size==0){
            Toast.makeText(context,"List is empty", Toast.LENGTH_LONG).show()
        }else{

        }
        return arrayList.size
    }


    inner  class NotesViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(blog: Store){
      //      binding.title.text = blog.title

        }

    }

}