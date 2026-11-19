#include "../core/settings.hpp"
#include <cassert>
#include <sstream>
int main(){
 sparkvisuals::ClientSettings s; s.zoomEnabled=true;s.zoomFactor=3.5f;s.hudScale=1.25f;
 s.panelX=125.0f;s.panelY=80.0f;s.cpsHud=true;s.accentRgb=0xB71C1C;
 std::stringstream data;assert(s.save(data));
 sparkvisuals::ClientSettings loaded;assert(loaded.load(data));assert(loaded.zoomEnabled&&loaded.cpsHud);
 assert(loaded.zoomFactor==3.5f&&loaded.hudScale==1.25f&&loaded.panelX==125.0f&&loaded.panelY==80.0f&&loaded.accentRgb==0xB71C1C);
 std::stringstream oldFormat("SPARKVISUALS_SETTINGS 1\n1 1 1 1 1 0 1 0\n2 1 123 456\n");
 sparkvisuals::ClientSettings legacy;assert(legacy.load(oldFormat));assert(legacy.panelX==12.0f&&legacy.panelY==12.0f);
 auto old=loaded;std::stringstream bad("SPARKVISUALS_SETTINGS 2\n1 1 1 1 1 0 1 0\n99 1 2000 10 123 456\n");
 assert(!loaded.load(bad));assert(loaded.zoomFactor==old.zoomFactor&&loaded.accentRgb==old.accentRgb);
 loaded.panelX=-1;std::stringstream out;assert(!loaded.save(out));
}