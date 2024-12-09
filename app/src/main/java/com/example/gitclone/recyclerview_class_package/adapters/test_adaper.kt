package com.example.gitclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import com.example.gitclone.R
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass

class RepositoryAdapterTest(
    private var repositoryList: MutableList<RepositoriesDataClass>, // Use MutableList here
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constants for item types
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    // Variable to handle loading state
    private var isLoading = false

    // Update this method to refresh the list dynamically
    fun updateData(newRepositories: List<RepositoriesDataClass>) {
        repositoryList.clear()
        repositoryList.addAll(newRepositories)
        notifyDataSetChanged()
    }

    // Append new data (for pagination)
    fun appendData(newRepositories: List<RepositoriesDataClass>) {
        repositoryList.addAll(newRepositories)
        isLoading = false
        notifyDataSetChanged()
    }

    // Show or hide the loading view in RecyclerView
    fun showLoading() {
        isLoading = true
        repositoryList.add(RepositoriesDataClass(
            keyword = "", name = "", description = "", language = "",
            stars = 0, updatedAt = "", profileImageUrl = "", ownerName = "", projectUrl = ""))
        notifyItemInserted(repositoryList.size - 1)
    }

    // Hide the loading view
    fun hideLoading() {
        if (repositoryList.isNotEmpty()) {
            repositoryList.removeAt(repositoryList.size - 1)
            isLoading = false
            notifyItemRemoved(repositoryList.size)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == repositoryList.size - 1) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
                RepositoryViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                val repository = repositoryList[position]
                val viewHolder = holder as RepositoryViewHolder

                viewHolder.ownerNameText.text = repository.ownerName
                viewHolder.repoNameText.text = repository.name
                viewHolder.repoDescriptionText.text = repository.description
                viewHolder.languageText.text = repository.language
                viewHolder.starsText.text = repository.stars.toString()
                viewHolder.lastUpdatedText.text = repository.updatedAt

                // Load profile image using Glide
                Glide.with(viewHolder.itemView.context)
                    .load(repository.profileImageUrl)
                    .into(viewHolder.profileImage)

                // Set up click listener for the whole item
                viewHolder.itemView.setOnClickListener {
                    itemClickListener.onItemClick(repository)
                }
            }

            VIEW_TYPE_LOADING -> {
                // Handle loading view
                val loadingViewHolder = holder as LoadingViewHolder
                // You can add logic to show a loading animation here
            }
        }
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    // ViewHolder for normal item
    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        val ownerNameText: TextView = itemView.findViewById(R.id.textOwnerName)
        val repoNameText: TextView = itemView.findViewById(R.id.textRepoName)
        val repoDescriptionText: TextView = itemView.findViewById(R.id.textRepoDescription)
        val languageText: TextView = itemView.findViewById(R.id.textLanguage)
        val starsText: TextView = itemView.findViewById(R.id.textStars)
        val lastUpdatedText: TextView = itemView.findViewById(R.id.textLastUpdated)
    }

    // ViewHolder for the loading view
    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(repository: RepositoriesDataClass)
    }
}
