package com.aakarshrestha.sampleappswipetorefresh.network

import android.os.CountDownTimer

fun fakeNetworkCall(
    onNetworkCallDone: (Boolean) -> Unit
) {
    object: CountDownTimer(3000, 1000) {
        override fun onTick(p0: Long) {

        }

        override fun onFinish() {
            onNetworkCallDone.invoke(false)
        }
    }.start()
}