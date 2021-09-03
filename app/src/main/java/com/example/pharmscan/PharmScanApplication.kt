package com.example.pharmscan

import android.app.Application
import android.content.Context
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Repository.PharmScanRepo
// TODO: remove this if deciding to not use this method of creating database
// currently done in mainactivity. If going to use it must update manifest file
// with android:name= ".PharmScanApplication"
//class PharmScanApplication : Application() {
//    // Using by lazy so the database and the repository are only created when they're needed
//    // rather than when the application starts
////    val database by lazy {
////        PharmScanDb.invoke(this) }
////    val repository by lazy {
////        PharmScanRepo(database.getHostCompNameDao()) }
//}