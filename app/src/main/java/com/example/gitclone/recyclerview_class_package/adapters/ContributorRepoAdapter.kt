package com.example.gitclone.recyclerview_class_package.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitclone.R
import com.example.gitclone.recyclerview_class_package.data_class_model.ContributorRepoCardData

class ContributorRepoAdapter : RecyclerView.Adapter<ContributorRepoAdapter.RepoViewHolder>() {

    private val repoList = mutableListOf<ContributorRepoCardData>()

    fun setRepos(repos: List<ContributorRepoCardData>) {
        repoList.clear()
        repoList.addAll(repos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contributer_repository, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: ImageView = itemView.findViewById(R.id.profile_image_cd)
        private val ownerName: TextView = itemView.findViewById(R.id.textOwnerName_cd)
        private val repoName: TextView = itemView.findViewById(R.id.textRepoName_cd)
        private val repoDescription: TextView = itemView.findViewById(R.id.textRepoDescription_cd)

        fun bind(repo: ContributorRepoCardData) {
            ownerName.text = repo.ownerName
            repoName.text = repo.repoName
            repoDescription.text = repo.repoDescription ?: "No Description"
            Glide.with(itemView.context)
                .load(repo.profileImageUrl)
                .placeholder(R.drawable.user_profile)
                .into(profileImage)
        }
    }
}
