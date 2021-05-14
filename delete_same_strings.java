

import java.util.Scanner;

/* 
Удаляем одинаковые строки
*/

public class Solution {
    public static String[] strings;

    public static void main(String[] args) {
        //напишите тут ваш код
        strings = new String[10];
        Scanner console = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            strings[i] = console.nextLine();
        }

        for (int i = 0; i < strings.length; i++) {
            String perem = strings[i];
            for (int j =i+ 1; j < strings.length; j++){
                if (perem == null){
                    break;
                }
                if (perem.equalsIgnoreCase(strings[j])) {
                    strings[i] = null;
                    strings[j] = null;
                }
            }
        }
        for (int i = 0; i < strings.length; i++) {
            System.out.print(strings[i] + ", ");
        }
    }
}
