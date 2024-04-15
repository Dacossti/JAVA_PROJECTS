
abstract class Unit
{
    protected int x, y, coins, hp, score;
    protected Field F;
    Unit (Field fi, int x0, int y0){
        this.F= fi;
        x=x0;
        y=y0;
        coins=0;
        hp=3;
        score=0;
    }
    
    abstract void move (char key);
    
    boolean IsAlive()
    {
        if(hp>0){
            return true;
        }else{
            return false;
        }
    }
    
    int Get_x()
    {
        return this.x;
    }
    
    int Get_y()
    {
        return this.y;
    }
    
    int GetCoins()
    {
        return this.coins;
    }
    
    int GetScore()
    {
      return this.score;  
    }
    
    int Gethp(){
        return this.hp;
    }
};

class Tracer extends Unit{
    
    Tracer(Field f, int x0, int y0){
        super (f, x0, y0);
    }
   
    void move(char key)
    {
        if(key != 'o'){
            int x1=x, y1=y;
            
            switch(key){
                case 'a': 
                y1--;
                break;
            
                case 'w':
                x1--;
                break;
            
                case 'd':
                y1++;
                break;
            
                case 'x':
                x1++;
                break;
            }
        
            if( this.F.Is_Legalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(this.F.CollectCoin(x, y)){
                    coins+=10;
                }else if(this.F.StepOnTrap(x,y)){
                    hp--;
                    if(hp==0){
                        System.out.println("\t__Game Over__");
                        System.out.println("\tScore : " + this.GetScore());
                        System.out.println("\tCoins : " + this.GetCoins());
                    }
                }
            }
        }else{
            System.out.println("\t__Goodbye__");
            System.out.println("\tScore : " + this.GetScore());
            System.out.println("\tCoins : " + this.GetCoins());
        }
   }
   
};

class Jumper extends Unit {
    
    Jumper(Field f, int x0, int y0){
        super (f, x0, y0);
    }
   
    void move(char key)
    {
        if(key != 'o'){
            int x1=x, y1=y;
            
            switch(key){
                case 'a': 
                y1-=2;
                break;
            
                case 'w':
                x1-=2;
                break;
            
                case 'd':
                y1+=2;
                break;
            
                case 'x':
                x1+=2;
                break;
            }
        
            if( this.F.Is_Legalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(this.F.CollectCoin(x, y)){
                    coins+=10;
                    score+=5;
                }else if(this.F.StepOnTrap(x, y)){
                    hp--;
                    if(hp==0){
                        System.out.println("\t__Game Over__");
                        System.out.println("\tScore : " + this.GetScore());
                        System.out.println("\tCoins : " + this.GetCoins());
                    }
                }
            }
        }else{
            System.out.println("\t__Goodbye__");
            System.out.println("\tScore : " + this.GetScore());
            System.out.println("\tCoins : " + this.GetCoins());
        }
   }
};
   
class Crosser extends Unit {
    
    Crosser (Field f, int x0, int y0){
        super (f, x0, y0);
    }
    
    void move(char key)
    {
        if(key != 'o'){
            int x1=x, y1=y;
            
            switch(key){
                case 'a': 
                x1--;
                y1--;
                break;
            
                case 'w':
                x1--;
                y1++;
                break;
            
                case 'd':
                x1++;
                y1++;
                break;
            
                case 'x':
                x1++;
                y1--;
                break;
            }
        
            if( this.F.Is_Legalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(this.F.CollectCoin(x, y)){
                    coins+=10;
                    score+=5;
                }else if(this.F.StepOnTrap(x, y)){
                    hp--;
                    if(hp==0){
                        System.out.println("\t__Game Over__");
                        System.out.println("\tScore : " + this.GetScore());
                        System.out.println("\tCoins : " + this.GetCoins());
                    }
                }
            }
        }else{
            System.out.println("\t__Goodbye__");
            System.out.println("\tScore : " + this.GetScore());
            System.out.println("\tCoins : " + this.GetCoins());
        }
   }
};


class King extends Unit {
    
    King (Field f, int x0, int y0){
        super (f, x0, y0);
    }
    
    void move(char key)
    {
        if(key != 'o'){
            int x1=x, y1=y;
            
            switch(key){
                case 'q': 
                x1--;
                y1--;
                break;
                
                case 'w':
                x1--;
                break;
            
                case 'e':
                x1--;
                y1++;
                break;
                
                case 'a':
                y1--;
                break;
                
                case 'd':
                y1++;
                break;
                
                case 'z':
                x1++;
                y1--;
                break;
                
                case 'x':
                x1++;
                break;
            
                case 'c':
                x1++;
                y1++;
                break;
            }
        
            if( F.Is_Legalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(F.CollectCoin(x, y)){
                    coins+=10;
                    score+=5;
                }else if(F.StepOnTrap(x, y)){
                    hp--;
                    if(hp==0){
                        System.out.println("\t__Game Over__");
                        System.out.println("\tScore : " + this.GetScore());
                        System.out.println("\tCoins : " + this.GetCoins());
                    }
                }
            }
        }else{
            System.out.println("\t__Goodbye__");
            System.out.println("\tScore : " + this.GetScore());
            System.out.println("\tCoins : " + this.GetCoins());
        }
    }
};