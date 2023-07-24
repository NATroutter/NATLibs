//package fi.natroutter.natlibs.utilities;
//
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
//
//public abstract class PluginTheme {
//
//    private static String serverName;
//    private String mainColor;
//    private String highlightColor;
//    private String TextColor;
//    private String separatorColor;
//
//    private static String prefixFormat = "<gradient:#f10e12:#f3393d><server_name></gradient>";
//    private static String mainFormat = "<color:#f2272a><value></color>";
//    private static String highlightFormat = "<color:#f3393d><value></color>";
//    private static String textFormat = "<color:#D3D3D3><value></color>";
//    private static String separatorFormat = "<color:#666666><value></color>";
//
//    public PluginTheme(String serverName, String mainColor, String highlightColor, String TextColor, String separatorColor) {
//        PluginTheme.serverName = serverName;
//        this.mainColor = mainColor;
//        this.highlightColor = highlightColor;
//        this.TextColor = TextColor;
//        this.separatorColor = separatorColor;
//    }
//
//    public static Component prefix() {
//        return Utilities.translateColors(prefixFormat,
//                Placeholder.parsed("server_name", serverName)
//        );
//    }
//    public static Component main(Object val) {
//        return mm.deserialize("<color:"+general.ThemeColor1+">" +val+ "</color>");
//    }
//    public static Component highlight(Object val) {
//        return mm.deserialize("<color:"+general.ThemeColor2+">" +val+ "</color>");
//    }
////
////    Component main();
////    Component highlight();
////    Component text();
////    Component separator();
//
//}
