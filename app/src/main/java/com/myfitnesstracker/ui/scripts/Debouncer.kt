package com.myfitnesstracker.ui.scripts

import android.view.View

/*
*   This is a debounce function which is used to limit the rate at which a function can fire
*/
internal object Debouncer {
    @Volatile private var enabled: Boolean = true
    private val enableAgain = Runnable { enabled = true }

    fun canPerformTask(view: View): Boolean {
        if (enabled) {
            enabled = false
            view.post(enableAgain)
            return true
        }
        return false
    }
}

internal fun <T : View> T.onClickDebounced(click: (view: T) -> Unit) {
    setOnClickListener {
        if (Debouncer.canPerformTask(it)) {
            @Suppress("UNCHECKED_CAST")
            click(it as T)
        }
    }
}