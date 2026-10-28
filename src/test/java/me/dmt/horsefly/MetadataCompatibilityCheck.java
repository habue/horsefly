package me.dmt.horsefly;

import com.google.gson.JsonParser;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/** Checks the processed mod descriptor with Fabric's actual version parser. */
public final class MetadataCompatibilityCheck {
    public static void main(String[] args) throws Exception {
        var resource = MetadataCompatibilityCheck.class.getResourceAsStream("/fabric.mod.json");
        if (resource == null) throw new AssertionError("Processed fabric.mod.json not found");

        String range;
        try (var reader = new InputStreamReader(resource, StandardCharsets.UTF_8)) {
            range = JsonParser.parseReader(reader).getAsJsonObject()
                .getAsJsonObject("depends").get("meteor-client").getAsString();
        }
        var predicate = VersionPredicate.parse(range);
        for (String accepted : new String[] {"26.1.2-1", "26.1.2-42", "26.1.2-SNAPSHOT", "26.1.2"}) {
            if (!predicate.test(Version.parse(accepted))) {
                throw new AssertionError("Compatible Meteor rejected: " + accepted + " by " + range);
            }
        }
        for (String rejected : new String[] {"26.1.1", "26.2-1", "26.2-SNAPSHOT", "26.2", "26.3-1"}) {
            if (predicate.test(Version.parse(rejected))) {
                throw new AssertionError("Incompatible Meteor accepted: " + rejected + " by " + range);
            }
        }
        System.out.println("Fabric metadata compatibility check passed: " + range);
    }
}
