import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;


/**
 * Created by nkorchyk on 31.07.2015.
 */


public class Main {


    public static boolean DateCompare(String date){
        String text = date;
        if(text.contains("/")){
            String[] textParts = text.split("/");
            int checkDay = Integer.parseInt(textParts[1]);
            int checkMonth = Integer.parseInt(textParts[0]);
            int checkYear = Integer.parseInt(textParts[2]);

            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
            Date currentDate = new Date();
            int currDay = Integer.parseInt(dayFormat.format(currentDate));
            int currMonth = Integer.parseInt(monthFormat.format(currentDate));
            int currYear = Integer.parseInt(yearFormat.format(currentDate));

            String DATE_FORMAT = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.set(currYear,currMonth-1,currDay);
            c2.set(checkYear, checkMonth-1, checkDay);
            if (c1.before(c2)) {
                System.out.print("no check");
                return false;
            }

            if (c1.after(c2)) {
                System.out.print("make check");
                return true;
            }

            if (c1.equals(c2)) {
                System.out.print("no check the same date");
                return false;
            }
        }
        System.out.print("Incorrect format");
        return false;
    }



    public static void main(String args[]) throws IOException{
        String text = "Nick, Sony";
        //String text = "Sony, Nick";
        if (text.equals("Sony, Nick")){
            System.out.println("true");
        }
        else if(text.equals("Nick, Sony")){
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }
        /*File file= new File("c:/tmp/filename.png");
        BufferedImage image = ImageIO.read(file);
        // Getting pixel color by position x and y
        int x = 240,y = 216;
        int clr =  image.getRGB(x,y);
        int  red   = (clr & 0x00ff0000) >> 16;
        int  green = (clr & 0x0000ff00) >> 8;
        int  blue  =  clr & 0x000000ff;
        System.out.println("Red Color value = "+ red);
        System.out.println("Green Color value = "+ green);
        System.out.println("Blue Color value = "+ blue);*/
            }
}
