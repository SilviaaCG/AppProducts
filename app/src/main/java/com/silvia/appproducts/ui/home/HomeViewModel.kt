package com.silvia.appproducts.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silvia.appproducts.data.ProductRepository
import com.silvia.appproducts.models.Product
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //lista de pproductos
    private var _productos = MutableLiveData<List<Product>>().apply {
        viewModelScope.launch {
            value = ProductRepository().findAllProducts()
        }
    }
    var productos: LiveData<List<Product>> = _productos
    fun updateProductos() {
        viewModelScope.launch { _productos.value = ProductRepository().findAllProducts() }
    }
    //producto seleccionado
    private var _productSelect = MutableLiveData<Product>().apply {
        value = Product(0L, "nnn","nombreProducto",0.0)
    }
    val productSelect:LiveData<Product> get () = _productSelect
    fun productSelectNow(product: Product){
        _productSelect.value = product
    }
    //Boolean para saber si es la plantilla de editar o de añadir
    private var _isEditable = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isEditable: LiveData<Boolean> = _isEditable
    fun setIsEditable(boolean: Boolean){
        _isEditable.value = boolean
    }

}