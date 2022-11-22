package mobile.shoppingcart.model
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobile.shoppingcart.db.DatabaseHandler
import java.util.ArrayList

class MainViewModel (application: Application): AndroidViewModel(application)  {
    var lst = MutableLiveData ArrayList<Store>()
  // var lst: List<Store> = ArrayList<Store>()

    var newlist = arrayListOf<Store>()
    var dbHandler: DatabaseHandler? = null

    fun add(store: StoreMVVM){
       // newlist.add(store)
      //  lst.value=newlist


        var success: Boolean = false
        dbHandler = DatabaseHandler(getApplication())
        success = dbHandler?.addStoreMVVM(store) as Boolean
        if (success){ Toast.makeText(getApplication(),"Enter value!", Toast.LENGTH_LONG).show()}
    }



    fun getAllStores(store: Store){
        dbHandler = DatabaseHandler(getApplication())
        lst = (dbHandler as DatabaseHandler).allStoreMVVM()
    }


}