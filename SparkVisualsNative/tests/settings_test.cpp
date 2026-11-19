#include "../core/settings.hpp"
#include <cassert>
#include <sstream>
int main(){
 sparkvisuals::ClientSettings s; s.zoomEnabled=true;s.zoomFactor=3.5f;s.hudScale=1.25f;
 s.cpsHud=true;s.accentRgb=0xB71C1C;std::stringstream data;assert(s.save(data));
 sparkvisuals::ClientSettings loaded;assert(loaded.load(data));assert(loaded.zoomEnabled&&loaded.cpsHud);
 assert(loaded.zoomFactor==3.5f&&loaded.hudScale==1.25f&&loaded.accentRgb==0xB71C1C);
 auto old=loaded;std::stringstream bad("SPARKVISUALS_SETTINGS 1\n1 1 1 1 1 0 1 0\n99 1 123 456\n");
 assert(!loaded.load(bad));assert(loaded.zoomFactor==old.zoomFactor&&loaded.accentRgb==old.accentRgb);
 loaded.zoomFactor=0.1f;std::stringstream out;assert(!loaded.save(out));
}