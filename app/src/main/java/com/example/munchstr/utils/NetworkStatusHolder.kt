package com.example.munchstr.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NetworkStatusHolder {
    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _networkStatus.asStateFlow()

    fun updateNetworkStatus(status: ConnectivityObserver.Status) {
        _networkStatus.value = status
    }
}
