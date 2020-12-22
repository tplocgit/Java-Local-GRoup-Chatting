package hcmus.edu.project02;

import java.io.*;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class AccountsFileControllers {
    public static String DEFAULT_SEPARATOR = " ";

    public static TreeMap<String, Account> read(String path) throws IOException {
        TreeMap<String, Account> accountMap = new TreeMap<>();

        File file = new File(path);
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str = br.readLine();
            while (str != null) {
                StringTokenizer tokenizer = new StringTokenizer(str, DEFAULT_SEPARATOR);
                String user = tokenizer.nextToken();
                String pwd = tokenizer.nextToken();
                accountMap.put(user, new Account(user, pwd));
                str = br.readLine();
            }
            br.close();
        }

        return accountMap;
    }

    public static void write(String path, TreeMap<String, Account> accountMap) throws IOException {
        File file = new File(path);
        boolean isExist = file.exists();
        boolean isCreated = false;

        if(!isExist)
            isCreated = file.createNewFile();

        if (!isExist && !isCreated) {
            System.out.println("Error while opening file.");
            return;
        }

        PrintWriter pw = new PrintWriter(file);

        String out;
        for(Map.Entry<String, Account> entry : accountMap.entrySet()) {
            Account account = entry.getValue();
            out = account.getUser() + " " + account.getPassword();
            System.out.println(out);
            pw.println(out);
        }
        pw.close();
    }
}
