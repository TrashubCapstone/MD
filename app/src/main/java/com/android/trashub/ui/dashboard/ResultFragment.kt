package com.android.trashub.ui.dashboard

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.trashub.adapter.TrashubAdapter
import com.android.trashub.data.Trashub
import com.android.trashub.databinding.FragmentHasilBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ResultFragment : Fragment() {
    private var _binding: FragmentHasilBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null

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

        db = FirebaseFirestore.getInstance()

        resultText?.let {
            fetchTrashubData(it)
        }

        return root
    }

    private fun fetchTrashubData(resultText: String) {
        val trashubList = ArrayList<Trashub>()
        listenerRegistration = db.collection("sampah")
            .whereEqualTo("nama_sampah", resultText) // Use resultText here
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("ResultFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    trashubList.clear()
                    for (doc in snapshot.documents) {
                        val trashubData = doc.toObject(Trashub::class.java)
                        if (trashubData != null) {
                            Log.d("ResultFragment", "Adding data: $trashubData")
                            trashubList.add(trashubData)
                        }
                    }
                    if (trashubList.isNotEmpty()) {
                        val firstItem = trashubList[0]
                        binding.resultJenis.text = firstItem.jenis_sampah
                        binding.resultKategori.text = firstItem.kategori_sampah
                        binding.resultKeterangan.text = firstItem.keterangan_sampah
                    } else {
                        Log.d("ResultFragment", "No matching documents found.")
                    }
                } else {
                    Log.d("ResultFragment", "No data found in snapshot")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listenerRegistration?.remove()
    }
}