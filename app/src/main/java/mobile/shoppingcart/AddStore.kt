package mobile.shoppingcart
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import mobile.shoppingcart.db.DatabaseHandler
import kotlinx.android.synthetic.main.store_add_edit.*
import mobile.shoppingcart.model.Store

class AddStore : AppCompatActivity(){
    var dbHandler: DatabaseHandler? = null
    var isEditMode = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.store_add_edit)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initDB()
        initOperations()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        btn_delete.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val baseproduct: Store = dbHandler!!.getStore(intent.getIntExtra("id",0))
            name.setText(baseproduct.name)


            btn_delete.visibility = View.VISIBLE
        }
    }

    private fun initOperations() {
        btn_save.setOnClickListener({
            var success: Boolean = false
            if (!isEditMode) {
                val baseproduct: Store = Store()
                baseproduct.name = name.text.toString()

                success = dbHandler?.addStore(baseproduct) as Boolean
            } else {
                val baseproduct: Store = Store()
                baseproduct.id = intent.getIntExtra("id", 0)
                baseproduct.name = name.text.toString()
                success = dbHandler?.updateStore(baseproduct) as Boolean
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