package com.siclo.app.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siclo.app.R
import com.siclo.app.core.data.api.models.response.Class
import kotlinx.android.synthetic.main.item_class.view.*
import kotlinx.android.synthetic.main.item_header_class.view.*

class ClassesAdapter(
    private val context: Context,
    private val clickAction: (clazz: Class) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var dataList = listOf<Any>()

    companion object {
        private const val HEADER_TYPE = 0
        private const val TRANSACTION_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            HEADER_TYPE -> {
                val view = inflater.inflate(R.layout.item_header_class, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_class, parent, false)
                ClassViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = dataList[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(element as String)
            is ClassViewHolder -> holder.bind(element as Class)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is String -> HEADER_TYPE
            else -> TRANSACTION_TYPE
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setDataValues(data: List<Any>) {
        if (dataList != data) {
            dataList = data
            notifyDataSetChanged()
        }
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
        override fun bind(item: String) {
            itemView.tvTitle.text = item
        }

    }

    inner class ClassViewHolder(itemView: View) : BaseViewHolder<Class>(itemView) {
        override fun bind(item: Class) {
            itemView.run {
                with(item) {
                    tvInstructorName.text = instructor.name
                    tvDescription.text = specialText
                    tvHour.text = hour
                    tvDuration.text = context.getString(R.string.dashboard_duration, duration)
                    tvIntensity.text = context.getString(R.string.dashboard_intensity, intensity)
                }
            }
            itemView.setOnClickListener { clickAction.invoke(item) }
        }
    }

}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}
