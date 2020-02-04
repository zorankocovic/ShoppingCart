package mobile.shoppingcart
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu
import android.view.MenuItem
import mobile.shoppingcart.adapter.ListProductAdapter
import mobile.shoppingcart.db.DatabaseHandler
import mobile.shoppingcart.model.StoreProduct

class ListProduct : AppCompatActivity() {

    var adapter: ListProductAdapter? = null;
    var fab: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var dbHandler: DatabaseHandler? = null
    var listStoreProduct: List<StoreProduct> = ArrayList<StoreProduct>()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        initOperations()
        //initDB()
    }

    fun initDB() {
        dbHandler = DatabaseHandler(this)
        listStoreProduct = (dbHandler as DatabaseHandler).storeproduct()
        adapter = ListProductAdapter(productList = listStoreProduct, context = applicationContext)
        (recyclerView as RecyclerView).adapter = adapter
    }

    fun initViews() {
      //  val toolbar = findViewById(R.id.toolbar) as Toolbar
       // setSupportActionBar(toolbar)
        fab = findViewById(R.id.fab) as FloatingActionButton
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        adapter = ListProductAdapter(productList = listStoreProduct, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager
    }

    fun initOperations() {
        fab?.setOnClickListener { view ->
            val i = Intent(applicationContext, AddProduct::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete All Tasks")
                .setPositiveButton("YES", { dialog, i ->
                    dbHandler!!.deleteAllProduct()
                    initDB()
                    dialog.dismiss()
                })
                .setNegativeButton("NO", { dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
            return true
        }

        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }
}