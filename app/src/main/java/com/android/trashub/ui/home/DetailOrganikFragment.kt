package com.android.trashub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.trashub.databinding.FragmentDetailOrganikBinding
import com.android.trashub.databinding.FragmentDetailRecycleBinding
import com.android.trashub.databinding.FragmentDetailReduceBinding

class DetailOrganikFragment : Fragment() {
    private var _binding: FragmentDetailOrganikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailOrganikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}