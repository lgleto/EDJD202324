package ipca.utility.bestnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleDetailActivity : AppCompatActivity() {

    lateinit var webView : WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        webView = findViewById<WebView>(R.id.webView)
        intent.extras?.let{
            var url = it.getString(URL)
            webView.loadUrl(url!!)
        }

        var webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                super.shouldOverrideUrlLoading(view, url)
                webView.loadUrl(url!!)
                return true
            }
        }

        webView.webViewClient = webViewClient
    }

    companion object {
        const val URL: String = "URL"
    }
}