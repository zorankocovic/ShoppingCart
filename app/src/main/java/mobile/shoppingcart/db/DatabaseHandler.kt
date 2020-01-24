package mobile.shoppingcart.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import mobile.shoppingcart.model.StoreProduct
import mobile.shoppingcart.model.ProductBase
import java.util.*

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_SP ($ID INTEGER PRIMARY KEY, $STORENAME TEXT, $NAME TEXT, $DESCR TEXT, $COMPLETED TEXT);"
        db.execSQL(CREATE_TABLE)
        val PRODUCTBASETABLE = "CREATE TABLE Product ($ID INTEGER PRIMARY KEY,  name TEXT);"
        db.execSQL(PRODUCTBASETABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_SP"
        db.execSQL(DROP_TABLE)
        onCreate(db)
        val DROP_PRODUCT = "DROP TABLE IF EXISTS Product"
        db.execSQL(DROP_PRODUCT)
        onCreate(db)
    }

    fun addProduct(storeproduct: StoreProduct): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STORENAME, storeproduct.store)
        values.put(NAME, storeproduct.product)
        values.put(DESCR, storeproduct.price)
        values.put(COMPLETED, storeproduct.completed)
        val _success = db.insert(TABLE_SP, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getProduct(_id: Int): StoreProduct {
        val storeproduct = StoreProduct()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_SP WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        storeproduct.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        storeproduct.store = cursor.getString(cursor.getColumnIndex(STORENAME))
        storeproduct.product = cursor.getString(cursor.getColumnIndex(NAME))
        storeproduct.price = cursor.getString(cursor.getColumnIndex(DESCR))
        storeproduct.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
        cursor.close()
        return storeproduct
    }

    fun storeproduct(): List<StoreProduct> {
        val sp = ArrayList<StoreProduct>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_SP"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val storeproduct = StoreProduct()
                    storeproduct.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    storeproduct.store = cursor.getString(cursor.getColumnIndex(STORENAME))
                    storeproduct.product = cursor.getString(cursor.getColumnIndex(NAME))
                    storeproduct.price = cursor.getString(cursor.getColumnIndex(DESCR))
                    storeproduct.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
                    sp.add(storeproduct)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return sp
    }

    fun updateProduct(storeproduct: StoreProduct): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STORENAME, storeproduct.store)
        values.put(NAME, storeproduct.product)
        values.put(DESCR, storeproduct.price)
        values.put(COMPLETED, storeproduct.completed)
        val _success = db.update(TABLE_SP, values, ID + "=?", arrayOf(storeproduct.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteProduct(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_SP, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllProduct(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_SP, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "ShoppingCartDb"
        private val TABLE_SP = "StoreProduct"
        private val ID = "Id"
        private val STORENAME = "StoreName"
        private val NAME = "Name"
        private val DESCR = "Descr"
        private val COMPLETED = "Completed"
    }

    fun addProducttobase(baseproduct: ProductBase): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", baseproduct.name)
        val _success = db.insert("Product", null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }
}