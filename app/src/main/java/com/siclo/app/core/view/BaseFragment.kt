package com.siclo.app.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.siclo.app.BR
import com.siclo.app.common.extensions.hideLoader
import com.siclo.app.common.extensions.showLoader
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.viewmodel.BaseViewModel
import com.siclo.app.core.viewmodel.event.BaseViewEvent

abstract class BaseFragment<DATA_BINDING : ViewDataBinding> : Fragment() {

    private lateinit var viewDataBinding: DATA_BINDING
    protected var isRestoredFromBackStack = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getInternalViewModel().viewEvent.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { event -> handleEvent(event) }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRestoredFromBackStack = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewDataBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, getInternalViewModel())
            executePendingBindings()
        }

        return viewDataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRestoredFromBackStack = true
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getInternalViewModel(): BaseViewModel

    protected open fun handleEvent(event: BaseViewEvent) {
        when (event) {
            is BaseViewEvent.OnRequestEvent -> {
                if (event.resource.status == Resource.LOADING) showLoader()
                else hideLoader()
            }
        }
    }
}