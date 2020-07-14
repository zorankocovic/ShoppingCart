package mobile.shoppingcart
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import mobile.shoppingcart.db.DatabaseHandler
import mobile.shoppingcart.model.StoreProduct
import kotlinx.android.synthetic.main.activity_add_edit.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mobile.shoppingcart.model.Store

class AddProduct : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var strs= IntArray(1)
    var storespinner: ArrayList<String> = ArrayList()
     val NEW_SPINNER_ID = 1
    var dbHandler: DatabaseHandler? = null
    var isEditMode = false
    var selectedstore:String=""
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        //if (intent == null)populatespinner()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
      /*  val mySpinner = findViewById<Spinner>(R.id.istorename)
        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@AddProduct
            prompt = "Select your favourite language"
            gravity = android.view.Gravity.CENTER

        }*/

        initDB()
        initOperations()

    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        btn_delete.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val storeproduct: StoreProduct = dbHandler!!.getProduct(intent.getIntExtra("Id",0))
          //  istorename.setText(storeproduct.store)
            populatespinner(storeproduct.store)
            iname.setText(storeproduct.product)
            idesc.setText(storeproduct.price)

            btn_delete.visibility = View.VISIBLE
        }
        else {populatespinner("")}
    }

    private fun initOperations() {
        btn_save.setOnClickListener({
            var success: Boolean = false
            if (!isEditMode) {
                val storeproduct: StoreProduct = StoreProduct()
                storeproduct.store = selectedstore
                storeproduct.product = iname.text.toString()
                storeproduct.price = idesc.text.toString()

                success = dbHandler?.addProduct(storeproduct) as Boolean
            } else {

                val tasks: StoreProduct = StoreProduct()
                tasks.id = intent.getIntExtra("Id", 0)
               tasks.store = selectedstore
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

    fun populatespinner(ab:String)
    {   //  var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")
        //var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")
var selectedposition: Int = 0
        var languages: ArrayList<Store> = ArrayList()
        //list.add("text")
        dbHandler = DatabaseHandler(this)
        languages = (dbHandler as DatabaseHandler).allStorespinner()
        var num:Int = languages.size
      //  Toast.makeText(applicationContext,num.toString(), Toast.LENGTH_SHORT).show()
        strs= IntArray(languages.size)
        for (i in 0 until num) {
            val storeproduct = languages[i]
            strs[i]= storeproduct.id
            storespinner.add(storeproduct.name)
if(i==0)selectedstore=storespinner[i]
       if(storeproduct.name==ab)
           selectedposition=i;
        }


       /* val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM Store"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                } while (cursor.moveToNext())
            }
        }
        cursor.close()*/




        val mySpinner = findViewById<Spinner>(R.id.istorename)
        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, storespinner)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(mySpinner)
        {
            adapter = aa
            setSelection(selectedposition, false)
            onItemSelectedListener = this@AddProduct
            prompt = "Select your favourite language"
            gravity = android.view.Gravity.CENTER

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

     override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       //  Toast.makeText(applicationContext,strs[position].toString(), Toast.LENGTH_SHORT).show()
        // Toast.makeText(applicationContext,storespinner[position], Toast.LENGTH_SHORT).show()
         selectedstore=storespinner[position]

     }
     }


