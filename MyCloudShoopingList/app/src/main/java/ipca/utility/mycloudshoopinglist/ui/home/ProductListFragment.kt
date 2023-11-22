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
import ipca.utility.mycloudshoopinglist.databinding.RowProductBinding
import ipca.utility.mycloudshoopinglist.databinding.RowShoppingListBinding
import ipca.utility.mycloudshoopinglist.models.Product
import ipca.utility.mycloudshoopinglist.models.ShoppingList

class ProductListFragment : Fragment() {

    val db = Firebase.firestore

    var products = arrayListOf<Product>( )

    private var adapter = ShoppingListAdapter()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var docId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            docId = it.getString("docId")
        }
    }

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
            .document(docId!!)
            .collection("items")
            .addSnapshotListener { result, error ->
                var products = arrayListOf<Product>( )
                if (result != null) {
                    for (document in result.documents) {
                        products.add(
                            Product.fromMap(document.id,document.data))
                    }
                }
                this.products = products
                adapter.notifyDataSetChanged()
            }

        binding.buttonAdd.setOnClickListener {
           val bundle = Bundle()
           bundle.putString("listId", docId)

           findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment,bundle)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ShoppingListAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = RowProductBinding.inflate(layoutInflater)

            rootView.textViewProductName.text = products[position].name
            rootView.textViewQty.text = "QTD: ${products[position].qtt}"
            rootView.checkBox.isChecked = products[position].isChecked

            rootView.root.setOnClickListener {
                //val bundle = Bundle()
                //bundle.putString("listId", docId)
//
                //findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment,bundle)
            }
            return rootView.root
        }

    }

}