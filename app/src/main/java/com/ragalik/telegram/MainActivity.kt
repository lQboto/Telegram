package com.ragalik.telegram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ragalik.telegram.activities.RegisterActivity
import com.ragalik.telegram.databinding.ActivityMainBinding
import com.ragalik.telegram.ui.fragments.ChatsFragment
import com.ragalik.telegram.ui.`object`.AppDrawer
import com.ragalik.telegram.util.AUTH
import com.ragalik.telegram.util.initFirebase
import com.ragalik.telegram.util.replaceActivity
import com.ragalik.telegram.util.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    lateinit var mAppDrawer : AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }

    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
    }
}