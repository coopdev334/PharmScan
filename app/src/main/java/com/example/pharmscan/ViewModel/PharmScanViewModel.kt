package com.example.pharmscan.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.pharmscan.Data.ScanLiveData
import com.example.pharmscan.Data.Tables.*
import com.example.pharmscan.Repository.PharmScanRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PharmScanViewModel(
    private val repo: PharmScanRepo
): ViewModel() {

    // Circular Progress Bar control variable
    val circularPrgBarLoading = mutableStateOf(false)

    // Screen fade amount. Used to fade composable that uses modifier.alpha() to fade
    // the screen. 1.0f is no fade, 0.0f is blank screen
    val screenFadeAmount = mutableStateOf(1.0f)

    // **********************************************************************
    // HostIpAddress
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    var hostIpAddress: LiveData<List<HostIpAddress>> = getAllLiveDataHostIpAddress()

    // Settings viewModel db interface
    // These functions will be called by the composable views to get and set database information
    // Suspend function modifier is not used here but in repo and dao
    fun insertHostIpAddress(hostIpAddress: HostIpAddress) = CoroutineScope(Dispatchers.IO).launch {
        repo.insertHostIpAddress(hostIpAddress)
    }

    fun deleteRowHostIpAddress(hostIpAddress: HostIpAddress) = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteRowHostIpAddress(hostIpAddress)
    }

    fun deleteAllHostIpAddress() = CoroutineScope(Dispatchers.IO).launch {
        repo.deleteAllHostIpAddress()
    }

    fun getAllLiveDataHostIpAddress() = repo.getAllLiveDataHostIpAddress()
    fun getAllHostIpAddress() = repo.getAllHostIpAddress()


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

    fun deleteAllCollectedData() = CoroutineScope(Dispatchers.IO).launch {
            repo.deleteAllCollectedData()
    }

    fun getAllLiveDataCollectedData() = repo.getAllLiveDataCollectedData()
    fun getAllCollectedData() = repo.getAllCollectedData()
    fun getAllCollectedDataOrderByRecCnt() = repo.getAllCollectedDataOrderByRecCnt()
    fun getAllCollectedDataOrderByTag() = repo.getAllCollectedDataOrderByTag()
    fun getAllCollectedDataOrderByNdc() = repo.getAllCollectedDataOrderByNdc()
    fun getColDataLastInsertedRow() = repo.getColDataLastInsertedRow()
    fun getColDataQtyPriceByTag(tag: String) = repo.getColDataQtyPriceByTag(tag)

    fun uploadCollectedData() = CoroutineScope(Dispatchers.IO).launch {
        screenFadeAmount.value = 0.2f
        circularPrgBarLoading.value = true
        repo.uploadCollectedData()
        delay(2000)
        circularPrgBarLoading.value = false
        screenFadeAmount.value = 1.0f
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
    fun getAllPSNdcOrderByNdc() = repo.getAllPSNdcOrderByNdc()
    fun getAllPSNdcOrderByPrice() = repo.getAllPSNdcOrderByPrice()
    fun getAllPSNdcOrderByPackSz() = repo.getAllPSNdcOrderByPackSz()


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

    // **********************************************************************
    // Scan LiveData
    val scanLiveData: MutableLiveData<ScanLiveData> by lazy {
        MutableLiveData<ScanLiveData>()
    }

}