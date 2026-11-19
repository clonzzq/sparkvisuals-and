package com.sparkvisuals.client

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.rgb(12, 12, 14)
    private val cardColor = Color.rgb(24, 24, 28)
    private val red = Color.rgb(220, 38, 55)
    private val white = Color.rgb(244, 244, 246)
    private val muted = Color.rgb(150, 150, 158)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(24))
            setBackgroundColor(bg)
        }
        root.addView(text("SPARK", 30, white, true))
        root.addView(text("VISUALS / MOBILE", 13, red, true))
        root.addView(text("Client launcher prototype", 14, muted, false).apply { setPadding(0, dp(5), 0, dp(24)) })
        root.addView(gameCard())
        val play = button("PLAY MINECRAFT", red, white)
        root.addView(play, params(dp(64)))
        play.setOnClickListener { launchMinecraft() }

        root.addView(section("CLIENT SETTINGS"))
        val prefs = getSharedPreferences("sparkvisuals", MODE_PRIVATE)
        root.addView(toggleRow("Custom crosshair preference", prefs.getBoolean("crosshair", false)) { v -> prefs.edit().putBoolean("crosshair", v).apply() })
        root.addView(toggleRow("Coordinates HUD preference", prefs.getBoolean("coordinates", true)) { v -> prefs.edit().putBoolean("coordinates", v).apply() })
        root.addView(toggleRow("Potion HUD preference", prefs.getBoolean("potions", true)) { v -> prefs.edit().putBoolean("potions", v).apply() })
        root.addView(section("ABOUT"))
        root.addView(text("This app is a launcher/settings prototype. Preferences are not connected to Minecraft yet; in-game integration is a separate development task.", 13, muted, false))
        root.addView(text("SPARKVISUALS / 0.1.0", 11, muted, false).apply { gravity = Gravity.CENTER; setPadding(0, dp(30), 0, 0) })
        setContentView(root)
    }

    private fun launchMinecraft() {
        val intent = packageManager.getLaunchIntentForPackage("com.mojang.minecraftpe")
        if (intent == null) {
            Toast.makeText(this, "Minecraft Bedrock not found. Install the official game first.", Toast.LENGTH_LONG).show()
            return
        }
        try { startActivity(intent) } catch (e: Exception) {
            Toast.makeText(this, "Launch failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun gameCard(): View {
        val box = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(16), dp(15), dp(16), dp(15)); setBackgroundColor(cardColor) }
        box.addView(text("GAME", 11, red, true))
        box.addView(text("Minecraft Bedrock", 20, white, true).apply { setPadding(0, dp(7), 0, dp(4)) })
        box.addView(text("Launch the installed official game", 13, muted, false))
        return box
    }
    private fun section(s: String) = text(s, 12, red, true).apply { setPadding(0, dp(25), 0, dp(12)) }
    private fun button(label: String, color: Int, fg: Int) = TextView(this).apply { text = label; textSize = 15f; setTextColor(fg); typeface = Typeface.DEFAULT_BOLD; gravity = Gravity.CENTER; setBackgroundColor(color) }
    private fun toggleRow(label: String, initial: Boolean, onChange: (Boolean) -> Unit): View {
        var enabled = initial
        return TextView(this).apply {
            text = "$label    ${if (enabled) "ON" else "OFF"}"
            textSize = 14f; setTextColor(white); setPadding(dp(14), dp(17), dp(14), dp(17)); setBackgroundColor(cardColor)
            setOnClickListener { enabled = !enabled; text = "$label    ${if (enabled) "ON" else "OFF"}"; onChange(enabled) }
        }
    }
    private fun text(s: String, size: Int, color: Int, bold: Boolean) = TextView(this).apply { text = s; textSize = size.toFloat(); setTextColor(color); if (bold) typeface = Typeface.DEFAULT_BOLD }
    private fun params(h: Int) = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h).apply { topMargin = dp(14) }
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
}
