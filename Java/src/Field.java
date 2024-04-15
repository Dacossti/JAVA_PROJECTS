
import java.util.Random;

public class Field{
    
    char [] [] field;
    int w,h;
    
    Field(int h, int w){
        this.h=h;
        this.w=w;
        field = new char [h][w];
    }
    
    void Fill()
    {
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Random rand = new Random(); 
                int rng = rand.nextInt(100);
                if (rng < 50){
                    field[i][j]='_';
                }else if (rng < 70){
                    field[i][j]='0';
                }else if (rng < 85){
                    field[i][j]='$';
                }else{
                    field[i][j]='*';
                }
            }
        }
    }
    
    void Output (int x, int y)
    {
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                if(i==x && j==y){
                    System.out.print("Y ");
                }else{
                    System.out.print(field[i][j] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    boolean Is_Legalmove (int x, int y)
    {
        if(x<0 || x>=h || y<0 || y>=w){
            System.out.println("Вне пределов!");
            return false;
        }else if(field[x][y] == '0'){
            System.out.println("Препятствие!");
            return false;
        }else{
            return true;
        }
    }
    
    boolean CollectCoin (int x, int y)
    {
        boolean fl= field[x][y]=='$';
        if (fl){
            System.out.println("Копейки собраны!");
            field[x][y]= '_';
        }
        return fl;
    }
    
    boolean StepOnTrap (int x, int y)
    {
        boolean ft= field[x][y]=='*';
        if (ft){
            System.out.println("Одна жизнь потеряна!");
            field[x][y]= '_';
        }
        return ft;
    }
};

