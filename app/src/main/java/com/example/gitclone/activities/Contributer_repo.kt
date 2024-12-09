package com.example.gitclone.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitclone.R
import com.example.gitclone.api_end_points.GitHubApiService
import com.example.gitclone.recyclerview_class_package.adapters.ContributorRepoAdapter
import com.example.gitclone.retrofitClient.ApiClient
import com.example.gitclone.viewModel.ContributorRepoViewModel
import com.example.gitclone.viewModel.ContributorRepoViewModelFactory

class contributer_repo : AppCompatActivity() {

    private lateinit var viewModel: ContributorRepoViewModel
    private lateinit var adapter: ContributorRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contributer_repo)

        // Find the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar2)

        // Set the toolbar as the action bar
        setSupportActionBar(toolbar)

        // Set the title
        supportActionBar?.title = "Contributer Contributions"

        val apiService = ApiClient.getClient().create(GitHubApiService::class.java)
        val factory = ContributorRepoViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, factory)[ContributorRepoViewModel::class.java]

        adapter = ContributorRepoAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val noDataText = findViewById<TextView>(R.id.noContributorsText1)

        viewModel.repoList.observe(this) { repos ->
            adapter.setRepos(repos)
            noDataText.visibility = if (repos.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch repositories
        val login = "sample_login" // Replace with actual login
        viewModel.fetchRepositories(login)
    }
}
class ContributorRepoActivity : AppCompatActivity() {

    private lateinit var viewModel: ContributorRepoViewModel
    private lateinit var adapter: ContributorRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contributer_repo)

        val apiService = ApiClient.getClient().create(GitHubApiService::class.java)
        val factory = ContributorRepoViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, factory)[ContributorRepoViewModel::class.java]

        adapter = ContributorRepoAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val noDataText = findViewById<TextView>(R.id.noContributorsText1)

        viewModel.repoList.observe(this) { repos ->
            adapter.setRepos(repos)
            noDataText.visibility = if (repos.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch repositories
        val login = "sample_login" // Replace with actual login
        viewModel.fetchRepositories(login)
    }
}
