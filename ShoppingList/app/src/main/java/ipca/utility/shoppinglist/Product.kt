package ipca.utility.shoppinglist

data class Product(var name: String?, var qtd: Int, var isChecked: Boolean = false)

/*
class Product {

    var name : String? = null
    var qtd  : Int = 0
    var isChecked : Boolean = false

    constructor(name: String?, qtd: Int, isChecked: Boolean){
        this.name = name
        this.qtd = qtd
        this.isChecked = isChecked
    }

}*/