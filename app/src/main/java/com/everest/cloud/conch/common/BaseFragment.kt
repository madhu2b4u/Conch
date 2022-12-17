package com.everest.cloud.conch.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.everest.cloud.conch.common.snackbar.SnackbarAlertType

abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int
    val sharedPrefsHelpers = SpUtil.instance
    private val networkListener: NetworkListener by lazy { NetworkListener() }
    var isNetworkAvailable = false

    var miniRedAlert = SnackbarAlertType.MINOR_ALERT
    var miniGreenAlert = SnackbarAlertType.MINOR_POSITIVE_INFORMATION
    var majorRedAlert = SnackbarAlertType.MAJOR_ALERT


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)


    override fun onStart() {
        super.onStart()
        networkListener.checkNetworkAvailability(requireContext()).asLiveData()
            .observe(viewLifecycleOwner) {
                isNetworkAvailable = it
            }
    }
}