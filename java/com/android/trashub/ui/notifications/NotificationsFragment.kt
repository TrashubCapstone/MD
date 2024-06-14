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
            Profile(1, "Profile 1", R.mipmap.ic_1_round),
            Profile(2, "Profile 2", R.mipmap.ic_2_round),
            Profile(3, "Profile 3", R.mipmap.ic_3_round),
            Profile(4, "Profile 4", R.mipmap.ic_4_round),
            Profile(5, "Profile 5", R.mipmap.ic_5_round),
            Profile(6, "Profile 6", R.mipmap.ic_6_round),
            Profile(7, "Profile 7", R.mipmap.ic_7_round),
            Profile(8, "Profile 8", R.mipmap.ic_8_round),
            Profile(9, "Profile 9", R.mipmap.ic_9_round),
            Profile(10, "Profile 10", R.mipmap.ic_10_round),
            Profile(11, "Profile 11", R.mipmap.ic_11_round)
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