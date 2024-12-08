package com.example.gitclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitclone.api_end_points.GitHubApiService
import com.example.gitclone.database.repositries_database
import com.example.gitclone.model_class.Contributor
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import com.example.gitclone.repositories.GitHubRepository
import com.example.gitclone.repositories.repositories_RepositoryClass
import com.example.gitclone.viewModel.GitHubViewModel
import com.example.gitclone.viewModel.GitHubViewModelFactory
import com.example.gitclone.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), RepositoryAdapterTest.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repositoryAdapter2: RepositoryAdapterTest
    private lateinit var searchView: SearchView
    private lateinit var viewModel: GitHubViewModel
    private var currentPage = 1
    private var isLoading = false
    private var keyword: String = ""

    // ViewModel with Factory
    private val repositoryViewModel: GitHubViewModel by viewModels {
        val apiRepository = GitHubRepository(
            GitHubApiService.create(),
            repositories_RepositoryClass(repositries_database.getDatabaseInstance(applicationContext).repositoriesDao())
        )
        val dbRepository = repositories_RepositoryClass(
            repositries_database.getDatabaseInstance(applicationContext).repositoriesDao()
        )
        GitHubViewModelFactory(apiRepository, dbRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        repositoryAdapter2 = RepositoryAdapterTest(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = repositoryAdapter2

        viewModel = repositoryViewModel

        observeViewModel()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        if (NetworkUtils.isNetworkAvailable(this@MainActivity)) {
                            fetchRepositories(it)
                        } else {
                            // If no internet, fetch from the database
                            fetchRepositoriesFromDatabase(it)
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
        viewModel.repositories.observe(this, Observer { repositories ->
            repositories?.let {
                isLoading = false
                repositoryAdapter2.updateData(it)
            }
        })

        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchRepositories(query: String, page: Int = 1) {
        keyword = query
        isLoading = true
        viewModel.fetchRepositories(query, page)
    }

    private fun fetchRepositoriesFromDatabase(query: String) {
        // Fetch repositories from the database for the given keyword in a background thread
        CoroutineScope(Dispatchers.IO).launch {
            val repositoriesFromDb = viewModel.getRepositoriesForKeyword(query)
            withContext(Dispatchers.Main) {
                // Update UI on the main thread
                if (repositoriesFromDb.isNotEmpty()) {
                    repositoryAdapter2.updateData(repositoriesFromDb)
                } else {
                    Toast.makeText(this@MainActivity, "No repositories found in database", Toast.LENGTH_SHORT).show()
                }
            }
        }
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



