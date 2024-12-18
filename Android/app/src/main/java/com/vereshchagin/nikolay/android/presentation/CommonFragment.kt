package com.vereshchagin.nikolay.android.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vereshchagin.nikolay.android.MainApplication
import com.vereshchagin.nikolay.android.Screens
import com.vereshchagin.nikolay.android.base.BaseFragment
import com.vereshchagin.nikolay.android.databinding.FragmentCommonBinding
import com.vereshchagin.nikolay.android.navigation.Router
import com.vereshchagin.nikolay.android.presentation.worker.DeviceChargingWorker
import javax.inject.Inject

class CommonFragment : BaseFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {

    @Inject
    lateinit var router: Router

    private val fragmentIndex by lazy { requireArguments().getInt(FRAGMENT_INDEX) }

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onNotificationPermissionResult
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MainApplication.Instance.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CommonFragment", "onCreate: $fragmentIndex")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Fragment #$fragmentIndex"

        binding.navBack.setOnClickListener { onNavigateBackClicked() }
        binding.navNext.setOnClickListener { onNavigateNextClicked() }
        binding.startWorker.setOnClickListener { onStartWorkerClicked() }
    }

    private fun onNavigateNextClicked() {
        val newIndex = (fragmentIndex + 1) % Screens.allScreens.size

        router.navigate(Screens.allScreens[newIndex])
    }

    private fun onNavigateBackClicked() {
        val newIndex = if (fragmentIndex - 1 < 0) {
            Screens.allScreens.lastIndex
        } else {
            fragmentIndex - 1
        }

        router.navigate(Screens.allScreens[newIndex])
    }

    private fun onStartWorkerClicked() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            scheduleDeviceChargingWorker()
            return
        }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                scheduleDeviceChargingWorker()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Permission not granted")
                    .setMessage("Permission to send notifications is required")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .create()
            }
            else -> {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Один раз запланировать WorkManager,
     * который будет присылать уведомление о том, что устройство находится на зарядке.
     */
    private fun scheduleDeviceChargingWorker() {
        val request = OneTimeWorkRequestBuilder<DeviceChargingWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()
            )
            .build()

        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueueUniqueWork(DeviceChargingWorker.TAG, ExistingWorkPolicy.KEEP, request)
    }

    private fun onNotificationPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            scheduleDeviceChargingWorker()
            return
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Permission denied")
            .setMessage("Permission to send notifications is required")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
    }

    companion object {

        private const val FRAGMENT_INDEX = "fragment_index"

        fun newInstance(index: Int): CommonFragment {
            return CommonFragment().apply {
                arguments = bundleOf(FRAGMENT_INDEX to index)
            }
        }
    }
}