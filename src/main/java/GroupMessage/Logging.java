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
        //Определяем файл
        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text + "\n");
            }
            finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String logFileName) throws FileNotFoundException {
        File file = new File(logFileName);
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        exists(logFileName);

        try {
            //Объект для чтения файла в буфер

            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
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
