package GroupMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Logging {

    public static void write(String logFileName, String text) throws FileNotFoundException {
        Date currentDate = new Date();
        Locale local = new Locale("ru","RU");
        DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, local);
        File file = new File(logFileName);
        //���������� ����
        try {
            //���������, ��� ���� ���� �� ���������� �� ������� ���
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter ��������� ����������� ������ � ����
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //���������� ����� � ����
                out.print(text + "\n");
            }
            finally {
                //����� ���� �� ������ ������� ����
                //����� ���� �� ���������
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String logFileName) throws FileNotFoundException {
        File file = new File(logFileName);
        //���� ����. ������ ��� ���������� ������
        StringBuilder sb = new StringBuilder();

        exists(logFileName);

        try {
            //������ ��� ������ ����� � �����

            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                //� ����� ��������� ��������� ����
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private static void exists(String logFileName) throws FileNotFoundException {
        File file = new File(logFileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }
    public static void update(String logFileName, String newText) throws FileNotFoundException {
        Date currentDate = new Date();
        Locale local = new Locale("ru","RU");
        DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, local);
        File file = new File(logFileName);
        exists(logFileName);
        StringBuilder sb = new StringBuilder();
        String oldFile = read(logFileName);
        sb.append(oldFile);
        sb.append("[" + df.format(currentDate) + "] " + newText);
        write(logFileName, sb.toString());
    }
}
