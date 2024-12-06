package com.example.gitclone.recyclerview_class_package.data_class_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoriesDataClass(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Unique ID (GitHub Repository ID or similar)
    val keyword: String,               // Search keyword used
    val name: String,                  // Repository name
    val description: String?,          // Nullable description
    val language: String?,             // Nullable programming language
    val stars: Int,                    // Number of stars
    val updatedAt: String,             // Last updated timestamp
    val profileImageUrl: String,       // Profile image URL
    val ownerName: String,             // Owner's name
    val projectUrl: String             // Link to the project on GitHub
)


//for database
//1 add dependency
//2 define data class - use adapter wali data class but add primary key see ex. above and specify table name for this data table
//3 create dao interface- here we need insert and get operations only so we will make these two methods
//4 create and instantiate room database