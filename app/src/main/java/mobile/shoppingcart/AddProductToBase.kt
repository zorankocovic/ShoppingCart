package mobile.shoppingcart

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import mobile.shoppingcart.db.DatabaseHandler
import mobile.shoppingcart.model.ProductBase
import kotlinx.android.synthetic.main.activity_add_edit_base.*

class AddProductToBase : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
var isEditMode = false

public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_edit_base)
    Toast.makeText(applicationContext,"sdfdsf", Toast.LENGTH_SHORT).show()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    initDB()
    initOperations()
}

private fun initDB() {
    dbHandler = DatabaseHandler(this)
    btn_delete.visibility = View.INVISIBLE
    if (intent != null && intent.getStringExtra("Mode") == "E") {
        isEditMode = true
        val baseproduct: ProductBase = dbHandler!!.getProductBase(intent.getIntExtra("id",0))
        name.setText(baseproduct.name)


        btn_delete.visibility = View.VISIBLE
    }
}

private fun initOperations() {
    btn_save.setOnClickListener({
        var success: Boolean = false
        if (!isEditMode) {
            val baseproduct: ProductBase = ProductBase()
            baseproduct.name = name.text.toString()

            success = dbHandler?.addProducttobase(baseproduct) as Boolean
        } else {
            val baseproduct: ProductBase = ProductBase()
            baseproduct.id = intent.getIntExtra("id", 0)
            baseproduct.name = name.text.toString()
            success = dbHandler?.updateProductBase(baseproduct) as Boolean
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