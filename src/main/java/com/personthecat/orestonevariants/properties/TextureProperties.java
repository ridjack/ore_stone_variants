package com.personthecat.orestonevariants.properties;

import com.personthecat.orestonevariants.Main;
import com.personthecat.orestonevariants.config.Cfg;
import com.personthecat.orestonevariants.util.PathTools;
import net.minecraft.util.ResourceLocation;
import org.hjson.JsonObject;

import static com.personthecat.orestonevariants.util.CommonMethods.f;
import static com.personthecat.orestonevariants.util.CommonMethods.osvLocation;
import static com.personthecat.orestonevariants.util.HjsonTools.getBoolOr;
import static com.personthecat.orestonevariants.util.HjsonTools.getStringOr;

public class TextureProperties {
    /** The name of the image file to be generated. */
    public final String fileName;
    /** The original ore sprite to generate overlays from. */
    public final String original;
    /** The background texture used for extracting overlays. */
    public final String background;
    /** A path to these properties' overlay sprite. */
    public final String overlayPath;
    /** A ResourceLocation representing these properties' overlay sprite. */
    public final ResourceLocation overlayLocation;
    /** Whether the texture used already exists in the jar file. */
    public final boolean builtIn;
    /** Whether to use fancy "shaded" overlays. */
    public final boolean shade;

    /** The default overlay texture used for generating overlays. */
    private static final String DEFAULT_TEXTURE = "item/string";
    /** The default background texture used for generating overlays. */
    private static final String DEFAULT_MATCHER = "block/stone";

    public TextureProperties(ResourceLocation location, JsonObject json) {
        this(
            location,
            getStringOr(json, "original", DEFAULT_TEXTURE),
            getStringOr(json, "background", DEFAULT_MATCHER),
            getBoolOr(json, "builtIn", false),
            getBoolOr(json, "shade", true)
        );
    }

    public TextureProperties(ResourceLocation location, String original, String background, boolean builtIn, boolean shade) {
        this.original = extract(original);
        this.background = extract(background);
        this.builtIn = builtIn;
        this.shade = shade;
        this.fileName = getFileName(location, shade);
        this.overlayPath = f("assets/{}/textures/block/{}", Main.MODID, fileName);
        this.overlayLocation = osvLocation("block/" + fileName);
    }

    /** Syntactically more consistent than calling TextureProperties::new. */
    public static TextureProperties from(ResourceLocation location, JsonObject json) {
        return new TextureProperties(location, json);
    }

    private static String extract(String condensedPath) {
        ResourceLocation asRL = new ResourceLocation(condensedPath);
        return f("/assets/{}/textures/{}", asRL.getNamespace(), asRL.getPath());
    }

    /** Generates a file name to be associated with these properties' overlay sprite. */
    private static String getFileName(ResourceLocation location, boolean shade) {
        final String fileName = f("{}/{}_overlay", location.getNamespace(), location.getPath());
        if (shade && Cfg.shadedTextures.get()) {
            return PathTools.ensureShaded(fileName);
        }
        return fileName;
    }
}