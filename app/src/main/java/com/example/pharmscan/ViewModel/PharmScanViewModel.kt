package com.example.pharmscan.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmscan.Data.Tables.*
import com.example.pharmscan.Repository.PharmScanRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PharmScanViewModel(
    private val repo: PharmScanRepo
): ViewModel() {

    // **********************************************************************
    // HostCompName
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private var _hostCompName = MutableLiveData(listOf<HostCompName>())
    val hostCompName: LiveData<List<HostCompName>> = _hostCompName


    // This gets all rows from database and updates live data
    // which is observed by hostCompNameList in NavHostMainScreen
    // to recompose LazyColumn list
    fun updateHostCompNameLiveData() {
        _hostCompName.value = getAllHostCompName()
    }

    // HostCompName viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertHostCompName(hostCompName)
    }
    fun deleteHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteHostCompName(hostCompName)
    }

    private fun getAllHostCompName() = repo.getAllHostCompName()


    // **********************************************************************
    // CollectedData
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private var _collectedData = MutableLiveData(listOf<CollectedData>())
    val collectedData: LiveData<List<CollectedData>> = _collectedData

    fun updateCollectedDataLiveData() {
        _collectedData.value = getAllCollectedData()
    }

    // CollectedData viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertCollectedData(collectedData: CollectedData) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertCollectedData(collectedData)
    }
    fun deleteCollectedData(collectedData: CollectedData) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteCollectedData(collectedData)
    }

    fun getAllCollectedData() = repo.getAllCollectedData()
    fun getAllCollectedDataOrderByRecCnt() = repo.getAllCollectedDataOrderByRecCnt()
    fun getAllCollectedDataOrderByTag() = repo.getAllCollectedDataOrderByTag()
    fun getAllCollectedDataOrderByNdc() = repo.getAllCollectedDataOrderByNdc()
    fun getColDataLastInsertedRow() = repo.getColDataLastInsertedRow()
    // TODO: use IO dispatcher when Toast removed
    fun uploadCollectedData() = CoroutineScope(Dispatchers.Main).launch {
        repo.uploadCollectedData()
    }


    // **********************************************************************
    // SystemInfo
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private var _systemInfo = MutableLiveData(listOf<SystemInfo>())
    val systemInfo: LiveData<List<SystemInfo>> = _systemInfo


    // This gets all rows from database and updates live data
    // which is observed by systemInfoList
    // to recompose LazyColumn list
    fun updateSystemInfoLiveData() {
        _systemInfo.value = getAllSystemInfo()
    }

    // SystemInfo viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertSystemInfo(systemInfo: SystemInfo) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertSystemInfo(systemInfo)
    }
    fun deleteSystemInfo(systemInfo: SystemInfo) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteSystemInfo(systemInfo)
    }

    fun getAllSystemInfo() = repo.getAllSystemInfo()


    // **********************************************************************
    // PSNdc
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private var _psNdc = MutableLiveData(listOf<PSNdc>())
    val psNdc: LiveData<List<PSNdc>> = _psNdc


    // This gets all rows from database and updates live data
    // which is observed by systemInfoList
    // to recompose LazyColumn list
    fun updatePSNdcLiveData() {
        _psNdc.value = getAllPSNdc()
    }

    // PSNdc viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertPSNdc(psNdc: PSNdc) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertPSNdc(psNdc)
    }
    fun deletePSNdc(psNdc: PSNdc) = CoroutineScope(Dispatchers.IO).launch {
        repo.deletePSNdc(psNdc)
    }

    fun getAllPSNdc() = repo.getAllPSNdc()


    // **********************************************************************
    // Settings
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private var _settings = MutableLiveData(listOf<Settings>())
    val settings: LiveData<List<Settings>> = _settings


    // This gets all rows from database and updates live data
    // which is observed by hostCompNameList in NavHostMainScreen
    // to recompose LazyColumn list
    fun updateSettingsLiveData() {
        _settings.value = getSettingsRow()
    }

    // HostCompName viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertSettings(hostCompName: Settings) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertSettings(hostCompName)
    }
    fun deleteSettings(hostCompName: Settings) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteSettings(hostCompName)
    }

    fun getSettingsRow() = repo.getSettingsRow()

 }