#include <pl/Mod.hpp>
#include <pl/ModMenu.hpp>
#include "../core/markers.hpp"
#include "../core/settings.hpp"
#include <array>
#include <filesystem>
#include <fstream>
#include <string>

class SparkVisualsMod {
public:
    static SparkVisualsMod& instance() {
        static SparkVisualsMod mod;
        return mod;
    }

    SparkVisualsMod() : self(*ll::mod::NativeMod::current()) {}
    [[nodiscard]] ll::mod::NativeMod& getSelf() const { return self; }

    bool load() {
        const auto dir = self.getConfigDir();
        std::error_code ec;
        std::filesystem::create_directories(dir, ec);
        if (ec) {
            self.getLogger().error("SparkVisuals: cannot create config directory: {}", ec.message());
            return false;
        }
        waypointFile = std::filesystem::path(dir) / "waypoints.dat";
        settingsFile = std::filesystem::path(dir) / "settings.cfg";

        if (std::filesystem::exists(waypointFile, ec) && !ec) {
            std::ifstream in(waypointFile);
            if (!in || !waypoints.load(in)) {
                self.getLogger().warn("SparkVisuals: invalid waypoint file; keeping empty waypoint list.");
                waypoints.entries.clear();
            }
        } else if (!saveWaypoints()) {
            self.getLogger().error("SparkVisuals: initial waypoint config save failed.");
            return false;
        }

        if (std::filesystem::exists(settingsFile, ec) && !ec) {
            std::ifstream in(settingsFile);
            if (!in || !settings.load(in)) {
                self.getLogger().warn("SparkVisuals: settings invalid; using defaults and rewriting config.");
                settings = sparkvisuals::ClientSettings{};
                if (!saveSettings()) return false;
            }
        } else if (!saveSettings()) {
            self.getLogger().error("SparkVisuals: initial settings save failed.");
            return false;
        }
        self.getLogger().info("SparkVisuals native module loaded. Waypoints: {}", waypoints.entries.size());
        return true;
    }

    bool enable() {
        auto registerResult = pl::modmenu::ModuleBuilder("sparkvisuals.native", "SparkVisuals")
            .modId(self.getId())
            .description("Cosmetic HUD test panel. Game data is not connected; feature toggles remain placeholders.")
            .defaultEnabled(true)
            .onToggle([this](std::string_view, bool enabled) {
                overlayEnabled = enabled;
                submitStatusOverlay(enabled);
            })
            .onConfigChanged([this](std::string_view, std::string_view key, std::string_view value) {
                if (key != "hud_scale") return;
                try {
                    const float scale = std::stof(std::string(value));
                    if (scale < 0.5f || scale > 2.0f) return;
                    settings.hudScale = scale;
                    if (saveSettings()) submitStatusOverlay(overlayEnabled);
                } catch (...) {
                    self.getLogger().warn("SparkVisuals: invalid HUD scale setting.");
                }
            })
            .config("coordinates", "Coordinates HUD (not rendered yet)", pl::modmenu::ConfigType::Toggle, "true")
            .config("fps_ping", "FPS / Ping HUD (not rendered yet)", pl::modmenu::ConfigType::Toggle, "true")
            .config("sprint_indicator", "Sprint Indicator (not rendered yet)", pl::modmenu::ConfigType::Toggle, "true")
            .config("potion_hud", "Potion HUD (not rendered yet)", pl::modmenu::ConfigType::Toggle, "true")
            .config("armor_hud", "Armor HUD (not rendered yet)", pl::modmenu::ConfigType::Toggle, "true")
            .config("cps", "CPS HUD (not rendered yet)", pl::modmenu::ConfigType::Toggle, "false")
            .config("crosshair", "Custom Crosshair (not rendered yet)", pl::modmenu::ConfigType::Toggle, "true")
            .config("zoom", "Zoom toggle (not implemented)", pl::modmenu::ConfigType::Toggle, "false")
            .config("hud_scale", "HUD panel scale", pl::modmenu::ConfigType::SliderFloat,
                    std::to_string(settings.hudScale), "0.5", "2.0")
            .registerModule();
        if (!registerResult) return false;
        submitStatusOverlay(true);
        return true;
    }

    void submitStatusOverlay(bool enabled) {
        if (!enabled) {
            pl::modmenu::submitDrawCommands("sparkvisuals.native", {});
            return;
        }
        const std::array<pl::modmenu::DrawCommand, 4> commands{{
            {.type = pl::modmenu::DrawCommandType::RectFilled,
             .x = 12.0f * settings.hudScale, .y = 12.0f * settings.hudScale, .w = 244.0f * settings.hudScale, .h = 72.0f * settings.hudScale,
             .color = 0xE6101010},
            {.type = pl::modmenu::DrawCommandType::RectFilled,
             .x = 12.0f * settings.hudScale, .y = 12.0f * settings.hudScale, .w = 3.0f * settings.hudScale, .h = 72.0f * settings.hudScale,
             .color = 0xFFC62828},
            {.type = pl::modmenu::DrawCommandType::Text,
             .x = 24.0f * settings.hudScale, .y = 20.0f * settings.hudScale, .size = 18.0f * settings.hudScale,
             .color = 0xFFFFFFFF, .text = "SparkVisuals HUD TEST"},
            {.type = pl::modmenu::DrawCommandType::Text,
             .x = 24.0f * settings.hudScale, .y = 46.0f * settings.hudScale, .size = 12.0f * settings.hudScale,
             .color = 0xFFFF7777, .text = "Game data not connected"},
        }};
        pl::modmenu::submitDrawCommands("sparkvisuals.native", commands);
    }
    bool disable() { return true; }

    bool unload() {
        return saveWaypoints() && saveSettings();
    }

private:
    bool writeAtomically(const std::filesystem::path& path,
                        const auto& writer) {
        if (path.empty()) return false;
        const auto temp = path.string() + ".tmp";
        {
            std::ofstream out(temp, std::ios::trunc);
            if (!out || !writer(out)) return false;
            out.flush();
            if (!out) return false;
        }
        std::error_code ec;
        std::filesystem::rename(temp, path, ec);
        if (ec) {
            std::filesystem::remove(path, ec);
            ec.clear();
            std::filesystem::rename(temp, path, ec);
        }
        return !ec;
    }

    bool saveWaypoints() {
        return writeAtomically(waypointFile, [this](std::ostream& out) { return waypoints.save(out); });
    }

    bool saveSettings() {
        return writeAtomically(settingsFile, [this](std::ostream& out) { return settings.save(out); });
    }

    ll::mod::NativeMod& self;
    sparkvisuals::WaypointStore waypoints;
    sparkvisuals::ClientSettings settings;
    bool overlayEnabled{true};
    std::filesystem::path waypointFile, settingsFile;
};

PL_REGISTER_MOD(SparkVisualsMod, SparkVisualsMod::instance())
