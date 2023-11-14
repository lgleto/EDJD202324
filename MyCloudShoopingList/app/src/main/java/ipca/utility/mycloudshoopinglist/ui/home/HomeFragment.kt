package ipca.utility.mycloudshoopinglist.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.utility.mycloudshoopinglist.TAG
import ipca.utility.mycloudshoopinglist.databinding.FragmentHomeBinding
import ipca.utility.mycloudshoopinglist.databinding.RowShoppingListBinding
import ipca.utility.mycloudshoopinglist.models.ShoppingList

class HomeFragment : Fragment() {

    val db = Firebase.firestore
    var shoppingList = arrayListOf<ShoppingList>( )
    private var adapter = ShoppingListAdapter()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listViewShoppingLists.adapter = adapter

        db.collection("lists")
            .addSnapshotListener { result, error ->
                var shoppingList = arrayListOf<ShoppingList>( )
                if (result != null) {
                    for (document in result.documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")

                        val name  = document.data?.get("name") as String
                        shoppingList.add(ShoppingList(name))
                    }
                }
                this.shoppingList = shoppingList
                adapter.notifyDataSetChanged()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ShoppingListAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return shoppingList.size
        }

        override fun getItem(position: Int): Any {
            return shoppingList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = RowShoppingListBinding.inflate(layoutInflater)

            rootView.textViewName.text = shoppingList[position].name

            return rootView.root
        }

    }

}