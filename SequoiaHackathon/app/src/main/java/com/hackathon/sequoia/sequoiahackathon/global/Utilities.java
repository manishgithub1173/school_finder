package com.hackathon.sequoia.sequoiahackathon.global;


/**
 * Created by manishkumar on 22/08/15.
 */
public class Utilities {

    public static String getFontName(int fontEnum) {
        switch (fontEnum) {
            case 0:
                return "Raleway-Regular.ttf";

            case 1:
                return "Raleway-Bold.ttf";

            case 2:
                return "Raleway-SemiBold.ttf";

            case 3:
                return "Raleway-ExtraBold.ttf";

            case 4:
                return "Raleway-Heavy.ttf";

            case 5:
                return "Raleway-Medium.ttf";

            case 6:
                return "Raleway-Light.ttf";

            case 7:
                return "Raleway-ExtraLight.ttf";

            case 8:
                return "Raleway-Thin.ttf";

            case 9:
                return "Raleway-Italic.ttf";

            case 10:
                return "Raleway-BoldItalic.ttf";

            default:
                return "Raleway-Regular.ttf";
        }
    }
}
