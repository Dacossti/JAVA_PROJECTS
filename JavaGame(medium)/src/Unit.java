
abstract class Unit
{
    protected int x, y, coins, hp, score;
    protected Field F;
    Unit (Field fi, int x0, int y0){
        this.F = fi;
        x = x0;
        y = y0;
        coins = 0;
        hp = 3;
        score = 0;
    }

    abstract void move (char key);

    public boolean IsAlive()
    {
        if(hp > 0){
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

    int getHp() { return this.hp; }
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

            if( this.F.isLegalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(this.F.collectCoin(x, y)){
                    coins+=10;
                }else if(this.F.stepOnTrap(x,y)){
                    hp--;
                    if(hp==0){
                        F.status = 5;
                    }
                }
            }
        }else{
            F.status = -1;
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

            if( this.F.isLegalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(this.F.collectCoin(x, y)){
                    coins+=10;
                    score+=5;
                }else if(this.F.stepOnTrap(x, y)){
                    hp--;
                    if(hp==0){
                        F.status = 5;
                    }
                }
            }
        }else{
            F.status = -1;
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

            if( this.F.isLegalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(this.F.collectCoin(x, y)){
                    coins+=10;
                    score+=5;
                }else if(this.F.stepOnTrap(x, y)){
                    hp--;
                    if(hp==0){
                        F.status = 5;
                    }
                }
            }
        }else{
            F.status = -1;
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

            if( F.isLegalmove (x1, y1)){
                x=x1;
                y=y1;
                score+=5;
                if(F.collectCoin(x, y)){
                    coins+=10;
                    score+=5;
                }else if(F.stepOnTrap(x, y)){
                    hp--;
                    if(hp==0){
                        F.status = 5;
                    }
                }
            }
        }else{
            F.status = -1;
        }
    }

    void Play (){


    }

};