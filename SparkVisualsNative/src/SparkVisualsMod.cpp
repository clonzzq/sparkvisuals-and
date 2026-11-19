#include <pl/Mod.hpp>
#include <pl/ModMenu.hpp>

class SparkVisualsMod {
public:
    static SparkVisualsMod& instance() {
        static SparkVisualsMod mod;
        return mod;
    }

    SparkVisualsMod() : self(*ll::mod::NativeMod::current()) {}

    [[nodiscard]] ll::mod::NativeMod& getSelf() const { return self; }

    bool load() {
        self.getLogger().info("SparkVisuals native module loaded (prototype).");
        return true;
    }

    bool enable() {
        // Register a simple menu entry to verify native Mod Menu integration.
        return pl::modmenu::ModuleBuilder("sparkvisuals.prototype")
            .modId(self.getId())
            .description("SparkVisuals native integration prototype. HUD features are not implemented yet.")
            .defaultEnabled(true)
            .registerModule();
    }

    bool disable() { return true; }
    bool unload() { return true; }

private:
    ll::mod::NativeMod& self;
};

PL_REGISTER_MOD(SparkVisualsMod, SparkVisualsMod::instance())
