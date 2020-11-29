package com.siclo.app.core.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.siclo.app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}