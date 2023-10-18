package ipca.utility.bestnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date

class MainActivity : AppCompatActivity() {

    var articles : List<Article> = arrayListOf<Article>()
    val articlesAdapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listViewArticles = findViewById<ListView>(R.id.listViewArticles)
        listViewArticles.adapter = articlesAdapter

        Backend.fetchArticles(lifecycleScope,"sports" ) {
            it?.let {articles ->
                this.articles = articles
                articlesAdapter.notifyDataSetChanged()
            }?:run {
                Toast.makeText(this@MainActivity,
                    "No internet connection!",
                    Toast.LENGTH_LONG).show()
            }
        }
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

                val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                intent.putExtra(ArticleDetailActivity.URL, articles[position].url )
                startActivity(intent)
            }


            return rootView
        }

    }
}