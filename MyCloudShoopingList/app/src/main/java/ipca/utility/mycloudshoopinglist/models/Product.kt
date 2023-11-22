package ipca.utility.mycloudshoopinglist.models

data class Product (
    val id : String,
    var name : String,
    var qtt: Int,
    var isChecked: Boolean){

    fun toMap() : Map<String, Any>{
        return hashMapOf(
            "name" to name,
            "qtr" to qtt,
            "isChecked" to isChecked,
        )
    }

    companion object {
        fun fromMap(id : String, data: MutableMap<String,Any>? ) : Product{
            return Product(
                id,
                data?.get("name") as String,
                (data?.get("qtr") as? Int?)?:0,
                (data?.get("isChecked") as? Boolean?)?:false,
            )

        }
    }
}