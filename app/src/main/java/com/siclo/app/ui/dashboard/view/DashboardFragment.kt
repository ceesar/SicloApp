package com.siclo.app.ui.dashboard.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.siclo.app.R
import com.siclo.app.common.extensions.hideLoader
import com.siclo.app.common.extensions.showDialog
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.data.api.models.response.Class
import com.siclo.app.core.view.BaseFragment
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import com.siclo.app.databinding.DashboardFragmentBinding
import com.siclo.app.ui.dashboard.adapter.ClassesAdapter
import com.siclo.app.ui.dashboard.model.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dialog_class.view.*
import kotlinx.android.synthetic.main.dialog_class.view.tvDuration
import kotlinx.android.synthetic.main.dialog_class.view.tvIntensity
import kotlinx.android.synthetic.main.item_class.view.*
import kotlinx.android.synthetic.main.view_toolbar.*

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardFragmentBinding>() {

    private val viewModel: DashboardViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    override fun getLayoutRes() = R.layout.dashboard_fragment

    override fun getInternalViewModel() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.retrieveClasses()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBackAction.visibility = View.INVISIBLE
        tvDescription.setText(R.string.dashboard_title)

        rvClasses.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvClasses.adapter = ClassesAdapter(requireContext()) { showClassDialog(it) }

        viewModel.classes.observe(viewLifecycleOwner, {
            hideLoader()
            (rvClasses.adapter as ClassesAdapter).setDataValues(it)
        })
    }

    @Suppress("UNCHECKED_CAST")
    override fun handleEvent(event: BaseViewEvent) {
        super.handleEvent(event)
        when (event) {
            is BaseViewEvent.OnRequestEvent -> {
                handleLoginRequest(event.resource as Resource<List<Any>>)
            }
        }
    }

    private fun handleLoginRequest(event: Resource<List<Any>>) {
        if (event.status == Resource.ERROR) {
            showDialog("Error result: ${event.message}", cancelable = false) {
                viewModel.retrieveClasses()
            }
        }
    }

    private fun showClassDialog(clazz: Class) {
        val view = View.inflate(requireContext(), R.layout.dialog_class, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .setNegativeButton(R.string.dashboard_close) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.dashboard_join_to_class) { dialog, _ ->
                dialog.cancel()
                findNavController().navigate(R.id.action_dashboardFragment_to_videoFragment)
            }
            .create()

        view.run {
            with(clazz) {
                tvTitle.text = specialText
                tvType.text = type
                tvDuration.text = getString(R.string.dashboard_duration, duration)
                tvIntensity.text = getString(R.string.dashboard_intensity, intensity)
            }
        }

        alertDialog.show()
    }
}