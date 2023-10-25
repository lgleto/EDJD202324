package osga.lifestyle.awsomenews

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

object Backend {

    private val client = OkHttpClient()
    private const val BASE_API = "https://newsapi.org/v2/"
    private const val PATH_TOP_HEALINES = "top-headlines?"
    private const val API_KEY = "apiKey=1765f87e4ebc40229e80fd0f75b6416c"


    fun fetchArticles(scope : LifecycleCoroutineScope,
                      category : String?,
                      callback: (List<Article>?)->Unit )  {

        var strUrl = "${BASE_API}${PATH_TOP_HEALINES}country=pt&${API_KEY}"
        category?.let{
            strUrl = "${BASE_API}${PATH_TOP_HEALINES}country=pt&category=${category}&${API_KEY}"
        }

        scope.launch (Dispatchers.IO){
            val request = Request.Builder()
                .url(strUrl)
                .build()
            client.newCall(request).execute().use { response ->
                response.body?.let {
                    val result = it.string()
                    val jsonResult = JSONObject(result)
                    val status : String = jsonResult["status"] as String
                    if (status == "ok" ){
                        val articleJSONArray  = jsonResult["articles"] as JSONArray
                        var articles = arrayListOf<Article>()
                        for ( index in 0..<articleJSONArray.length() ){
                            val jsonArticle = articleJSONArray.getJSONObject(index)
                            val article = Article.fromJson(jsonArticle)
                            articles.add(article)
                        }
                        scope.launch (Dispatchers.Main){
                            callback(articles)
                        }
                    }
                }?:run{
                    scope.launch (Dispatchers.Main){
                        callback(null)
                    }
                }
            }
        }
    }

    fun fetchImage(scope: LifecycleCoroutineScope,
                   url:String,
                   callback : (Bitmap?) -> Unit ){
        scope.launch (Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute().use { response ->
                response.body?.let {
                    val input = it.byteStream()
                    val bitmap = BitmapFactory.decodeStream(input)
                    scope.launch (Dispatchers.Main){
                        callback(bitmap)
                    }
                }?:run{
                    scope.launch (Dispatchers.Main){
                        callback(null)
                    }
                }
            }
        }
    }
}