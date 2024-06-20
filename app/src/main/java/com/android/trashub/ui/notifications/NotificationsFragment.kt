package com.android.trashub.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.trashub.MainActivity
import com.android.trashub.R
import com.android.trashub.adapter.ProfileAdapter
import com.android.trashub.databinding.FragmentNotificationsBinding
import com.android.trashub.model.Profile

class NotificationsFragment : Fragment(), ProfileAdapter.ProfileClickListener {

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        val adapter = ProfileAdapter(this)
        binding.recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerView.layoutManager = layoutManager

        val profileList = listOf(
            Profile(1, "Lion", R.drawable.lion),
            Profile(2, "Bear", R.drawable.bear),
            Profile(3, "Cat", R.drawable.cat),
            Profile(4, "Dog", R.drawable.dog),
            Profile(5, "Wolf", R.drawable.wolf),
            Profile(6, "Koala", R.drawable.koala),
            Profile(7, "Monkey", R.drawable.salsa),
            Profile(8, "Crocodile", R.drawable.salsa2),
            Profile(9, "Tiger", R.drawable.tiger),
            Profile(10, "Elephant", R.drawable.gajah),
            Profile(11, "Deer", R.drawable.rusa)
        )
        adapter.submitList(profileList)
    }

    override fun onProfileClick(profile: Profile) {
        val intent = Intent(activity, MainActivity::class.java).apply {
            putExtra("profile_image", profile.imageUrl)
        }
        activity?.startActivity(intent)
    }
}