import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static String toJSON(String input) {
        StringBuilder output = new StringBuilder();
        output.append("{\n");

        Pattern message = Pattern.compile("Tour id: \"(\\d+)\".*?Location: \"(.+?)\".*?Person: \"(.+?)\"", Pattern.DOTALL);


        Matcher matcher = message.matcher(input);
        if (matcher.find()) {
            output.append("  \"id\": \"").append(matcher.group(1)).append("\",\n");
            output.append("  \"location\": \"").append(matcher.group(2)).append("\",\n");

            String[] personInfo = matcher.group(3).split(" ");
            if (personInfo.length >= 2) {
                output.append("  \"person\": {\n");
                output.append("    \"name\": \"").append(personInfo[0]).append("\",\n");
                output.append("    \"surname\": \"").append(personInfo[1]).append("\"\n");
                output.append("  }\n");
            }
        }

        output.append("}");
        return output.toString();
    }

    public static void main(String[] args) {
        try {

            String dataFormat1 = Files.readString(Paths.get("01/input.txt"));
            String dataFormat2 = toJSON(dataFormat1);

            Files.writeString(Paths.get("01/output.txt"), dataFormat2);
            System.out.println(dataFormat2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
