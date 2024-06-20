package com.android.trashub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.trashub.adapter.TrashubAdapter
import com.android.trashub.data.Trashub
import com.android.trashub.databinding.FragmentDetailJenisBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class DetailAnorganikFragment : Fragment() {
    private var _binding: FragmentDetailJenisBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null
    private lateinit var trashub: Trashub

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailJenisBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = FirebaseFirestore.getInstance()

        // Retrieve data from bundle
        val trashubId = arguments?.getString("trashubId")

        trashubId?.let { id ->
            getTrashubData(id)
        }

        return root
    }

    private fun getTrashubData(trashubId: String) {
        db.collection("sampah").document(trashubId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    trashub = document.toObject(Trashub::class.java)!!

                    // Set data to views
                    binding.result.text = trashub.nama_sampah
                    binding.resultJenis.text = trashub.jenis_sampah
                    binding.resultKategori.text = trashub.kategori_sampah
                    binding.resultKeterangan.text = trashub.keterangan_sampah

                    Glide.with(requireContext())
                        .load(trashub.imageUrl)
                        .into(binding.image)
                } else {
                    Log.d("DetailAnorganikFragment", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("DetailAnorganikFragment", "get failed with ", exception)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listenerRegistration?.remove()
    }
}
