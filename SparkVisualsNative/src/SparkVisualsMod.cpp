#include <pl/Mod.hpp>
#include <pl/ModMenu.hpp>
#include "../core/markers.hpp"
#include "../core/settings.hpp"
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
        return pl::modmenu::ModuleBuilder("sparkvisuals.prototype")
            .modId(self.getId())
            .description("SparkVisuals native loader entry. In-game HUD rendering is not yet integrated.")
            .defaultEnabled(true)
            .registerModule();
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
    std::filesystem::path waypointFile, settingsFile;
};

PL_REGISTER_MOD(SparkVisualsMod, SparkVisualsMod::instance())
