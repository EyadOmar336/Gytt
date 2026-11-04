package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ChatSessionEntity::class,
        ChatMessageEntity::class,
        ExperimentEntity::class,
        LessonProgressEntity::class,
        QuizAttemptEntity::class,
        BookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GeneLabDatabase : RoomDatabase() {
    abstract fun dao(): GeneLabDao

    companion object {
        @Volatile
        private var INSTANCE: GeneLabDatabase? = null

        fun getDatabase(context: Context): GeneLabDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeneLabDatabase::class.java,
                    "genelab_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
