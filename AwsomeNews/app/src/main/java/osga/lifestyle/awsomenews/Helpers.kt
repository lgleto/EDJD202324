package osga.lifestyle.awsomenews;

import java.text.SimpleDateFormat
import java.util.Date

fun Date.toShortDateTime() : String {
    val format = SimpleDateFormat("EEEE, dd MMM yyyy HH:mm")
    return format.format(this)
}

fun String.toDate(): Date {
    //2023-10-17T10:22:32Z
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return format.parse(this)
}