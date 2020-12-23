package hcmus.edu.project02;

public class HTMLText {
    public static String textSuccess(String content) {
        return "<html><span style='color:green;'>"+ content + "</span></html>";
    }

    public static String textDanger(String content) {
        return "<html><span style='color:red;'>"+ content + "</span></html>";
    }

    public static String textInfo(String content) {
        return "<html><span style='color:#17a2b8;'>" + content + "</span></html>";
    }

    public static String textPrimary(String content) {
        return "<html><span style='color:#007bff;'>" + content + "</span></html>";
    }
}
