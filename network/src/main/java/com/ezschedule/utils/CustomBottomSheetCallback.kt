package com.ezschedule.utils

import android.view.View
import android.view.animation.DecelerateInterpolator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

class CustomBottomSheetCallback : BottomSheetCallback() {
    override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheet.animate().apply {
                interpolator = DecelerateInterpolator()
                translationY(0f)
                duration = 500
                start()
            }
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
}