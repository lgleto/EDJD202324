package osga.lifestyle.awsomenews.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import osga.lifestyle.awsomenews.Article
import osga.lifestyle.awsomenews.Backend
import osga.lifestyle.awsomenews.R
import osga.lifestyle.awsomenews.databinding.FragmentHomeBinding
import osga.lifestyle.awsomenews.toShortDateTime

class HomeFragment : Fragment() {

    var articles : List<Article> = arrayListOf<Article>()
    val articlesAdapter = ArticleListAdapter()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listViewArticles.adapter = articlesAdapter

        Backend.fetchArticles(lifecycleScope,null ) {
            it?.let {articles ->
                this.articles = articles
                articlesAdapter.notifyDataSetChanged()
            }?:run {
                Toast.makeText(requireContext(),
                    "No internet connection!",
                    Toast.LENGTH_LONG).show()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ArticleListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return articles.size
        }

        override fun getItem(position: Int): Any {
            return articles[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_article,parent,false)
            val textViewTitle = rootView.findViewById<TextView>(R.id.textViewTitle)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewDate = rootView.findViewById<TextView>(R.id.textViewDate)
            val imageView = rootView.findViewById<ImageView>(R.id.imageView)

            textViewTitle.text = articles[position].title
            textViewDescription.text = articles[position].description
            textViewDate.text = articles[position].publishedAt.toShortDateTime()

            articles[position].urlToImage?.let { url ->
                Backend.fetchImage(lifecycleScope, url){ bitmap ->
                    bitmap?.let {
                        imageView.setImageBitmap(it)
                    }
                }
            }
            rootView.setOnClickListener {
                //val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                //intent.putExtra(ArticleDetailActivity.URL, articles[position].url )
                //startActivity(intent)
            }


            return rootView
        }

    }
}