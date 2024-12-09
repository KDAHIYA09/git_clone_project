package com.example.gitclone.recyclerview_class_package.data_class_model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "repositories")
data class RepositoriesDataClass(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val keyword: String,
    val name: String,
    val description: String?,
    val language: String?,
    val stars: Int,
    val updatedAt: String,
    val profileImageUrl: String,
    val ownerName: String,
    val projectUrl: String
)



//for database
//1 add dependency
//2 define data class - use adapter wali data class but add primary key see ex. above and specify table name for this data table
//3 create dao interface- here we need insert and get operations only so we will make these two methods
//4 create and instantiate room database