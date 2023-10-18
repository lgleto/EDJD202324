package ipca.utility.bestnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class ArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        intent.extras?.let{
            var url = it.getString(URL)
            findViewById<WebView>(R.id.webView).loadUrl(url!!)
        }
    }

    companion object {
        const val URL: String = "URL"
    }
}