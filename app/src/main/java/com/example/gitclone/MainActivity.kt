package com.example.gitclone

// MainActivity.kt
import RepositoryAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass

class MainActivity : AppCompatActivity(), RepositoryAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repositoryAdapter: RepositoryAdapter
    private val repositoryList = listOf(
        // Populate this list with your dummy data or API data
        RepositoriesDataClass(
            name = "Repo 1",
            description = "Description 1",
            language = "Kotlin",
            stars = 1200,
            updatedAt = "1 day ago",
            profileImageUrl = "https://example.com/profile1.jpg",
            ownerName = "Owner 1",
            projectUrl = "https://github.com/example/repo1"
        ),
        RepositoriesDataClass(
            name = "Repo 1",
            description = "Description 1",
            language = "Kotlin",
            stars = 1200,
            updatedAt = "1 day ago",
            profileImageUrl = "https://example.com/profile1.jpg",
            ownerName = "Owner 1",
            projectUrl = "https://github.com/example/repo1"
        ),
        RepositoriesDataClass(
            name = "Repo 1",
            description = "Description 1",
            language = "Kotlin",
            stars = 1200,
            updatedAt = "1 day ago",
            profileImageUrl = "https://example.com/profile1.jpg",
            ownerName = "Owner 1",
            projectUrl = "https://github.com/example/repo1"
        ),
        RepositoriesDataClass(
            name = "Repo 1",
            description = "Description 1",
            language = "Kotlin",
            stars = 1200,
            updatedAt = "1 day ago",
            profileImageUrl = "https://example.com/profile1.jpg",
            ownerName = "Owner 1",
            projectUrl = "https://github.com/example/repo1"
        ),
        RepositoriesDataClass(
            name = "Repo 1",
            description = "Description 1",
            language = "Kotlin",
            stars = 1200,
            updatedAt = "1 day ago",
            profileImageUrl = "https://example.com/profile1.jpg",
            ownerName = "Owner 1",
            projectUrl = "https://github.com/example/repo1"
        ),
        RepositoriesDataClass(
            name = "Repo 2",
            description = "Description 2",
            language = "Java",
            stars = 500,
            updatedAt = "3 days ago",
            profileImageUrl = "https://example.com/profile2.jpg",
            ownerName = "Owner 2",
            projectUrl = "https://github.com/example/repo2"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // Set up the RecyclerView with the Adapter and LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)
        repositoryAdapter = RepositoryAdapter(repositoryList, this)
        recyclerView.adapter = repositoryAdapter
    }

    // Handle item clicks
    override fun onItemClick(repository: RepositoriesDataClass) {
        // Handle the item click event (e.g., navigate to RepoDetails activity)
    }
}
