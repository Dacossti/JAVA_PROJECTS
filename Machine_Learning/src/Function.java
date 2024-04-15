
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.Math.*;

public class Function {
    double[] coeff;
    public static int choice;

    Function()
    {
        coeff = new double [4];

        Scanner s = new Scanner(System.in);
        System.out.println("Enter coefficients k1, k2, k3, k4 of your function: ");
        for(int i=0; i<4;i++)
        {
            coeff[i] = s.nextDouble();
        }
    }
    Function(double[] c)
    {
        coeff = c;
    }

    double Integrate(double a, double b)
    {
        return ( (coeff[0]/4)*(pow(b, 4)) + (coeff[1]/3)*(pow(b, 3)) + (coeff[2]/2)*(pow(b, 2)) + (coeff[3])*b ) - ( (coeff[0]/4)*(pow(a, 4)) + (coeff[1]/3)*(pow(a, 3)) + (coeff[2]/2)*(pow(a, 2)) + (coeff[3])*a ) ;
    }

    Function Derivate ()
    {
        Function tmp;
        double[] c = new double[4];
        int j = 3;

        for(int i=0; i<4;i++)
        {
            if(i == 0){
                c[i] = 0;
            }else{
                c[i] = j*coeff[i - 1];
                j--;
            }
        }

        tmp = new Function(c);
        return tmp;
    }

    double Image (int x)
    {
        return this.coeff[0] * pow(x, 3) + this.coeff[1] * pow(x, 2) + this.coeff[2] * x + this.coeff[3];
    }

    double SumDist (int x1, int x2, int x3, Function F)
    {
        return abs(F.Image(x1) - this.Image(x1)) + abs(F.Image(x2) - this.Image(x2)) + abs(F.Image(x3) - this.Image(x3));
    }

    double MaxGraphDist(Function F)
    {
        Function tmp, deriv;
        double[] c = new double[4];

        for(int i=0; i<4;i++)
        {
            c[i] = F.coeff[i] - coeff[i];
        }

        tmp = new Function(c);
        deriv = tmp.Derivate();

        double delta = pow(deriv.coeff[2], 2) - 4*(deriv.coeff[1])*(deriv.coeff[3]);
        double s1, s2;

        if(delta <= 0){
            if((deriv.coeff[1]) > 0){
                return abs( c[0] * pow(100, 3) + c[1] * pow(100, 2) + c[2] * 100 + c[3] );
            }else{
                return abs( c[0] * pow(- 100, 3) + c[1] * pow(- 100, 2) + c[2] * 100 + c[3] );
            }
        }else{
            s1 = (-(deriv.coeff[2]) + sqrt(delta))/(2*(deriv.coeff[1]));
            s2 = (-(deriv.coeff[2]) - sqrt(delta))/(2*(deriv.coeff[1]));
            if((deriv.coeff[1]) > 0){
                if(s1 < s2){
                    return abs( c[0] * pow(s1, 3) + c[1] * pow(s1, 2) + c[2] * s1 + c[3] );
                }else{
                    return abs( c[0] * pow(s2, 3) + c[1] * pow(s2, 2) + c[2] * s2 + c[3] );
                }
            }else{
                if(s1 < s2){
                    return abs( c[0] * pow(s2, 3) + c[1] * pow(s2, 2) + c[2] * s2 + c[3] );
                }else{
                    return abs( c[0] * pow(s1, 3) + c[1] * pow(s1, 2) + c[2] * s1 + c[3] );
                }
            }
        }
    }

    double Error (Function F)
    {
        if(choice == 1){
            return abs(this.Integrate(-100, 100) - F.Integrate(-100, 100));
        }else if(choice == 2){
            return this.MaxGraphDist(F);
        }else{
            return this.SumDist(- 100, 0, 100, F);
        }

    }

    Function Parent_Gen (int d, int Size, Function target)
    {
        Function tmp;
        double[] c = new double[4];
        Vector<Function> gen = new Vector<>();
        Random rand = new Random();

        do
        {
            gen.clear();
            //Generation of descendants according to deviation d
            for(int i=0; i<Size; i++){
                for(int j=0; j<4; j++){
                    c[j] = coeff[j] + rand.nextInt(2*d + 1) - d;//(int) ((Math.random() * (2*d + 1)) -d);//random() % (2*d + 1) - d;
                }
                tmp = new Function(c);
                gen.addElement(tmp);
            }

            // Search of the most fitted descendant according to the chosen attribute
            tmp = gen.get(0);
            for(int i=1; i<gen.size(); i++){
                if( gen.get(i).Error(target) < tmp.Error(target))
                {
                    tmp = gen.get(i);
                }
            }

        }while(tmp.Error(target) >= this.Error(target));

        return tmp;
    }
    void Reinforcement_Gen (int d, int N, Function target){
        Function tmp = this;

        //double[] c = tmp.coeff;
        //Function tmp2 =  new Function(c);

        Random rand = new Random();

        System.out.println("_____________________________REINFORCEMENT PROCESS_______________________________");
        do{
            for(int j=0; j<4; j++){
                tmp.coeff[j] +=  rand.nextInt(2*d + 1) - d;
            }

            if(tmp.SumDist(- 100, 0, 100, target) < this.SumDist(- 100, 0, 100, target)){
                //tmp = tmp2;
                this.coeff = tmp.coeff;
                this.Print();
                System.out.println();
                tmp.coeff = this.coeff;
            }
            N--;

        }while(N>0);
    }


    void Print()
    {
        System.out.printf("%.3fx^3 + %.3fx^2 + %.3fx + %.3f%n", coeff[0], coeff[1], coeff[2], coeff[3]);
    }

    void PrintWithError(double e)
    {
        System.out.printf("%.3fx^3 + %.3fx^2 + %.3fx + %.3f\t\t\t\t %.3f%n", coeff[0], coeff[1], coeff[2], coeff[3], e);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Which way you wanna use?: 1- Indirect 2- Intermediate 3- Direct ");
        choice = s.nextInt();

        int dev, N;
        System.out.println("Enter number for maximum deviation (>= 1) and number of descendants for each generation: ");
        dev = s.nextInt();
        N = s.nextInt();

        Function target = new Function();
        System.out.println("Your target function is: ");
        target.Print();
        System.out.println();

        Function source = new Function();
        System.out.println("Your initial function is: ");
        source.Print();
        System.out.println();

        source.Reinforcement_Gen(3, 10, target);
        System.out.println("Your genetically reinforced initial function is: ");
        source.Print();
        System.out.println();

        System.out.println("_____________________________SEARCHING PROCESS_______________________________");
        System.out.println("    \t\tParents\t\t\t\t\t\t\t\t\tError");

        Function tmp = source;

        int step = 0;

        tmp.PrintWithError(source.Error(target));
        do
        {
            tmp = tmp.Parent_Gen(dev, N, target);
            tmp.PrintWithError(tmp.Error(target));
            //System.out.print("\t\t\t\t" + tmp.Error(target));
            step++;
        }while( (tmp.Error(target) >= pow(10, - 9)) && step < 5000);
    }


};
