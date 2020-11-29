package com.siclo.app.ui.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.siclo.app.R
import com.siclo.app.common.constants.SicloResource
import com.siclo.app.common.extensions.hideLoader
import com.siclo.app.common.extensions.showDialog
import com.siclo.app.common.extensions.showLoader
import kotlinx.android.synthetic.main.dashboard_video_fragment.*
import kotlinx.android.synthetic.main.view_toolbar.*

class DashboardVideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dashboard_video_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setBackgroundColor(resources.getColor(R.color.white_smoke, null))
        tvDescription.setText(R.string.dashboard_video_title)
        btnBackAction.setOnClickListener { findNavController().popBackStack() }
        setupVideo()
    }

    @Suppress("DEPRECATION")
    private fun setupVideo() {
        try {
            showLoader()

            val mediaController = MediaController(requireActivity())
            mediaController.setAnchorView(videoView)
            videoView.run {

                setMediaController(mediaController)
                setVideoPath(SicloResource.LINK_VIDEO)
                requestFocus()
                start()

                try {
                    setOnPreparedListener { hideLoader() }
                } catch (e: Exception) {
                    showDialog(getString(R.string.dashboard_video_play_error)) {
                        findNavController().popBackStack()
                    }
                }
            }

        } catch (e: Exception) {
            showDialog(getString(R.string.dashboard_video_loading_error)) {
                findNavController().popBackStack()
            }
        }
    }
}