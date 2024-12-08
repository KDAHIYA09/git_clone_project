package com.example.gitclone.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitclone.R
import com.example.gitclone.RepositoryAdapterTest
import com.example.gitclone.api_end_points.GitHubApiService
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import com.example.gitclone.repositories.GitHubRepository
import com.example.gitclone.viewModel.GitHubViewModel
import com.example.gitclone.viewModel.GitHubViewModelFactory
import com.example.gitclone.utils.NetworkUtils

class MainActivity : AppCompatActivity(), RepositoryAdapterTest.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repositoryAdapter2: RepositoryAdapterTest
    private lateinit var searchView: SearchView
    private lateinit var viewModel: GitHubViewModel

    private var isLoading = false
    private var keyword: String = ""

    // ViewModel with Factory
    private val repositoryViewModel: GitHubViewModel by viewModels {
        val apiRepository = GitHubRepository(GitHubApiService.create())  // Pass only the API repository
        GitHubViewModelFactory(apiRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        val searchTextView = searchView.findViewById<android.widget.TextView>(androidx.appcompat.R.id.search_src_text)
        searchTextView.setTextColor(resources.getColor(R.color.black))
        searchTextView.setHintTextColor(resources.getColor(R.color.dark_on_surface))

        repositoryAdapter2 = RepositoryAdapterTest(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = repositoryAdapter2

        // Set the scroll listener to handle pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // If the user has scrolled to the bottom of the list, load more repositories
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - 1) {
                    // Get the current page number from the ViewModel
                    val currentPage = repositoryViewModel.getCurrentPage()  // Access the currentPage
                    repositoryViewModel.fetchRepositories(keyword, currentPage)  // Fetch next page of repositories
                }
            }
        })

        viewModel = ViewModelProvider(this, GitHubViewModelFactory(GitHubRepository(GitHubApiService.create()))).get(GitHubViewModel::class.java)

        observeViewModel()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        if (NetworkUtils.isNetworkAvailable(this@MainActivity)) {
                            fetchRepositories(it)
                        } else {
                            Toast.makeText(this@MainActivity, "No internet connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeViewModel() {
        repositoryViewModel.repositories.observe(this, Observer { repositories ->
            repositories?.let {
                isLoading = false
                repositoryAdapter2.appendData(it)
            }
        })

        repositoryViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        repositoryViewModel.loading.observe(this, Observer { isLoading ->
            // Show or hide the progress bar based on loading state
            findViewById<ProgressBar>(R.id.progressBar).visibility =
                if (isLoading) View.VISIBLE else View.GONE
        })
    }

    private fun fetchRepositories(query: String, page: Int = 1) {
        keyword = query
        isLoading = true
        repositoryViewModel.fetchRepositories(query, page)
    }

    override fun onItemClick(repository: RepositoriesDataClass) {
        val intent = Intent(this, repository_details_screen::class.java).apply {
            putExtra("REPO_NAME", repository.name)
            putExtra("OWNER_NAME", repository.ownerName)
            putExtra("DESCRIPTION", repository.description)
            putExtra("STARS", repository.stars)
            putExtra("LANGUAGE", repository.language)
            putExtra("PROJECT_URL", repository.projectUrl)
            putExtra("PROFILE_IMAGE_URL", repository.profileImageUrl)
        }
        startActivity(intent)
    }
}
