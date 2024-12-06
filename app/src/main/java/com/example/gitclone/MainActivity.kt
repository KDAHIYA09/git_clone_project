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
        RepositoriesDataClass(
            keyword = "example",
            name = "Repo 1",
            description = "This is the first repository example.",
            language = "Kotlin",
            stars = 1200,
            updatedAt = "1 day ago",
            profileImageUrl = "https://example.com/profile1.jpg",
            ownerName = "Owner 1",
            projectUrl = "https://github.com/example/repo1"
        ),
        RepositoriesDataClass(
            keyword = "example",
            name = "Repo 2",
            description = "A repository example showcasing Java.",
            language = "Java",
            stars = 500,
            updatedAt = "3 days ago",
            profileImageUrl = "https://example.com/profile2.jpg",
            ownerName = "Owner 2",
            projectUrl = "https://github.com/example/repo2"
        ),
        RepositoriesDataClass(
            keyword = "example",
            name = "Repo 3",
            description = "This is a Python project repository.",
            language = "Python",
            stars = 150,
            updatedAt = "5 days ago",
            profileImageUrl = "https://example.com/profile3.jpg",
            ownerName = "Owner 3",
            projectUrl = "https://github.com/example/repo3"
        ),
        RepositoriesDataClass(
            keyword = "example",
            name = "Repo 4",
            description = "A popular JavaScript project.",
            language = "JavaScript",
            stars = 2300,
            updatedAt = "7 days ago",
            profileImageUrl = "https://example.com/profile4.jpg",
            ownerName = "Owner 4",
            projectUrl = "https://github.com/example/repo4"
        ),
        RepositoriesDataClass(
            keyword = "example",
            name = "Repo 5",
            description = "This is a Swift repository example.",
            language = "Swift",
            stars = 900,
            updatedAt = "10 days ago",
            profileImageUrl = "https://example.com/profile5.jpg",
            ownerName = "Owner 5",
            projectUrl = "https://github.com/example/repo5"
        ),
        RepositoriesDataClass(
            keyword = "example",
            name = "Repo 6",
            description = "An interesting Ruby project.",
            language = "Ruby",
            stars = 300,
            updatedAt = "12 days ago",
            profileImageUrl = "https://example.com/profile6.jpg",
            ownerName = "Owner 6",
            projectUrl = "https://github.com/example/repo6"
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
