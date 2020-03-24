package com.sakshi.coolshopapplication.arch

import androidx.fragment.app.Fragment
import com.sakshi.coolshopapplication.CoolShop

open class BaseFragment : Fragment() {
  private var compRoot: CompRootUi? = null

  protected fun compRoot(): CompRootUi? {
    if (compRoot == null) {
      compRoot = CompRootUi(
          (this.activity!!.application as CoolShop).getCompRoot(),
          this.activity!!
      )
    }
    return compRoot
  }
}