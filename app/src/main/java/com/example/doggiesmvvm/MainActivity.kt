package com.example.doggiesmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLog()
    }

    private fun initLog() {
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

inline fun FragmentManager.doTransaction(func: FragmentTransaction.() ->
FragmentTransaction) {
    beginTransaction().func().addToBackStack("detail").commit()
}