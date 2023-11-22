package ipca.utility.mycloudshoopinglist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.utility.mycloudshoopinglist.databinding.FragmentProductDetailBinding
import ipca.utility.mycloudshoopinglist.models.Product

class ProductDetailFragment : Fragment() {

    var qtd : Int
        get()  = binding.textViewQtd.text.toString().toInt()
        set(value){
            if (value >= 0){
                binding.textViewQtd.text = value.toString()
            }
        }

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!


    var listId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listId = it.getString("listId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonIncrement.setOnClickListener {
            qtd += 1
        }

        binding.buttonDecrement.setOnClickListener {
            qtd -= 1
        }

        binding.buttonDone.setOnClickListener {
            val db = Firebase.firestore
            db.collection("lists")
                .document(listId!!)
                .collection("items")
                .add(Product("",
                    binding.editTextProductName.text.toString(),
                    binding.textViewQtd.text.toString().toInt(),
                    false
                ).toMap())
                .addOnSuccessListener { documentReference ->
                    findNavController().popBackStack()
                }
                .addOnFailureListener { e ->
                    Toast.makeText( requireContext(), "Error adding document",Toast.LENGTH_LONG).show()
                }
        }

    }

    companion object {
        const val DATA_NAME = "data_name"
        const val DATA_QTD  = "data_qtd"
        const val DATA_POSITION = "data_position"
    }


}