package com.android.trashub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.trashub.R
import com.android.trashub.databinding.FragmentReduceBinding

class ReduceFragment : Fragment() {
    private var _binding: FragmentReduceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReduceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnReduce.setOnClickListener {
            findNavController().navigate(R.id.action_reduceFragment_to_detailReduceFragment)
        }

        binding.btnReuse.setOnClickListener {
            findNavController().navigate(R.id.action_reduceFragment_to_detailReuseFragment)
        }

        binding.btnRecycle.setOnClickListener {
            findNavController().navigate(R.id.action_reduceFragment_to_detailRecycleFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}