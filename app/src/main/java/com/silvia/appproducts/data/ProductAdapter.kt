package com.silvia.appproducts.data

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.silvia.appproducts.MainActivity
import com.silvia.appproducts.R
import com.silvia.appproducts.models.Product
import com.silvia.appproducts.ui.home.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductAdapter(val products:List<Product>,val lifecycleOwne: CoroutineScope,val homeViewModel: HomeViewModel): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val view:View, val lifecycleOwne: CoroutineScope,val homeViewModel: HomeViewModel) : RecyclerView.ViewHolder(view){
        val image = itemView.findViewById(R.id.productImage) as  ImageView
        val name =  itemView.findViewById(R.id.productName) as TextView
        val price = itemView.findViewById(R.id.priceProduct) as TextView
        val btnDelete = itemView.findViewById(R.id.btnDelete) as Button
        val btnEdit = itemView.findViewById(R.id.btnEdit) as Button

        fun bind(product: Product){

            name.text = product.nombre
            price.text = product.precio.toString() + "€"
            // Utiliza Picasso para cargar la imagen en el ImageView
            Picasso.get().load(product.image).into(image)
            btnDelete.setOnClickListener{
                lifecycleOwne.launch {
                    ProductRepository().deleteProduct(product.id,it.context)
                }
                homeViewModel.updateProductos()
                Toast.makeText(it.context,"Has eliminado el producto :" + product.nombre, Toast.LENGTH_SHORT).show()
            }
            btnEdit.setOnClickListener{
                //Pasar datos del producto seleccionado al fragment de detalles
                homeViewModel.productSelectNow(product)
                homeViewModel.setIsEditable(true)
                findNavController(view).navigate(R.id.action_nav_home_to_homeDetailsFragment)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view,lifecycleOwne,homeViewModel)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int){
        (holder as ProductViewHolder).bind(products[position])
    }
    override fun getItemCount(): Int {
        return  products.size
    }


}
