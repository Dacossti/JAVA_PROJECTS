
import java.util.Scanner;

public class Main
{
	public static void main (String[] args) {
	    
	    Field F = new Field (6 , 10);
        F.Fill();
    
        Scanner keyb = new Scanner (System.in);
        
        System.out.println("\t__How do you wanna play (1-Tracer 2-Jumper 3-Crosser 4-King?__");
        System.out.print ("Your choice: ");
        int choice = keyb.nextInt();
        
        Unit you;
        if(choice == 1){
            you = new Tracer (F, 0, 0);
        }else if(choice == 2){
            you = new Jumper (F, 0, 0);
        }else if(choice == 3){
            you = new Crosser (F, 0, 0);
        }else{
            you = new King (F, 0, 0);
        }

        System.out.println("\t__START__");
        F.Output(you.Get_x(), you.Get_y());
        char key;
        while( you.IsAlive()){
            
            System.out.print ("Your move: ");
            key = keyb.next().charAt(0);
            
            you.move(key);
            if(you.Gethp() != 0){
                if(key !='o'){
                    F.Output(you.Get_x(), you.Get_y());
                }
            }
        }
    }
}
