package com.mysuperdispatch.test.daxxtestapp.data.local.entites

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "posts")
class Post(@ColumnInfo(name = "title")
           val title: String,
           @ColumnInfo(name = "author")
           val author: String,
           @ColumnInfo(name = "publishedAt")
           val publishedAt: Long,
           @ColumnInfo(name = "index")
           val index: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
