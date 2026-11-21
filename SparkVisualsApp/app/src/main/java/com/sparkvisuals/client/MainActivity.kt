package com.sparkvisuals.client

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : Activity() {
    private val bg = Color.rgb(10, 10, 12)
    private val panel = Color.rgb(25, 25, 29)
    private val red = Color.rgb(218, 35, 52)
    private val fg = Color.rgb(245, 245, 247)
    private val dim = Color.rgb(155, 155, 163)
    private val prefs by lazy { getSharedPreferences("sparkvisuals", MODE_PRIVATE) }
    private lateinit var content: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun base(title: String, subtitle: String): LinearLayout {
        val outer = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(bg) }
        val header = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(20), dp(24), dp(20), dp(16)) }
        header.addView(label("SPARKVISUALS", 23, fg, true))
        header.addView(label(title.uppercase(), 13, red, true).apply { setPadding(0, dp(6), 0, 0) })
        header.addView(label(subtitle, 13, dim, false).apply { setPadding(0, dp(5), 0, 0) })
        outer.addView(header)
        content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(20), 0, dp(20), dp(22)) }
        val scroll = ScrollView(this).apply { addView(content) }
        outer.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        return outer
    }

    private fun showHome() {
        val root = base("Home", "Standalone companion • external overlay only")
        content.addView(panel("GAME", "Minecraft Bedrock", "Launches the official installed game. No in-game client integration."))
        val play = button("PLAY MINECRAFT", red, fg)
        content.addView(play, params(dp(60)))
        play.setOnClickListener { launchGame() }
        val hudStart = button("START EXTERNAL HUD", Color.rgb(45, 35, 38), fg)
        content.addView(hudStart, params(dp(56)))
        hudStart.setOnClickListener { startHudOverlay() }
        content.addView(section("CLIENT"))
        content.addView(navCard("Visual modules", "HUD preferences and visual settings") { showModules() })
        content.addView(navCard("Markers", "Saved waypoints and manually entered position") { showMarkers() })
        content.addView(navCard("Settings", "Client theme and local preferences") { showSettings() })
        content.addView(section("STATUS"))
        content.addView(panel("HUD STATUS", "External overlay preview", "Displays coordinates you enter manually and distance to your nearest enabled waypoint. It does not read Minecraft."))
        content.addView(label("SPARKVISUALS • PROTOTYPE", 11, dim, false).apply { gravity = Gravity.CENTER; setPadding(0, dp(22), 0, 0) })
        setContentView(root)
    }

    private fun showModules() {
        val root = base("Visuals", "Local preferences — not yet rendered in-game")
        content.addView(navCard("← Back", "Return to dashboard") { showHome() })
        content.addView(section("HUD ELEMENT PREFERENCES"))
        listOf(
            "FPS counter" to true, "CPS counter" to false, "Coordinates" to true,
            "Potion effects" to true, "Armor display" to true, "Cooldown display" to false,
            "Custom crosshair" to false, "Zoom preference" to false, "HUD layout editor" to false
        ).forEach { (name, default) ->
            content.addView(toggle(name, default, "pref_module_"+key(name)))
        }
        content.addView(section("DISCLAIMER"))
        content.addView(label("These are saved configuration flags. They currently do not change Minecraft's screen.", 13, dim, false))
        setContentView(root)
    }

    private fun showSettings() {
        val root = base("Settings", "Saved locally on this device")
        content.addView(navCard("← Back", "Return to dashboard") { showHome() })
        content.addView(section("PREFERENCES"))
        content.addView(toggle("Crimson theme", true, "pref_theme"))
        content.addView(toggle("Show integration status", true, "pref_status"))
        content.addView(section("APP INFO"))
        content.addView(panel("VERSION", "0.1.0 prototype", "Standalone Android app • Kotlin"))
        content.addView(panel("GAME", "Minecraft Bedrock", "Launches the official installed game by package name"))
        setContentView(root)
    }

    private fun showMarkers() {
        val root = base("Markers", "Saved waypoints • manual coordinates • external HUD")
        content.addView(navCard("← Back", "Return to dashboard") { showHome() })

        content.addView(section("ADD WAYPOINT"))
        val name = EditText(this).apply { hint = "Marker name (e.g. Base)"; setTextColor(fg); setHintTextColor(dim); setSingleLine(); setBackgroundColor(panel) }
        val x = coordinateInput("X")
        val y = coordinateInput("Y")
        val z = coordinateInput("Z")
        content.addView(name, params(dp(52)))
        content.addView(x, params(dp(50)))
        content.addView(y, params(dp(50)))
        content.addView(z, params(dp(50)))
        val save = button("SAVE MARKER", red, fg)
        content.addView(save, params(dp(54)))
        save.setOnClickListener {
            val n = name.text.toString().trim()
            val xv = x.text.toString().toDoubleOrNull()
            val yv = y.text.toString().toDoubleOrNull()
            val zv = z.text.toString().toDoubleOrNull()
            if (n.isBlank() || xv == null || yv == null || zv == null) {
                Toast.makeText(this, "Enter a name and numeric X/Y/Z coordinates.", Toast.LENGTH_SHORT).show()
            } else {
                val arr = loadMarkers()
                arr.put(JSONObject().put("name", n).put("x", xv).put("y", yv).put("z", zv).put("color", "Crimson").put("enabled", true))
                prefs.edit().putString("markers_json", arr.toString()).apply()
                showMarkers()
            }
        }

        content.addView(section("MANUAL CURRENT POSITION"))
        val currentX = coordinateInput("Current X").apply { setText(prefs.getString("current_x", "")) }
        val currentY = coordinateInput("Current Y").apply { setText(prefs.getString("current_y", "")) }
        val currentZ = coordinateInput("Current Z").apply { setText(prefs.getString("current_z", "")) }
        content.addView(currentX, params(dp(50)))
        content.addView(currentY, params(dp(50)))
        content.addView(currentZ, params(dp(50)))
        val savePosition = button("SAVE CURRENT POSITION", red, fg)
        content.addView(savePosition, params(dp(50)))
        savePosition.setOnClickListener {
            val cx = currentX.text.toString().toDoubleOrNull()
            val cy = currentY.text.toString().toDoubleOrNull()
            val cz = currentZ.text.toString().toDoubleOrNull()
            if (cx == null || cy == null || cz == null) {
                Toast.makeText(this, "Enter valid current X/Y/Z.", Toast.LENGTH_SHORT).show()
            } else {
                prefs.edit().putString("current_x", cx.toString()).putString("current_y", cy.toString())
                    .putString("current_z", cz.toString()).apply()
                showMarkers()
            }
        }

        content.addView(section("SAVED WAYPOINTS"))
        val markers = loadMarkers()
        if (markers.length() == 0) content.addView(panel("EMPTY", "No markers saved", "Add a waypoint above."))
        for (i in 0 until markers.length()) {
            val item = markers.optJSONObject(i) ?: continue
            val cx = prefs.getString("current_x", "")?.toDoubleOrNull()
            val cz = prefs.getString("current_z", "")?.toDoubleOrNull()
            val distText = if (cx != null && cz != null) {
                val dx = item.optDouble("x") - cx
                val dz = item.optDouble("z") - cz
                "Horizontal X/Z distance: %.1f blocks".format(kotlin.math.sqrt(dx * dx + dz * dz))
            } else "Distance: save your current X/Z above"
            val state = if (item.optBoolean("enabled", true)) "Enabled in companion list" else "Disabled in companion list"
            val colorName = item.optString("color", "Crimson")
            val entry = panel("WAYPOINT • $colorName", item.optString("name"), "X ${item.optDouble("x")}   Y ${item.optDouble("y")}   Z ${item.optDouble("z")}\n$state\n$distText")
            content.addView(entry, params(dp(86)))
            val edit = button("EDIT WAYPOINT", Color.rgb(45, 35, 38), fg)
            content.addView(edit, params(dp(42)))
            edit.setOnClickListener { editMarkerDialog(item, i) }
            val remove = button("DELETE", Color.rgb(70, 25, 30), fg)
            content.addView(remove, params(dp(42)))
            remove.setOnClickListener {
                val newArr = JSONArray()
                for (j in 0 until markers.length()) if (j != i) newArr.put(markers.get(j))
                prefs.edit().putString("markers_json", newArr.toString()).apply()
                showMarkers()
            }
        }
        content.addView(label("Coordinates are manual, not read from Minecraft. Distances use horizontal X/Z only. The floating HUD is external to the game.", 12, dim, false).apply { setPadding(0, dp(16), 0, 0) })
        setContentView(root)
    }

    private fun editMarkerDialog(marker: JSONObject, index: Int) {
        val form = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(20), dp(8), dp(20), dp(8)) }
        val nameField = EditText(this).apply { setText(marker.optString("name")); hint = "Name" }
        val xField = coordinateInput("X").apply { setText(marker.optDouble("x").toString()) }
        val yField = coordinateInput("Y").apply { setText(marker.optDouble("y").toString()) }
        val zField = coordinateInput("Z").apply { setText(marker.optDouble("z").toString()) }
        val enabledBox = CheckBox(this).apply {
            text = "Enabled in companion marker list"
            setTextColor(fg)
            isChecked = marker.optBoolean("enabled", true)
        }
        val colors = arrayOf("Crimson", "Gold", "Green", "Blue", "Purple", "White")
        var selectedColor = marker.optString("color", "Crimson")
        val colorGroup = RadioGroup(this).apply { orientation = RadioGroup.VERTICAL }
        colors.forEach { color ->
            val radio = RadioButton(this).apply { text = color; setTextColor(fg); isChecked = color == selectedColor }
            radio.setOnClickListener { selectedColor = color }
            colorGroup.addView(radio)
        }
        form.addView(nameField); form.addView(xField); form.addView(yField); form.addView(zField)
        form.addView(enabledBox); form.addView(label("Marker color", 13, red, true)); form.addView(colorGroup)
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Edit waypoint")
            .setView(form)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save", null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val nm = nameField.text.toString().trim()
                val xv = xField.text.toString().toDoubleOrNull()
                val yv = yField.text.toString().toDoubleOrNull()
                val zv = zField.text.toString().toDoubleOrNull()
                if (nm.isBlank() || xv == null || yv == null || zv == null) {
                    Toast.makeText(this, "Enter a name and valid X/Y/Z.", Toast.LENGTH_SHORT).show()
                } else {
                    val saved = loadMarkers()
                    saved.put(index, JSONObject().put("name", nm).put("x", xv).put("y", yv).put("z", zv)
                        .put("color", selectedColor).put("enabled", enabledBox.isChecked))
                    prefs.edit().putString("markers_json", saved.toString()).apply()
                    dialog.dismiss()
                    showMarkers()
                }
            }
        }
        dialog.show()
    }

    private fun loadMarkers(): JSONArray = try { JSONArray(prefs.getString("markers_json", "[]")) } catch (_: Exception) { JSONArray() }
    private fun coordinateInput(axis: String) = EditText(this).apply { hint = "$axis coordinate"; inputType = 12290; setTextColor(fg); setHintTextColor(dim); setSingleLine(); setBackgroundColor(panel) }

    private fun toggle(title: String, default: Boolean, key: String): View {
        val enabled = prefs.getBoolean(key, default)
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(dp(13), dp(13), dp(13), dp(13)); setBackgroundColor(panel) }
        row.addView(label(title, 14, fg, false), LinearLayout.LayoutParams(0, -2, 1f))
        val sw = Switch(this).apply { isChecked = enabled; buttonTintList = null }
        row.addView(sw)
        sw.setOnCheckedChangeListener { _, value -> prefs.edit().putBoolean(key, value).apply() }
        return row
    }

    private fun navCard(title: String, subtitle: String, action: () -> Unit): View {
        val v = panel("OPEN", title, subtitle)
        v.setOnClickListener { action() }
        return v
    }
    private fun panel(tag: String, title: String, subtitle: String): View {
        val box = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(15), dp(13), dp(15), dp(13)); setBackgroundColor(panel) }
        box.addView(label(tag, 10, red, true))
        box.addView(label(title, 18, fg, true).apply { setPadding(0, dp(5), 0, dp(4)) })
        box.addView(label(subtitle, 12, dim, false))
        return box
    }
    private fun section(s: String) = label(s, 12, red, true).apply { setPadding(0, dp(21), 0, dp(11)) }
    private fun button(s: String, color: Int, textColor: Int) = TextView(this).apply { text=s; textSize=14f; setTextColor(textColor); typeface=Typeface.DEFAULT_BOLD; gravity=Gravity.CENTER; setBackgroundColor(color) }
    private fun label(s: String, size: Int, color: Int, bold: Boolean) = TextView(this).apply { text=s; textSize=size.toFloat(); setTextColor(color); if (bold) typeface=Typeface.DEFAULT_BOLD }
    private fun params(h: Int) = LinearLayout.LayoutParams(-1, h).apply { topMargin=dp(9) }
    private fun dp(v: Int) = (v*resources.displayMetrics.density).toInt()
    private fun key(s: String) = s.lowercase().replace(" ", "_")

    private fun startHudOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            val permissionIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(permissionIntent)
            Toast.makeText(this, "Allow display over other apps, then return and press Start HUD.", Toast.LENGTH_LONG).show()
            return
        }
        try {
            startService(Intent(this, HudOverlayService::class.java))
            Toast.makeText(this, "SparkVisuals HUD started. Launch Minecraft and drag the panel.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "HUD start failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun launchGame() {
        val intent = packageManager.getLaunchIntentForPackage("com.mojang.minecraftpe")
        if (intent == null) Toast.makeText(this, "Minecraft Bedrock is not installed.", Toast.LENGTH_LONG).show()
        else try { startActivity(intent) } catch (e: Exception) { Toast.makeText(this, "Launch error: ${e.message}", Toast.LENGTH_LONG).show() }
    }
}
