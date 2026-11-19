#include <pl/Mod.hpp>
#include <pl/ModMenu.hpp>
#include "../core/markers.hpp"
#include "../core/settings.hpp"
#include <array>
#include <cmath>
#include <filesystem>
#include <fstream>
#include <string>
class SparkVisualsMod {
public:
 static SparkVisualsMod& instance(){static SparkVisualsMod mod;return mod;}
 SparkVisualsMod():self(*ll::mod::NativeMod::current()){}
 [[nodiscard]] ll::mod::NativeMod& getSelf()const{return self;}
 bool load(){
  const auto dir=self.getConfigDir();std::error_code ec;std::filesystem::create_directories(dir,ec);
  if(ec){self.getLogger().error("SparkVisuals: cannot create config directory: {}",ec.message());return false;}
  waypointFile=std::filesystem::path(dir)/"waypoints.dat";settingsFile=std::filesystem::path(dir)/"settings.cfg";
  if(std::filesystem::exists(waypointFile,ec)&&!ec){std::ifstream in(waypointFile);if(!in||!waypoints.load(in)){self.getLogger().warn("SparkVisuals: invalid waypoint file; keeping empty waypoint list.");waypoints.entries.clear();}}
  else if(!saveWaypoints()){self.getLogger().error("SparkVisuals: initial waypoint config save failed.");return false;}
  if(std::filesystem::exists(settingsFile,ec)&&!ec){std::ifstream in(settingsFile);if(!in||!settings.load(in)){self.getLogger().warn("SparkVisuals: settings invalid; using defaults and rewriting config.");settings=sparkvisuals::ClientSettings{};if(!saveSettings())return false;}}
  else if(!saveSettings()){self.getLogger().error("SparkVisuals: initial settings save failed.");return false;}
  self.getLogger().info("SparkVisuals native module loaded. Waypoints: {}",waypoints.entries.size());return true;
 }
 bool enable(){
  auto registerResult=pl::modmenu::ModuleBuilder("sparkvisuals.native","SparkVisuals").modId(self.getId())
    .description("Configurable cosmetic HUD test panel. No Minecraft game data is connected.")
   .defaultEnabled(true).onToggle([this](std::string_view,bool enabled){overlayEnabled=enabled;submitStatusOverlay(enabled);})
   .onConfigChanged([this](std::string_view,std::string_view key,std::string_view value){
    if(key=="accent_color" || key=="background_color"){
     int rgb{};
     if(!sparkvisuals::ClientSettings::parseRgb(value,rgb)){self.getLogger().warn("SparkVisuals: invalid color value.");return;}
     if(key=="accent_color") settings.accentRgb=rgb; else settings.backgroundRgb=rgb;
     if(saveSettings()) submitStatusOverlay(overlayEnabled); else self.getLogger().warn("SparkVisuals: color save failed.");
     return;
    }
    try{const float parsed=std::stof(std::string(value));
     if(key=="hud_scale"){if(parsed<0.5f||parsed>2.0f)return;settings.hudScale=parsed;}
     else if(key=="panel_x"){if(parsed<0.0f||parsed>1000.0f)return;settings.panelX=parsed;}
     else if(key=="panel_y"){if(parsed<0.0f||parsed>1000.0f)return;settings.panelY=parsed;}
     else return;
     if(saveSettings())submitStatusOverlay(overlayEnabled);else self.getLogger().warn("SparkVisuals: could not save HUD setting.");
    }catch(...){self.getLogger().warn("SparkVisuals: invalid HUD setting value.");}
   })
   .config("hud_scale","HUD panel scale",pl::modmenu::ConfigType::SliderFloat,std::to_string(settings.hudScale),"0.5","2.0")
   .config("panel_x","HUD panel X offset",pl::modmenu::ConfigType::SliderFloat,std::to_string(settings.panelX),"0.0","1000.0")
    .config("panel_y","HUD panel Y offset",pl::modmenu::ConfigType::SliderFloat,std::to_string(settings.panelY),"0.0","1000.0")
   .config("accent_color","HUD accent color",pl::modmenu::ConfigType::Color,"#C62828")
   .config("background_color","HUD panel background",pl::modmenu::ConfigType::Color,"#101010")
   .registerModule();
  if(!registerResult)return false;submitStatusOverlay(true);return true;
 }
 void submitStatusOverlay(bool enabled){
  if(!enabled){pl::modmenu::submitDrawCommands("sparkvisuals.native",{});return;}
  const auto scale=settings.hudScale,x=settings.panelX*scale,y=settings.panelY*scale;
  const std::array<pl::modmenu::DrawCommand,4> commands{{
   {.type=pl::modmenu::DrawCommandType::RectFilled,.x=x,.y=y,.w=244.0f*scale,.h=72.0f*scale,.color=(0xE6000000u | static_cast<unsigned>(settings.backgroundRgb))},
   {.type=pl::modmenu::DrawCommandType::RectFilled,.x=x,.y=y,.w=3.0f*scale,.h=72.0f*scale,.color=(0xFF000000u | static_cast<unsigned>(settings.accentRgb))},
   {.type=pl::modmenu::DrawCommandType::Text,.x=x+12.0f*scale,.y=y+8.0f*scale,.size=18.0f*scale,.color=0xFFFFFFFF,.text="SparkVisuals HUD TEST"},
   {.type=pl::modmenu::DrawCommandType::Text,.x=x+12.0f*scale,.y=y+34.0f*scale,.size=12.0f*scale,.color=0xFFFF7777,.text="Game data not connected"},
  }};
  pl::modmenu::submitDrawCommands("sparkvisuals.native",commands);
 }
 bool disable(){return true;}
 bool unload(){return saveWaypoints()&&saveSettings();}
private:
 bool writeAtomically(const std::filesystem::path& path,const auto& writer){
  if(path.empty())return false;const auto temp=path.string()+".tmp";
  {std::ofstream out(temp,std::ios::trunc);if(!out||!writer(out))return false;out.flush();if(!out)return false;}
  std::error_code ec;std::filesystem::rename(temp,path,ec);
  if(ec){std::filesystem::remove(path,ec);ec.clear();std::filesystem::rename(temp,path,ec);}return !ec;
 }
 bool saveWaypoints(){return writeAtomically(waypointFile,[this](std::ostream& out){return waypoints.save(out);});}
 bool saveSettings(){return writeAtomically(settingsFile,[this](std::ostream& out){return settings.save(out);});}
 ll::mod::NativeMod& self;sparkvisuals::WaypointStore waypoints;sparkvisuals::ClientSettings settings;
 bool overlayEnabled{true};std::filesystem::path waypointFile,settingsFile;
};
PL_REGISTER_MOD(SparkVisualsMod,SparkVisualsMod::instance())