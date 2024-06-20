package com.android.trashub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.R
import com.android.trashub.adapter.TrashubAdapter
import com.android.trashub.data.Trashub
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class BFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trashubList: ArrayList<Trashub>
    private lateinit var db: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null

    // Number of columns for the grid
    private val numberOfColumns = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_organik, container, false)

        recyclerView = rootView.findViewById(R.id.rvOrganik)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), numberOfColumns)
        recyclerView.setHasFixedSize(true)

        trashubList = arrayListOf()

        db = FirebaseFirestore.getInstance()
        getTrashubData()

        return rootView
    }

    private fun getTrashubData() {
        recyclerView.visibility = View.GONE

        listenerRegistration = db.collection("sampah")
            .whereEqualTo("jenis_sampah", "B3") // Filter untuk mengambil data jenis sampah organik saja
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("BFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    trashubList.clear()
                    for (doc in snapshot.documents) {
                        val trashubData = doc.toObject(Trashub::class.java)
                        if (trashubData != null) {
                            Log.d("BFragment", "Adding data: $trashubData")
                            trashubList.add(trashubData)
                        }
                    }
                    val tAdapter = TrashubAdapter(trashubList) { trashub ->
                        navigateToResultFragment(trashub)
                    }
                    recyclerView.adapter = tAdapter
                    recyclerView.visibility = View.VISIBLE
                } else {
                    Log.d("BFragment", "No data found in snapshot")
                }
            }
    }

    private fun navigateToResultFragment(trashub: Trashub) {
        val bundle = Bundle().apply {
            putString("trashubId", trashub.id)
        }
        findNavController().navigate(R.id.action_b3Fragment_to_detailBFragment, bundle)
    }
        override fun onDestroyView() {
        super.onDestroyView()
        listenerRegistration?.remove()
    }
}