package com.hackathon.sequoia.sequoiahackathon.global;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Set;

public class SharedInfo {

	private static HashMap<String, Typeface> customFontCache = null;
    private static HashMap<String, Integer> navDrawerIcons = null;
    private static Set<String> wishlistVariantIds = null;
    private static String userAgent = null;

	public static Typeface getCustomFontTypeFace(Context context,
			String fontName) {
		HashMap<String, Typeface> customFontCache = SharedInfo.customFontCache;

		if (customFontCache == null) {
			customFontCache = new HashMap<String, Typeface>();
			SharedInfo.customFontCache = customFontCache;

		}

		if (customFontCache.containsKey(fontName)) {
			return customFontCache.get(fontName);
		} else {
			Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + fontName);
			customFontCache.put(fontName, typeFace);
			return typeFace;
		}
	}

    /*
    public static int getNavigationDrawerIcon(String permalink, boolean isExpanded) {
        if (navDrawerIcons == null) {
            navDrawerIcons = new HashMap<>();
        }

        Integer iconResId = navDrawerIcons.get(permalink);

        if (iconResId == null || permalink.equals("kids-furniture")) {
            if (permalink.equals("living-room-furniture")) {
                iconResId = R.drawable.nav_living_room_furniture;
            }

            else if (permalink.equals("dining-furniture")) {
                iconResId = R.drawable.nav_dining_furniture;
            }

            else if (permalink.equals("bedroom-furniture")) {
                iconResId = R.drawable.nav_bedroom_furniture;
            }

            else if (permalink.equals("storage")) {
                iconResId = R.drawable.nav_storage;
            }

            else if (permalink.equals("kids-furniture") && isExpanded) {
                iconResId = R.drawable.nav_kids_selected;
            }

            else if (permalink.equals("kids-furniture") && !isExpanded) {
                iconResId = R.drawable.nav_kids_default;
            }

            else if (permalink.equals("decor")) {
                iconResId = R.drawable.nav_decor;
            }

            else if (permalink.equals("collections")) {
                iconResId = R.drawable.nav_collections;
            }

            else if (permalink.equals("new-arrivals")) {
                iconResId = R.drawable.nav_new_arrivals;
            }

            else if (permalink.equals("lookcreator/background")) {
                iconResId = R.drawable.nav_background;
            }

            else if (permalink.equals("lookcreator/props")) {
                iconResId = R.drawable.nav_addons;
            }

            else if (permalink.equals("wardrobes")) {
                iconResId = R.drawable.nav_wardrobes;
            }

            navDrawerIcons.put(permalink, iconResId);
        }

        if (iconResId == null) {
            return 0;
        }

        return iconResId.intValue();
    }*/
}
