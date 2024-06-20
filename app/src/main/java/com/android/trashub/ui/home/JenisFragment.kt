package com.android.trashub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.trashub.R
import com.android.trashub.databinding.FragmentJenisBinding

class JenisFragment : Fragment() {

    private var _binding: FragmentJenisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJenisBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnOrganikContainer.setOnClickListener {
            findNavController().navigate(R.id.action_jenisFragment_to_organikFragment)
        }

        binding.btnAnorganikContainer.setOnClickListener {
            findNavController().navigate(R.id.action_jenisFragment_to_anorganikFragment)
        }

        binding.btnB3Container.setOnClickListener {
            findNavController().navigate(R.id.action_jenisFragment_to_b3Fragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}