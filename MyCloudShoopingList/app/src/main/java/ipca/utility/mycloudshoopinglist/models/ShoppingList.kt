package ipca.utility.mycloudshoopinglist.models

data class ShoppingList (var id : String,
                         var name: String){

    companion object {
        fun fromMap(id: String, data: MutableMap<String, Any>?) : ShoppingList{
            return ShoppingList(
                id,
                data?.get("name") as String
                )

        }
    }
}