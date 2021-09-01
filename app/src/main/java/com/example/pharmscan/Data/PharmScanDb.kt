package com.example.pharmscan.Data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pharmscan.Data.DAO.HostCompNameDao
import com.example.pharmscan.Data.Tables.HostCompName

// List all tables here and update version for each database change
@Database(
    entities = [
        HostCompName::class
    ],
    version = 1
)
abstract class PharmScanDb: RoomDatabase() {
    // These functions will return the Dao's for each table
    abstract fun getHostCompNameDaoDao(): HostCompNameDao

    // companion object is a static object accessed in this class
    companion object {
        // @Volatile means all thread that may have this instance var will get
        // updated immediately. This instance is of type PharmScanDb class
        @Volatile
        private var instance: PharmScanDb? = null
        private val LOCK = Any()

        // When this database class is created the invoke function will automatically
        // be called. If instance is null create the database using synchronized(LOCK)
        // so this cannot be called by another thread at the same time. If instance is
        // not null then database was already created and just return the instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        // This function calls the Room database builder which will create the db
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PharmScanDb::class.java,
                "PharmScanDb.db"
            ).build()
    }
}