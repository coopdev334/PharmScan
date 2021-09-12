package com.example.pharmscan.ViewModel

import androidx.lifecycle.LiveData
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
    var hostCompName: LiveData<List<HostCompName>> = getAllLiveDataHostCompName()

    // Settings viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertHostCompName(hostCompName)
    }
    fun deleteRowHostCompName(hostCompName: HostCompName) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteRowHostCompName(hostCompName)
    }

    fun deleteAllHostCompName() = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteAllHostCompName()
    }

    fun getAllLiveDataHostCompName() = repo.getAllLiveDataHostCompName()
    fun getAllHostCompName() = repo.getAllHostCompName()


    // **********************************************************************
    // CollectedData
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    var collectedData: LiveData<List<CollectedData>> = getAllLiveDataCollectedData()

    // CollectedData viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertCollectedData(collectedData: CollectedData) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertCollectedData(collectedData)
    }
    fun deleteCollectedDataRow(collectedData: CollectedData) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteCollectedDataRow(collectedData)
    }

    fun deleteAllCollectedData() = repo.deleteAllCollectedData()
    fun getAllLiveDataCollectedData() = repo.getAllLiveDataCollectedData()
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
    var systemInfo: LiveData<List<SystemInfo>> = getLiveDataSystemInfoRow()

    // Settings viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertSystemInfo(systemInfo: SystemInfo) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertSystemInfo(systemInfo)
    }
    fun deleteRowSystemInfo(systemInfo: SystemInfo) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteRowSystemInfo(systemInfo)
    }

    fun deleteAllSystemInfo() = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteAllSystemInfo()
    }

    fun getLiveDataSystemInfoRow() = repo.getLiveDataSystemInfoRow()
    fun getSystemInfoRow() = repo.getSystemInfoRow()


    // **********************************************************************
    // PSNdc
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    var psNdc: LiveData<List<PSNdc>> = getAllLiveDataPSNdc()

    // PSNdc viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertPSNdc(psNdc: PSNdc) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertPSNdc(psNdc)
    }
    fun deleteRowPSNdc(psNdc: PSNdc) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteRowPSNdc(psNdc)
    }

    fun deleteAllPSNdc() = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteAllPSNdc()
    }

    fun getAllLiveDataPSNdc() = repo.getAllLiveDataPSNdc()
    fun getAllPSNdc() = repo.getAllPSNdc()
    fun getNdcPSNdc(ndc: String) = repo.getNdcPSNdc(ndc)


    // **********************************************************************
    // Settings
    // LiveData object that can be observed in UI When inserts or deletes run
    // on Room database tables, LiveData calls the onChange callback on this
    // object
    var settings: LiveData<List<Settings>> = getLiveDataSettingsRow()

    // Settings viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertSettings(settings: Settings) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertSettings(settings)
    }
    fun deleteRowSettings(settings: Settings) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteRowSettings(settings)
    }

    fun deleteAllSettings() = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteAllSettings()
    }

    fun getLiveDataSettingsRow() = repo.getLiveDataSettingsRow()
    fun getSettingsRow() = repo.getSettingsRow()

 }