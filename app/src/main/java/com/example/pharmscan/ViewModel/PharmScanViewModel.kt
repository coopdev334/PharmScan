package com.example.pharmscan.ViewModel

import androidx.lifecycle.ViewModel
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Repository.PharmScanRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PharmScanViewModel(
    private val repo: PharmScanRepo
): ViewModel() {
    // HostCompName viewModel db interace
    fun insertHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.Main).launch {
        repo.insertHostCompName(hostCompName)
    }
    fun deleteHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.Main).launch {
        repo.deleteHostCompName(hostCompName)
    }
    fun getAllHostCompName() = repo.getAllHostCompName()
 }