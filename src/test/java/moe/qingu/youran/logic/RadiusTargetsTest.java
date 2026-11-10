package moe.qingu.youran.logic;

import moe.qingu.youran.config.ItemConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RadiusTargetsTest {
    @Test
    void defaultsAreThoseOf134() {
        KeyValues.Result<RadiusTargets.Target> r = RadiusTargets.parse(ItemConfig.redBloodGemTargets);
        assertTrue(r.errors.isEmpty(), r.errors.toString());
        assertEquals(2, r.values.size());
        assertEquals("scp:old_man", r.values.get(0).entity);
        assertEquals(10, r.values.get(0).radius);
        assertEquals("scp:old_man_d", r.values.get(1).entity);
        assertEquals(5, r.values.get(1).radius);
    }

    @Test
    void needsModPrefixAndNonNegativeRadius() {
        KeyValues.Result<RadiusTargets.Target> r = RadiusTargets.parse(new String[]{"old_man=10", "scp:old_man=-1", "scp:x=0"});
        assertEquals(1, r.values.size());
        assertEquals(0, r.values.get(0).radius);
        assertEquals(2, r.errors.size());
    }
}
