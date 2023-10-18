package ipca.utility.bestnews

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date

data class Article (
    var title       : String,
    var description : String?,
    var url         : String,
    var urlToImage  : String?,
    var publishedAt : Date,
) {

    companion object {
        fun fromJson( jsonObject: JSONObject) : Article {

            val title       = jsonObject["title"      ] as String
            val content     = jsonObject["content"    ] as? String?
            val url         = jsonObject["url"        ] as String
            val urlToImage  = jsonObject["urlToImage" ] as? String?
            val publishedAt = (jsonObject["publishedAt" ] as String).toDate()


            return Article(
                title,
                content,
                url,
                urlToImage,
                publishedAt
            )
        }
    }
}

