package com.android.trashub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.R
import com.android.trashub.adapter.TrashubAdapter
import com.android.trashub.data.Trashub
import com.google.firebase.database.*

class OrganikFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trashubList: ArrayList<Trashub>
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_organik, container, false)

        recyclerView = rootView.findViewById(R.id.rvOrganik)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        trashubList = arrayListOf()

        getTrashubData()

        return rootView
    }

    private fun getTrashubData() {
        recyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Trashub")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trashubList.clear()
                if (snapshot.exists()) {
                    for (trashubSnap in snapshot.children) {
                        val trashubData = trashubSnap.getValue(Trashub::class.java)
                        if (trashubData != null) {
                            trashubList.add(trashubData)
                        }
                    }
                    val tAdapter = TrashubAdapter(trashubList)
                    recyclerView.adapter = tAdapter

                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}