#pragma once

#include <string>
#include <vector>

namespace sparkvisuals {

struct ModuleDescriptor {
    std::string id;
    std::string display_name;
    std::string category;
    bool enabled{false};
};

inline std::vector<ModuleDescriptor> default_modules() {
    return {
        {"fps", "FPS HUD", "HUD", false},
        {"cps", "CPS HUD", "HUD", false},
        {"coordinates", "Coordinates HUD", "HUD", false},
        {"effects", "Potions HUD", "HUD", false},
        {"cooldowns", "Cooldowns HUD", "HUD", false},
        {"armor", "Armor HUD", "HUD", false},
        {"crosshair", "Custom Crosshair", "Visual", false},
        {"zoom", "Zoom", "Visual", false},
        {"markers", "Markers", "Utility", true},
        {"hud-layout", "HUD Layout", "Settings", true},
    };
}

} // namespace sparkvisuals
