package com.example.gitclone.recyclerview_class_package.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitclone.R
import com.example.gitclone.model_class.Contributor

class ContributorAdapter(private var contributors: MutableList<Contributor>) :
    RecyclerView.Adapter<ContributorViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    // Interface for item click listener
    interface OnItemClickListener {
        fun onItemClick(contributor: Contributor)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contributor, parent, false)
        return ContributorViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(contributors[position])
    }

    override fun getItemCount(): Int = contributors.size

    fun updateData(newContributors: List<Contributor>) {
        contributors.clear()
        contributors.addAll(newContributors)
        notifyDataSetChanged()
    }
}

class ContributorViewHolder(itemView: View, private val onItemClickListener: ContributorAdapter.OnItemClickListener?) :
    RecyclerView.ViewHolder(itemView) {

    private val avatarImageView: ImageView = itemView.findViewById(R.id.contributer_image)
    private val loginTextView: TextView = itemView.findViewById(R.id.contributerName)
    private val contributionsTextView: TextView = itemView.findViewById(R.id.contributionsCount)

    fun bind(contributor: Contributor) {
        // Load avatar image
        Glide.with(itemView.context)
            .load(contributor.avatarUrl)
            .error(R.drawable.user_profile) // Optional image in case of error
            .into(avatarImageView)

        // Set contributor details
        loginTextView.text = contributor.login
        contributionsTextView.text = "Contributions: ${contributor.contributions}"

        // Set the click listener for the item view
        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(contributor)
        }
    }
}
