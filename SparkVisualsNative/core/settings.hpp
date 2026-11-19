#pragma once
#include <cmath>
#include <istream>
#include <ostream>
#include <string>
namespace sparkvisuals {
struct ClientSettings {
 bool sprintIndicator{true}, potionHud{true}, armorHud{true}, coordinatesHud{true};
 bool fpsPingHud{true}, cpsHud{false}, crosshair{true}, zoomEnabled{false};
 float zoomFactor{2.0f}, hudScale{1.0f}, panelX{12.0f}, panelY{12.0f};
 int accentRgb{0xC62828}, backgroundRgb{0x101010};
 bool valid() const {
  return std::isfinite(zoomFactor)&&zoomFactor>=1.0f&&zoomFactor<=8.0f&&
   std::isfinite(hudScale)&&hudScale>=0.5f&&hudScale<=2.0f&&
   std::isfinite(panelX)&&panelX>=0.0f&&panelX<=1000.0f&&
   std::isfinite(panelY)&&panelY>=0.0f&&panelY<=1000.0f&&
   accentRgb>=0&&accentRgb<=0xFFFFFF&&backgroundRgb>=0&&backgroundRgb<=0xFFFFFF;
 }
 bool save(std::ostream& out) const {
  if(!valid()) return false;
  out<<"SPARKVISUALS_SETTINGS 2\n"<<sprintIndicator<<' '<<potionHud<<' '<<armorHud<<' '
   <<coordinatesHud<<' '<<fpsPingHud<<' '<<cpsHud<<' '<<crosshair<<' '<<zoomEnabled<<'\n'
   <<zoomFactor<<' '<<hudScale<<' '<<panelX<<' '<<panelY<<' '<<accentRgb<<' '<<backgroundRgb<<'\n';
  return bool(out);
 }
 bool load(std::istream& in) {
  std::string magic; unsigned version{}; ClientSettings c;
  if(!(in>>magic>>version)||magic!="SPARKVISUALS_SETTINGS"||(version!=1&&version!=2)) return false;
  if(!(in>>c.sprintIndicator>>c.potionHud>>c.armorHud>>c.coordinatesHud>>c.fpsPingHud
    >>c.cpsHud>>c.crosshair>>c.zoomEnabled>>c.zoomFactor>>c.hudScale)) return false;
  if(version==2 && !(in>>c.panelX>>c.panelY)) return false;
  if(!(in>>c.accentRgb>>c.backgroundRgb)||!c.valid()) return false;
  std::string trailing; if(in>>trailing) return false;
  *this=c; return true;
 }
};
}