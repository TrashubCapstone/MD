package com.android.trashub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.R
import com.android.trashub.adapter.TrashubAdapter
import com.android.trashub.data.Trashub
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class OrganikFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trashubList: ArrayList<Trashub>
    private lateinit var db: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_organik, container, false)

        recyclerView = rootView.findViewById(R.id.rvOrganik)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        trashubList = arrayListOf()

        db = FirebaseFirestore.getInstance()
        getTrashubData()

        return rootView
    }

    private fun getTrashubData() {
        recyclerView.visibility = View.GONE

        listenerRegistration = db.collection("sampah")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("OrganikFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    trashubList.clear()
                    for (doc in snapshot.documents) {
                        val trashubData = doc.toObject(Trashub::class.java)
                        if (trashubData != null) {
                            Log.d("OrganikFragment", "Adding data: $trashubData")
                            trashubList.add(trashubData)
                        }
                    }
                    val tAdapter = TrashubAdapter(trashubList)
                    recyclerView.adapter = tAdapter
                    recyclerView.visibility = View.VISIBLE
                } else {
                    Log.d("OrganikFragment", "No data found in snapshot")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listenerRegistration?.remove()
    }
}
