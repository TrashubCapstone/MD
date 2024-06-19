package com.android.trashub.ui.dashboard

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.trashub.databinding.FragmentHasilBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentHasilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHasilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bitmap = arguments?.getParcelable<Bitmap>("image")
        val resultText = arguments?.getString("result")

        binding.image.setImageBitmap(bitmap)
        binding.result.text = resultText

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}