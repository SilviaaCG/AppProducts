package com.silvia.appproducts.data

import com.silvia.appproducts.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("/producto")
    suspend fun listProducts(): Response<List<Product>>
    @GET("/producto/get={id}")
    suspend fun productById(@Path("id") id:Long): Response<Product>
    @POST("/producto")
    suspend fun saveProduct(@Body product: Product): Response<Product>
    @DELETE("/producto/delete={id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<String>
}