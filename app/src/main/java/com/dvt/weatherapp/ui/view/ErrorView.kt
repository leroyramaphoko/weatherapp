package com.dvt.weatherapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dvt.weatherapp.databinding.ViewErrorBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onRetryClickListener: (() -> Unit?)? = null

    init {
        initView()
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        val binding = ViewErrorBinding.inflate(inflater, this, true)

        binding.buttonRetry.setOnClickListener {
            onRetryClickListener?.invoke()
        }
    }

    fun onRetryClicked(onRetryClickListener: () -> Unit) {
        this.onRetryClickListener = onRetryClickListener
    }
}