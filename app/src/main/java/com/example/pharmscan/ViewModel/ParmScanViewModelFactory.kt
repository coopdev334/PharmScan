package com.example.pharmscan.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pharmscan.Repository.PharmScanRepo

@Suppress("UNCHECKED_NAMES")
class ParmScanViewModelFactory(
    private val repo: PharmScanRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PharmScanViewModel(repo) as T
    }
}