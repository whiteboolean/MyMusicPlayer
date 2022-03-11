package com.open.mymusicplayer.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.open.architecture.com.open.architecture.utils.Utils

class App  : Application(),ViewModelStoreOwner{

    protected var mAppViewModelStore:ViewModelStore ? = null
    protected var mFactory:ViewModelProvider.Factory?= null


    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }



    fun getAppViewModelProvider(activity: Activity) : ViewModelProvider{
        return ViewModelProvider(
            activity.applicationContext as App,
            (activity.applicationContext as App).getAppFactory(activity)!!
        )
    }


    //ApplicationViewModelFactory 工程是为了创建ViewModel，给上面的getAppViewModelProvider
    private fun getAppFactory(activity:Activity) : ViewModelProvider.Factory?{
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory
    }



    // 监测下 Activity是否为null
    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    // 监测下 Activity是否为null
    private fun checkActivity(fragment: Fragment): Activity? {
        return fragment.activity
            ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }







}