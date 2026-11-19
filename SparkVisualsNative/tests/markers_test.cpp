#include "../core/markers.hpp"
#include <cassert>
#include <cmath>
#include <limits>
#include <sstream>
using namespace sparkvisuals;
int main() {
 WaypointStore s;
 assert(s.add({1,"Base","overworld",3,4,0,0xC62828FF,true}));
 assert(!s.add({1,"Duplicate","overworld",0,0,0}));
 assert(!s.add({2,"","overworld",0,0,0}));
 assert(!s.add({3,"Bad","overworld",std::numeric_limits<double>::quiet_NaN(),0,0}));
 auto* p=s.find(1); assert(p);
 assert(std::abs(WaypointStore::distance(*p,0,0,0,"overworld")-5.0)<1e-9);
 assert(WaypointStore::distance(*p,0,0,0,"nether")<0);
 assert(s.set_enabled(1,false)); assert(!s.find(1)->enabled);
 assert(s.update(1,{1,"New Base","overworld",6,8,0,0xFF0000FF,true}));
 assert(std::abs(WaypointStore::distance(*s.find(1),0,0,0,"overworld")-10.0)<1e-9);
 assert(!s.update(1,{2,"Wrong id","overworld",0,0,0}));
 std::stringstream saved;
 assert(s.save(saved));
 WaypointStore restored;
 assert(restored.load(saved));
 assert(restored.entries.size()==1);
 assert(restored.find(1)->name=="New Base");
 assert(restored.find(1)->color_rgba==0xFF0000FF);
 WaypointStore unchanged;
 assert(unchanged.add({9,"Keep","overworld",1,2,3}));
 std::stringstream invalid(R"(SPARKVISUALS_WAYPOINTS 1 2
1 "A" "overworld" 0 0 0 1 1
1 "B" "overworld" 0 0 0 1 1
)");
 assert(!unchanged.load(invalid));
 assert(unchanged.find(9) && unchanged.entries.size()==1);
 assert(s.erase(1)); assert(!s.erase(1)); assert(!s.find(1));
}
