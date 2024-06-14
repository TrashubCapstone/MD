package com.android.trashub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding.btnOrganik.setOnClickListener {
            // Tambahkan aksi yang diinginkan di sini
        }

        binding.btnAnorganik.setOnClickListener {
            // Tambahkan aksi yang diinginkan di sini
        }

        binding.btnB3.setOnClickListener {
            // Tambahkan aksi yang diinginkan di sini
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}