package com.sparkvisuals.client

import android.app.Activity
import android.os.Bundle
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
        val root = base("Home", "Android client companion / build 0.1.0")
        content.addView(panel("GAME", "Minecraft Bedrock", "Installed game • launch shortcut"))
        val play = button("PLAY MINECRAFT", red, fg)
        content.addView(play, params(dp(60)))
        play.setOnClickListener { launchGame() }
        content.addView(section("CLIENT"))
        content.addView(navCard("Visual modules", "HUD preferences and visual settings") { showModules() })
        content.addView(navCard("Markers", "Personal waypoints: names and coordinates") { showMarkers() })
        content.addView(navCard("Settings", "Client theme and local preferences") { showSettings() })
        content.addView(section("STATUS"))
        content.addView(panel("INTEGRATION", "Not connected to Minecraft", "The controls below save in the APK only. No in-game rendering is wired yet."))
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
        val root = base("Markers", "Personal waypoints only — no automatic detection")
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
                arr.put(JSONObject().put("name", n).put("x", xv).put("y", yv).put("z", zv))
                prefs.edit().putString("markers_json", arr.toString()).apply()
                showMarkers()
            }
        }
        content.addView(section("DISTANCE CALCULATOR"))
        val currentX = coordinateInput("Your current X").apply { setText(prefs.getString("current_x", "")) }
        val currentZ = coordinateInput("Your current Z").apply { setText(prefs.getString("current_z", "")) }
        content.addView(currentX, params(dp(50)))
        content.addView(currentZ, params(dp(50)))
        val calc = button("UPDATE DISTANCES", red, fg)
        content.addView(calc, params(dp(50)))
        calc.setOnClickListener {
            val cx = currentX.text.toString().toDoubleOrNull()
            val cz = currentZ.text.toString().toDoubleOrNull()
            if (cx == null || cz == null) Toast.makeText(this, "Enter valid X/Z.", Toast.LENGTH_SHORT).show()
            else { prefs.edit().putString("current_x", cx.toString()).putString("current_z", cz.toString()).apply(); showMarkers() }
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
                "Distance: %.1f blocks".format(kotlin.math.sqrt(dx * dx + dz * dz))
            } else "Distance: set your current X/Z above"
            val entry = panel("WAYPOINT", item.optString("name"), "X ${item.optDouble("x")}   Y ${item.optDouble("y")}   Z ${item.optDouble("z")}\n$distText")
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
        content.addView(label("Waypoint data is stored locally. Distance calculation and in-game display are not implemented.", 12, dim, false).apply { setPadding(0, dp(16), 0, 0) })
        setContentView(root)
    }

    private fun editMarkerDialog(marker: JSONObject, index: Int) {
        val form = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(20), dp(8), dp(20), dp(8)) }
        val nameField = EditText(this).apply { setText(marker.optString("name")); hint = "Name" }
        val xField = coordinateInput("X").apply { setText(marker.optDouble("x").toString()) }
        val yField = coordinateInput("Y").apply { setText(marker.optDouble("y").toString()) }
        val zField = coordinateInput("Z").apply { setText(marker.optDouble("z").toString()) }
        form.addView(nameField); form.addView(xField); form.addView(yField); form.addView(zField)
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
                    saved.put(index, JSONObject().put("name", nm).put("x", xv).put("y", yv).put("z", zv))
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

    private fun launchGame() {
        val intent = packageManager.getLaunchIntentForPackage("com.mojang.minecraftpe")
        if (intent == null) Toast.makeText(this, "Minecraft Bedrock is not installed.", Toast.LENGTH_LONG).show()
        else try { startActivity(intent) } catch (e: Exception) { Toast.makeText(this, "Launch error: ${e.message}", Toast.LENGTH_LONG).show() }
    }
}
