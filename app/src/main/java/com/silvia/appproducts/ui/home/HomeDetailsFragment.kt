package com.silvia.appproducts.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.silvia.appproducts.R
import com.silvia.appproducts.data.ProductRepository
import com.silvia.appproducts.databinding.FragmentHomeDetailsBinding
import com.silvia.appproducts.models.Product
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class HomeDetailsFragment : Fragment() {
    //activityViewModels() para el viewModel compartido
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeDetailsBinding? = null
    private val binding get() = _binding!!



     var productImageView = "sisi"

    // Declarar una constante para el código de solicitud de selección de imagen
    private val PICK_IMAGE_REQUEST = 1

    // Inicializar el lanzador de actividad para la selección de imagen
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // La imagen seleccionada está disponible en el objeto "uri"
        uri?.let { selectedImageUri ->
            productImageView = selectedImageUri.toString()
            Picasso.get().load(productImageView).into(binding.productImage)
           // binding.productImage.setImageURI(selectedImageUri)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val product = homeViewModel.productSelect.value
        val isEditable = homeViewModel.isEditable.value

        if (!isEditable!!) {
            homeViewModel.productSelectNow(Product(0L, productImageView.toString(),"nombre producto", 0.0))
        }
        homeViewModel.productSelect.observe(viewLifecycleOwner){
            productImageView = it.image
            Picasso.get().load(it.image).into(binding.productImage)
            insertStringOnEditText(it.id.toString(),binding.productId)
            insertStringOnEditText(it.nombre,binding.productName)
            insertStringOnEditText(it.precio.toString(),binding.productPrice)
        }

        binding.btnImage.setOnClickListener{
            pickImageLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener(){
            val double = binding.productPrice.text.toString().toDouble()
            var newProduct:Product
            if(!isEditable){
                newProduct = Product(0L,productImageView,binding.productName.text.toString(),double)
                lifecycleScope.launch {
                    ProductRepository().newProduct(newProduct)
                }

            }else{

                newProduct = Product(product!!.id, productImageView,binding.productName.text.toString(),double)
                lifecycleScope.launch {
                    ProductRepository().newProduct(newProduct)
                }

            }

                findNavController().navigate(R.id.action_homeDetailsFragment_to_nav_home)
        }

        return root

    }
    fun insertStringOnEditText(string: String, editText: EditText){
       editText.text  = Editable.Factory.getInstance().newEditable(string)

    }


}