package com.example.gitclone

import RepositoryAdapter
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitclone.database.repositries_database
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import com.example.gitclone.repositories.repositories_RepositoryClass
import com.example.gitclone.viewModel.RepositoryViewModel
import com.example.gitclone.viewModel.RepositoryViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), RepositoryAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var searchView: SearchView

    // ViewModel with Factory
    private val repositoryViewModel: RepositoryViewModel by viewModels {
        val database = repositries_database.getDatabaseInstance(applicationContext)
        val repository = repositories_RepositoryClass(database.repositoriesDao())
        RepositoryViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        // Set up RecyclerView
        repositoryAdapter = RepositoryAdapter(mutableListOf(), this) // Use mutableListOf() for a MutableList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = repositoryAdapter

        // Insert dummy data
        insertDummyData()

        // Observe LiveData from ViewModel
        observeViewModel()

        // Set up SearchView to fetch data from the database
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { fetchRepositories(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeViewModel() {
        // Observe repositories list
        repositoryViewModel.repositories.observe(this, Observer { repositories ->
            repositoryAdapter.updateData(repositories)
        })

        // Observe noDataAvailable flag
        repositoryViewModel.noDataAvailable.observe(this, Observer { noData ->
            if (noData) {
                Toast.makeText(this, "No data available for the entered keyword", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchRepositories(keyword: String) {
        // Fetch repositories for the entered keyword
        repositoryViewModel.fetchRepositories(keyword)
    }

    private fun insertDummyData() {
        CoroutineScope(Dispatchers.IO).launch {
            val dummyData = List(10) { index ->
                RepositoriesDataClass(
                    keyword = "dummy",
                    name = "Dummy Repo $index",
                    description = "This is a description for Dummy Repo $index.",
                    language = "Kotlin",
                    stars = (100..1000).random(),
                    updatedAt = "${(1..10).random()} days ago",
                    profileImageUrl = "https://example.com/profile$index.jpg",
                    ownerName = "Owner $index",
                    projectUrl = "https://github.com/example/repo$index"
                )
            }
            val database = repositries_database.getDatabaseInstance(applicationContext)
            database.repositoriesDao().insertAll(dummyData)
        }
    }

    override fun onItemClick(repository: RepositoriesDataClass) {
        // Handle item click (e.g., navigate to another activity or show details)
        Toast.makeText(this, "Clicked on ${repository.name}", Toast.LENGTH_SHORT).show()
    }
}
