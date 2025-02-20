package cn.lunodio.commonview.util;

import java.util.Locale;


public class LanguageUtil {


    private static String display_language;

    public static void setDisplay_language(String display_language) {
        LanguageUtil.display_language = display_language;
    }

    public static String getDisplay_language() {
        return display_language;
    }



//    public static Locale getLocale() {
//        final Locale locale;
//        if (DisplayLanguageEnum.Chinese.getChinese().equals(LanguageUtil.getDisplay_language())) {
//            locale = new Locale("zh", "CN");
//        } else {
//            locale = new Locale("en");
//        }
//        return locale;
//    }

    private static Locale locale;


    public static void setLocale(Locale locale) {
        LanguageUtil.locale = locale;
    }

    public static Locale getLocale() {
        return locale;
    }

    //    Locale currentLocale = Locale.getDefault();
//        //zh
//        String language = currentLocale.getLanguage();
//        String country = currentLocale.getCountry();
//
//        System.out.println("Language: " + language);
//        System.out.println("Country: " + country);

}
