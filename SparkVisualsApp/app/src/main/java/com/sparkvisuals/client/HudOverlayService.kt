package com.sparkvisuals.client

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

class HudOverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private var hudView: LinearLayout? = null
    private lateinit var layoutParams: WindowManager.LayoutParams

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        showHud()
    }

    private fun showHud() {
        val density = resources.displayMetrics.density
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding((12*density).toInt(), (9*density).toInt(), (12*density).toInt(), (9*density).toInt())
            setBackgroundColor(Color.argb(225, 12, 12, 15))
        }
        val title = TextView(this).apply {
            text = "SPARKVISUALS  •  HUD"
            textSize = 12f
            setTextColor(Color.rgb(235, 45, 62))
            typeface = Typeface.DEFAULT_BOLD
        }
        val info = TextView(this).apply {
            text = "FPS  --   |   XYZ  --\nHUD preview • game data not connected"
            textSize = 11f
            setTextColor(Color.WHITE)
            setPadding(0, (5*density).toInt(), 0, 0)
        }
        val close = TextView(this).apply {
            text = "×  CLOSE HUD"
            textSize = 10f
            setTextColor(Color.rgb(255, 100, 110))
            setPadding(0, (6*density).toInt(), 0, 0)
            setOnClickListener { stopSelf() }
        }
        root.addView(title)
        root.addView(info)
        root.addView(close)
        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = (16*density).toInt()
            y = (110*density).toInt()
        }
        title.setOnTouchListener(object : android.view.View.OnTouchListener {
            private var startX = 0
            private var startY = 0
            private var touchX = 0f
            private var touchY = 0f
            override fun onTouch(v: android.view.View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = layoutParams.x
                        startY = layoutParams.y
                        touchX = event.rawX
                        touchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        layoutParams.x = startX + (event.rawX - touchX).toInt()
                        layoutParams.y = startY + (event.rawY - touchY).toInt()
                        windowManager.updateViewLayout(root, layoutParams)
                        return true
                    }
                }
                return false
            }
        })
        hudView = root
        windowManager.addView(root, layoutParams)
    }

    override fun onDestroy() {
        hudView?.let { try { windowManager.removeView(it) } catch (_: Exception) {} }
        hudView = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
