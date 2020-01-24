package mobile.shoppingcart
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import mobile.shoppingcart.db.DatabaseHandler
import mobile.shoppingcart.model.StoreProduct
import kotlinx.android.synthetic.main.activity_add_edit.*
class AddProduct : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null
    var isEditMode = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initDB()
        initOperations()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        btn_delete.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val storeproduct: StoreProduct = dbHandler!!.getProduct(intent.getIntExtra("Id",0))
            istorename.setText(storeproduct.store)
            iname.setText(storeproduct.product)
            idesc.setText(storeproduct.price)

            btn_delete.visibility = View.VISIBLE
        }
    }

    private fun initOperations() {
        btn_save.setOnClickListener({
            var success: Boolean = false
            if (!isEditMode) {
                val storeproduct: StoreProduct = StoreProduct()
                storeproduct.store = istorename.text.toString()
                storeproduct.product = iname.text.toString()
                storeproduct.price = idesc.text.toString()

                success = dbHandler?.addProduct(storeproduct) as Boolean
            } else {
                val tasks: StoreProduct = StoreProduct()
                tasks.id = intent.getIntExtra("Id", 0)
                tasks.store = istorename.text.toString()
                tasks.product = iname.text.toString()
                tasks.price = idesc.text.toString()

                success = dbHandler?.updateProduct(tasks) as Boolean
            }

            if (success)
                finish()
        })

        btn_delete.setOnClickListener({
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete the Task.")
                .setPositiveButton("YES", { dialog, i ->
                    val success = dbHandler?.deleteProduct(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                })
                .setNegativeButton("NO", { dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
