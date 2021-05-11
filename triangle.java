package vse_po_video;
import java.util.*;
import java.lang.*;

public class Main {

        public static void main(String[] args) {
            Scanner consol = new Scanner(System.in);
            String stroka = consol.nextLine();
            int number = consol.nextInt();
            if (number >= 5 || number <=0){
                System.out.println(stroka);
            }else {
                do{
                    System.out.println(stroka);
                    number--;
                } while (number>0 && number<5);
            }
        }
}
