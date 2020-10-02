package com.ragalik.telegram.ui.fragment

import androidx.fragment.app.Fragment
import com.ragalik.telegram.MainActivity
import com.ragalik.telegram.util.APP_ACTIVITY


open class BaseFragment (layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}