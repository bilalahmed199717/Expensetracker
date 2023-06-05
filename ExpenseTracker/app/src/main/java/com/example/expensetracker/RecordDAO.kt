package com.example.expensetracker

import androidx.room.*

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: RecordEntity): Long

//    @Query("Select * from records")
//    suspend fun getAllRecords(): List<RecordEntity>

    @Query("Select * from records where type = :type")
    suspend fun getAllExpenseRecords(type: String): List<RecordEntity>

    @Query("Select * from records where type = :type and category = :category")
    suspend fun getAllExpenseRecordsWithCategory(type: String, category: String): List<RecordEntity>
}