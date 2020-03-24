package com.sakshi.coolshopapplication.arch

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import com.sakshi.coolshopapplication.CoolShop

@SuppressLint("Registered")
open class BaseActivity : FragmentActivity() {

  private var compRoot: CompRootUi? = null

  protected fun compRoot(): CompRootUi? {
    if (compRoot == null) {
      compRoot = CompRootUi(
          (application as CoolShop).getCompRoot(),
          this
      )
    }
    return compRoot
  }
}