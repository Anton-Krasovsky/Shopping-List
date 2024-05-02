package by.tigertosh.shoppinglist.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class MyTouchListener : OnTouchListener {
    var xDelta = 0.0f
    var yDelta = 0.0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(position: View, event: MotionEvent?): Boolean {

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                xDelta = position.x - event.rawX
                yDelta = position.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                position.x = xDelta + event.rawX
                position.y = yDelta + event.rawY
            }
        }
        return true
    }
}