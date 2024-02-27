package com.silvia.appproducts.data

import android.app.Activity
import android.content.Context
import android.os.Debug
import android.util.Log
import android.widget.Toast
import com.silvia.appproducts.models.Product
import kotlin.coroutines.coroutineContext


class ProductRepository {
    private val service = ProductAPI().makeRetrofitService()

    public suspend fun findAllProducts(): List<Product>? {
        try {
            val responseProducts = service.listProducts()
            if (responseProducts.isSuccessful) {
                return responseProducts.body()
            } else {
                return null
                Log.e(
                    "ERROR EN LLAMADA",
                    "findAllProducts: Error: No se han encontrado los productos"
                )
            }
        }catch (e:Exception){
            Log.e("ERROR EN LLAMADA", "findAllProducts: Error: ${e.message}" )
            return null
        }

    }

    public suspend fun foundProduct(id:Long):Product? {
            try{
                val responseProduct = service.productById(id)
                if (responseProduct.isSuccessful) {
                    return responseProduct.body()
                } else {
                    return null
                    Log.e("ERROR EN LLAMADA", "foundProduct: Error: No se ha encontrado el producto" )
                }
            }catch (e: Exception) {
                Log.e("ERROR EN LLAMADA", "foundProduct: Error: ${e.message}" )
                return null
            }


    }

    suspend fun newProduct(product: Product){
            try{
                val response = service.saveProduct(product)
                if (response.isSuccessful){
                    val savedProduct = response.body()
                }else{
                    Log.e("ERRORM LLAMADA","newProduct")
                    //Toast.makeText(contect, "Error al guardar el producto: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }catch(e:Exception){
                //Toast.makeText(context, "Error al guardar el producto: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("ERROR EN LLAMADA", "newProduct: Error: ${e.message}")
            }

    }
    suspend fun deleteProduct(id: Long, context: Context){
        try {
            val response = service.deleteProduct(id)
            if (response.isSuccessful){
                Toast.makeText(context,"Se ha eliminado el producto con id : " + id.toString(), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"No se ha podido eliminar el producto con id : " + id.toString(), Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Log.e("ERROR EN LLAMADA", "deleteProduct: Error: ${e.message}" )

        }
    }
}
