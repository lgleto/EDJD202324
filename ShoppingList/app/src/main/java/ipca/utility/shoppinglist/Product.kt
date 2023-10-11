package ipca.utility.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity
data class Product(
    @PrimaryKey
    var uid: Long,
    var name: String?,
    var qtd: Int,
    var isChecked: Boolean = false)

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll() : LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM Product")
    fun deleteAll()

}

