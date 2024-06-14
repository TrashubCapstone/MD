package com.android.trashub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.databinding.ItemProfileBinding
import com.android.trashub.model.Profile

class ProfileAdapter(private val profileClickListener: ProfileClickListener) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var profileList: List<Profile> = listOf()

    inner class ProfileViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.name.text = profile.name
            binding.imageProfile.setImageResource(profile.imageUrl)
            binding.root.setOnClickListener {
                profileClickListener.onProfileClick(profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(profileList[position])
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    fun submitList(list: List<Profile>) {
        profileList = list
        notifyDataSetChanged()
    }

    interface ProfileClickListener {
        fun onProfileClick(profile: Profile)
    }
}