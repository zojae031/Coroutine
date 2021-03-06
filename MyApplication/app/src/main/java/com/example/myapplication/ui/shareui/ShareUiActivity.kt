package com.example.myapplication.ui.shareui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityShareuiBinding
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.util.toast

class ShareUiActivity : BaseActivity<ActivityShareuiBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_shareui

    companion object {
        fun newIntent(context: Context) = Intent(context, ShareUiActivity::class.java)
    }

    private val canOverlay = registerForActivityResult(OverlayActivityResultContract()) {
        if (it) {
            runService()
        } else {
            toast(this, "거절띠")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnStartMediaProjectionService.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//minSdk 버전 높아서 없어도 될듯
                if (Settings.canDrawOverlays(this)) {
                    runService()
                } else {
                    canOverlay.launch("package:$packageName")
                }
            }
        }
    }

    private fun runService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(MediaProjectionService.firstStart(this))
        } else {
            startService(MediaProjectionService.firstStart(this))
        }
    }
}

class OverlayActivityResultContract : ActivityResultContract<String, Boolean>() {

    override fun createIntent(context: Context, input: String?): Intent =
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(input))

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean =
        resultCode == Activity.RESULT_OK
}