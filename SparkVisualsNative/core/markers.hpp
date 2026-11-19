#pragma once

#include <cstdint>
#include <string>
#include <vector>

namespace sparkvisuals {

struct Waypoint {
    std::uint64_t id{};
    std::string name;
    std::string dimension;
    double x{};
    double y{};
    double z{};
    std::uint32_t color_rgba{0xC62828FF};
    bool enabled{true};
};

struct WaypointStore {
    std::vector<Waypoint> entries;

    bool add(Waypoint point) {
        if (point.name.empty()) return false;
        entries.push_back(std::move(point));
        return true;
    }

    bool erase(std::uint64_t id) {
        const auto old_size = entries.size();
        entries.erase(
            std::remove_if(entries.begin(), entries.end(),
                [id](const Waypoint& point) { return point.id == id; }),
            entries.end());
        return entries.size() != old_size;
    }
};

} // namespace sparkvisuals
