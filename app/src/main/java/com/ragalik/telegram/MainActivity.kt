package com.ragalik.telegram

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ragalik.telegram.activity.RegisterActivity
import com.ragalik.telegram.databinding.ActivityMainBinding
import com.ragalik.telegram.model.User
import com.ragalik.telegram.ui.fragment.ChatsFragment
import com.ragalik.telegram.ui.`object`.AppDrawer
import com.ragalik.telegram.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    lateinit var mAppDrawer : AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser {
            initFields()
            initFunc()
        }
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        setSupportActionBar(mToolbar)
        mAppDrawer.create()
    }
}