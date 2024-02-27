package com.silvia.appproducts.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.silvia.appproducts.MainActivity
import com.silvia.appproducts.R
import com.silvia.appproducts.data.ProductAdapter
import com.silvia.appproducts.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val PERMISSION_CODE = 1001
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Verificar si tienes permisos de almacenamiento

            homeViewModel.updateProductos()
            initProductos(homeViewModel) // Si ya tienes permisos, realiza las operaciones que requieren permisos aquí

        binding.btnAdd.setOnClickListener(){
            homeViewModel.setIsEditable(false)
            findNavController().navigate(R.id.action_nav_home_to_homeDetailsFragment)
        }
        binding.btnUpdate.setOnClickListener { view ->
            homeViewModel.updateProductos()
            Snackbar.make(view, "Se ha actualizado los productos", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        return root
    }
    fun initProductos(homeViewModel:HomeViewModel){
        homeViewModel.productos.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                binding.productsList.adapter = ProductAdapter(it,lifecycleScope, homeViewModel)
                binding.productsList.layoutManager = LinearLayoutManager(requireActivity())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}