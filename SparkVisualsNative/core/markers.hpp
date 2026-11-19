#pragma once
#include <algorithm>
#include <cmath>
#include <cstdint>
#include <string>
#include <utility>
#include <vector>
namespace sparkvisuals {
struct Waypoint {
 std::uint64_t id{}; std::string name; std::string dimension;
 double x{}, y{}, z{}; std::uint32_t color_rgba{0xC62828FF}; bool enabled{true};
};
struct WaypointStore {
 std::vector<Waypoint> entries;
 bool add(Waypoint p) {
  if (!p.id || p.name.empty() || p.dimension.empty() || !std::isfinite(p.x) ||
      !std::isfinite(p.y) || !std::isfinite(p.z) || find(p.id)) return false;
  entries.push_back(std::move(p)); return true;
 }
 bool update(std::uint64_t id, Waypoint p) {
  auto* old=find(id);
  if (!old || p.id!=id || p.name.empty() || p.dimension.empty() ||
      !std::isfinite(p.x) || !std::isfinite(p.y) || !std::isfinite(p.z)) return false;
  *old=std::move(p); return true;
 }
 bool set_enabled(std::uint64_t id,bool value) {
  auto* p=find(id); if(!p) return false; p->enabled=value; return true;
 }
 bool erase(std::uint64_t id) {
  auto n=entries.size();
  entries.erase(std::remove_if(entries.begin(),entries.end(),
   [id](const Waypoint& p){return p.id==id;}),entries.end());
  return entries.size()!=n;
 }
 const Waypoint* find(std::uint64_t id) const {
  auto i=std::find_if(entries.begin(),entries.end(),
   [id](const Waypoint& p){return p.id==id;});
  return i==entries.end()?nullptr:&*i;
 }
 Waypoint* find(std::uint64_t id) {
  auto i=std::find_if(entries.begin(),entries.end(),
   [id](const Waypoint& p){return p.id==id;});
  return i==entries.end()?nullptr:&*i;
 }
 static double distance(const Waypoint& p,double x,double y,double z,const std::string& dim) {
  if(p.dimension!=dim || !std::isfinite(x)||!std::isfinite(y)||!std::isfinite(z)) return -1.0;
  const auto dx=p.x-x, dy=p.y-y, dz=p.z-z;
  return std::sqrt(dx*dx+dy*dy+dz*dz);
 }
};
}