package com.example.gitclone.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitclone.R
import com.example.gitclone.viewModel.GitHubViewModel
import com.example.gitclone.viewModel.GitHubViewModelFactory
import com.example.gitclone.recyclerview_class_package.adapter.ContributorAdapter
import com.example.gitclone.api_end_points.GitHubApiService
import com.example.gitclone.model_class.Contributor
import com.example.gitclone.repositories.GitHubRepository

class repository_details_screen : AppCompatActivity(), ContributorAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contributorAdapter: ContributorAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var noContributorsText: TextView
    private var repoName: String = ""
    private var ownerName: String = ""

    // ViewModel with Factory - Use the 'by viewModels()' delegate to initialize the ViewModel
    private val githubViewModel: GitHubViewModel by viewModels {
        val apiRepository = GitHubRepository(GitHubApiService.create())  // Pass only the API repository
        GitHubViewModelFactory(apiRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_details_screen)

        // Find the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar1)

        // Set the toolbar as the action bar
        setSupportActionBar(toolbar)

        // Set the title
        supportActionBar?.title = "Repository Details"

        // Get repository data from Intent
        repoName = intent.getStringExtra("REPO_NAME") ?: ""
        ownerName = intent.getStringExtra("OWNER_NAME") ?: ""
        val description = intent.getStringExtra("DESCRIPTION") ?: ""
        val stars = intent.getIntExtra("STARS", 0)
        val language = intent.getStringExtra("LANGUAGE") ?: ""
        val projectUrl = intent.getStringExtra("PROJECT_URL") ?: ""
        val profileImageUrl = intent.getStringExtra("PROFILE_IMAGE_URL")

        // Set repository details in the UI
        findViewById<TextView>(R.id.textRepoName).text = repoName
        findViewById<TextView>(R.id.textOwnerName).text = ownerName
        findViewById<TextView>(R.id.textRepoDescription).text = description
        findViewById<TextView>(R.id.textStars).text = stars.toString()
        findViewById<TextView>(R.id.textLanguage).text = language
        findViewById<TextView>(R.id.textProjectUrl).text = projectUrl
        Glide.with(this)
            .load(profileImageUrl)
            .into(findViewById(R.id.profile_image_detail))

        findViewById<TextView>(R.id.textProjectUrl).setOnClickListener {
            val projectUrl = "https://example.com" // Replace this with the actual URL you want to pass
            val intent = Intent(this, project_url_webview::class.java).apply {
                putExtra("PROJECT_URL", projectUrl)
            }
            startActivity(intent)
        }

        // Initialize ProgressBar and TextView for no contributors
        progressBar = findViewById(R.id.progressBar)
        noContributorsText = findViewById(R.id.noContributorsText)

        // Setup RecyclerView for Contributors
        recyclerView = findViewById(R.id.contributorsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        contributorAdapter = ContributorAdapter(mutableListOf())
        recyclerView.adapter = contributorAdapter

        // Observe contributors from the ViewModel
        observeContributors()

        // Fetch contributors
        githubViewModel.fetchContributors(ownerName, repoName)  // Correct ViewModel instance

        // Observe loading state for progress bar visibility
        githubViewModel.loading1.observe(this, Observer { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE  // Show progress bar while loading
            } else {
                progressBar.visibility = View.GONE  // Hide progress bar after loading is done
            }
        })

        githubViewModel.contributors.observe(this, Observer { contributors ->
            progressBar.visibility = View.GONE  // Hide progress bar when data is received

            if (contributors.isNullOrEmpty()) {
                // Show "No contributors" text and hide the RecyclerView
                noContributorsText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                // Update the RecyclerView with the new data
                contributorAdapter.updateData(contributors)
                noContributorsText.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        })

        // Set the item click listener for the adapter
        contributorAdapter.setOnItemClickListener(this)

    }

    private fun observeContributors() {
        githubViewModel.contributors.observe(this, Observer { contributors ->
            progressBar.visibility = View.GONE  // Hide progress bar when data is received

            if (contributors.isNullOrEmpty()) {
                // Show "No contributors" text and hide the RecyclerView
                noContributorsText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                // Update the RecyclerView with the new data
                contributorAdapter.updateData(contributors)
                noContributorsText.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        })

        // Observe loading state and show/hide progress bar
        githubViewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        // Observe error state and show error message if any
        githubViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    // Override onItemClick to handle item clicks
    override fun onItemClick(contributor: Contributor) {
        val intent = Intent(this, contributer_repo::class.java).apply {
            putExtra("CONTRIBUTOR_LOGIN", contributor.login) // Pass the contributor's login
        }
        startActivity(intent)
    }
}
