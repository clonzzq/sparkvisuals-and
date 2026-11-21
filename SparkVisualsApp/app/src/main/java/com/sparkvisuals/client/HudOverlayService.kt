package com.sparkvisuals.client

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray
import java.util.Locale
import kotlin.math.sqrt

class HudOverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private var hudView: LinearLayout? = null
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var info: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val refreshTask = object : Runnable {
        override fun run() {
            updateHudText()
            handler.postDelayed(this, 2000L)
        }
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        showHud()
        updateHudText()
        handler.postDelayed(refreshTask, 2000L)
    }

    private fun showHud() {
        val density = resources.displayMetrics.density
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding((12*density).toInt(), (9*density).toInt(), (12*density).toInt(), (9*density).toInt())
            setBackgroundColor(Color.argb(225, 12, 12, 15))
        }
        val title = TextView(this).apply {
            text = "SPARKVISUALS • EXTERNAL HUD"
            textSize = 12f
            setTextColor(Color.rgb(235, 45, 62))
            typeface = Typeface.DEFAULT_BOLD
        }
        info = TextView(this).apply {
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

    private fun updateHudText() {
        val prefs = getSharedPreferences("sparkvisuals", MODE_PRIVATE)
        val cx = prefs.getString("current_x", "")?.toDoubleOrNull()
        val cy = prefs.getString("current_y", "")?.toDoubleOrNull()
        val cz = prefs.getString("current_z", "")?.toDoubleOrNull()
        val coords = if (cx != null && cy != null && cz != null) {
            "Manual XYZ: %.1f  %.1f  %.1f".format(Locale.US, cx, cy, cz)
        } else {
            "Coords: enter X/Y/Z in SparkVisuals → Markers"
        }
        val nearest = if (cx != null && cz != null) {
            try {
                val markers = JSONArray(prefs.getString("markers_json", "[]"))
                var nearestName: String? = null
                var nearestDistance = Double.POSITIVE_INFINITY
                for (i in 0 until markers.length()) {
                    val marker = markers.optJSONObject(i) ?: continue
                    if (!marker.optBoolean("enabled", true)) continue
                    val mx = marker.optDouble("x", Double.NaN)
                    val mz = marker.optDouble("z", Double.NaN)
                    if (!mx.isFinite() || !mz.isFinite()) continue
                    val distance = sqrt((mx - cx) * (mx - cx) + (mz - cz) * (mz - cz))
                    if (distance < nearestDistance) {
                        nearestDistance = distance
                        nearestName = marker.optString("name", "Waypoint")
                    }
                }
                if (nearestName != null) {
                    "Nearest: $nearestName • %.1f blocks (X/Z)".format(Locale.US, nearestDistance)
                } else "Nearest: no enabled waypoints"
            } catch (_: Exception) {
                "Nearest: marker data unavailable"
            }
        } else "Nearest waypoint: save current X/Z first"
        info.text = "$coords\n$nearest\nManual data • external to Minecraft"
    }

    override fun onDestroy() {
        handler.removeCallbacks(refreshTask)
        hudView?.let { try { windowManager.removeView(it) } catch (_: Exception) {} }
        hudView = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
