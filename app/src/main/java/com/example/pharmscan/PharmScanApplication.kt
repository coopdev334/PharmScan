package com.example.pharmscan

import android.app.Application
import android.content.Context
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Repository.PharmScanRepo

class PharmScanApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
//    val database by lazy {
//        PharmScanDb.invoke(this) }
//    val repository by lazy {
//        PharmScanRepo(database.getHostCompNameDao()) }

    // This is used for Toast displays throughout application
    // Note if Toast is used in coroutine, ONLY Main dispatcher can be used
    companion object {

        var context: Context? = null

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    fun getAppContext(): Context? {
        return context
    }
}