package com.android.trashub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.trashub.R
import com.android.trashub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnJenisContainer.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_jenisFragment)
        }

        binding.btn3RContainer.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_reduceFragment)
        }

        binding.btnInformasiContainer.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_newsFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}