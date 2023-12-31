package ipca.utility.mycloudshoopinglist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.utility.mycloudshoopinglist.R
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
                        shoppingList.add(
                            ShoppingList.fromMap(document.id,document.data))
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

            rootView.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("docId", shoppingList[position].id )
                findNavController().navigate(R.id.action_navigation_home_to_productListFragment, bundle)
            }

            return rootView.root
        }

    }

}