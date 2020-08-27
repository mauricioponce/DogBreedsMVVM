package com.example.doggiesmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.doggiesmvvm.list.DoggiesFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(R.id.mainFrameLayout, DoggiesFragment.newInstance(1))

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
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
    supportFragmentManager.doTransaction { add(frameId, fragment) }
}


fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.doTransaction{replace(frameId, fragment)}
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.doTransaction{remove(fragment)}
}