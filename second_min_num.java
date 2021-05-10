import java.util.*;
import java.lang.*;

public class second_min_num {

        public static void main(String[] args) {

            Scanner console = new Scanner(System.in);

            int min1 = console.nextInt();
            int min2 = console.nextInt();
            while (console.hasNextInt()) {
                int next = console.nextInt();
                if ((min1 > min2) && (min2 != next)){
                    min1 = min1 < next? min1:next;
                    } else {
                    min2 = min2 < next? min2:next;
                    }

            }
            if (min1 > min2) System.out.println(min1);
            else System.out.println(min2);

        }
}

