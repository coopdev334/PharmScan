package com.example.pharmscan.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Repository.PharmScanRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PharmScanViewModel(
    private val repo: PharmScanRepo
): ViewModel() {

    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private var _hostCompName = MutableLiveData(listOf<HostCompName>())
    val hostCompName: LiveData<List<HostCompName>> = _hostCompName

    // onNameAdd is an event we're defining that the UI can invoke
    // (events flow up from UI)
    // This gets all rows from database and updates live data
    // which is observed buy hostCompNameList in NavHostMainScreen
    // to recompose LazyColumn list
    fun onNameAdd() {
        _hostCompName.value = getAllHostCompName()
    }


    // HostCompName viewModel db interace
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.IO).launch {
        repo.insert(hostCompName)
    }
    fun deleteHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.IO).launch {
        repo.delete(hostCompName)
    }

    fun getAllHostCompName() = repo.getAll()

 }