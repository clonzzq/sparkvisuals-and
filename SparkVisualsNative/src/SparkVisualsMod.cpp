#include <pl/Mod.hpp>
#include <pl/ModMenu.hpp>
#include "../core/markers.hpp"
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
        // Persist only user-authored waypoints in the mod-owned config directory.
        // No game memory access, hooks, patches, or gameplay automation.
        const auto dir = self.getConfigDir();
        std::error_code ec;
        std::filesystem::create_directories(dir, ec);
        if (ec) {
            self.getLogger().error("SparkVisuals: cannot create config directory: {}", ec.message());
            return false;
        }
        waypointFile = std::filesystem::path(dir) / "waypoints.dat";
        if (std::filesystem::exists(waypointFile, ec) && !ec) {
            std::ifstream in(waypointFile);
            if (!in || !waypoints.load(in)) {
                self.getLogger().warn("SparkVisuals: waypoint file is invalid; starting with an empty list.");
                waypoints.entries.clear();
            }
        } else {
            saveWaypoints();
        }
        self.getLogger().info("SparkVisuals native module loaded. Waypoints: {}", waypoints.entries.size());
        return true;
    }

    bool enable() {
        return pl::modmenu::ModuleBuilder("sparkvisuals.prototype")
            .modId(self.getId())
            .description("SparkVisuals: native module is loaded. HUD rendering is not enabled in this build.")
            .defaultEnabled(true)
            .registerModule();
    }

    bool disable() { return true; }

    bool unload() {
        return saveWaypoints();
    }

private:
    bool saveWaypoints() {
        if (waypointFile.empty()) return true;
        const auto temp = waypointFile.string() + ".tmp";
        {
            std::ofstream out(temp, std::ios::trunc);
            if (!out || !waypoints.save(out)) return false;
        }
        std::error_code ec;
        std::filesystem::rename(temp, waypointFile, ec);
        if (ec) {
            std::filesystem::remove(waypointFile, ec);
            ec.clear();
            std::filesystem::rename(temp, waypointFile, ec);
        }
        return !ec;
    }

    ll::mod::NativeMod& self;
    sparkvisuals::WaypointStore waypoints;
    std::filesystem::path waypointFile;
};

PL_REGISTER_MOD(SparkVisualsMod, SparkVisualsMod::instance())
