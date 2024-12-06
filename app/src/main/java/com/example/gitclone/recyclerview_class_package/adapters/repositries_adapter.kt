// RepositoryAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import com.example.gitclone.R
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass

class RepositoryAdapter(
    private val repositoryList: List<RepositoriesDataClass>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    // Create the ViewHolder for each repository item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    // Bind the repository data to the views
    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositoryList[position]

        holder.ownerNameText.text = repository.ownerName
        holder.repoNameText.text = repository.name
        holder.repoDescriptionText.text = repository.description
        holder.languageText.text = repository.language
        holder.starsText.text = repository.stars.toString()
        holder.lastUpdatedText.text = repository.updatedAt

        // Load profile image using Glide
        Glide.with(holder.itemView.context)
            .load(repository.profileImageUrl)
            .into(holder.profileImage)

        // Set up click listener for the whole item
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(repository)
        }
    }

    // Return the size of the repository list
    override fun getItemCount(): Int {
        return repositoryList.size
    }

    // Define the ViewHolder to hold references to the views
    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        val ownerNameText: TextView = itemView.findViewById(R.id.textOwnerName)
        val repoNameText: TextView = itemView.findViewById(R.id.textRepoName)
        val repoDescriptionText: TextView = itemView.findViewById(R.id.textRepoDescription)
        val languageText: TextView = itemView.findViewById(R.id.textLanguage)
        val starsText: TextView = itemView.findViewById(R.id.textStars)
        val lastUpdatedText: TextView = itemView.findViewById(R.id.textLastUpdated)
    }

    // Interface for item click listener
    interface OnItemClickListener {
        fun onItemClick(repository: RepositoriesDataClass)
    }
}
