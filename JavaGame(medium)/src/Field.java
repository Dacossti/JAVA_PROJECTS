

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

import static java.awt.Toolkit.*;


public class Field extends JFrame{
    int h, w;
    JPanel panel, controlPanel, playerStat;
    JButton choice;
    JLabel notif, score, coins;
    JTextField text;
    JButton [][] grid;

    JComponent background;
    BufferedImage myImage;

    int Xim1, Xim2, Yim1, Yim2;
    public int status;
    boolean firstOpen, threadOpen;

    public Thread thread;


    Field() throws IOException {
        super ("Java Game");
        setBounds(0, 0, getDefaultToolkit().getScreenSize().width,getDefaultToolkit().getScreenSize().height - 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        firstOpen = true;
        threadOpen = true;

        myImage = ImageIO.read(new File("C:\\Users\\yannt\\IdeaProjects\\JavaGame\\src\\img2.jpg"));
        Image resized1 = myImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        Image resized2 = myImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

        int width = this.getWidth();
        Xim1 = 0;
        Xim2 = width;

        background = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(resized1, Xim1, 0, background);
                Xim1 -= 1;
                Xim2 -= 1;
                g.drawImage(resized2, Xim2, 0, background);

                if(Xim2 <= 0){
                    Xim1 = -1;
                    Xim2 = width - 1;
                }

                if(!threadOpen){
                    Xim1 = 0;
                    Xim2 = width;
                }

            }
        };
        setContentPane(background);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(threadOpen){
                    background.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        });
        thread.start();

        h = 6;
        w = 10;

        panel = new JPanel(new GridLayout(h, w, 3, 3));
        panel.setBounds(100, 250, getWidth() - 200, getHeight() - 300);
        panel.setOpaque(false);

        grid = new JButton[h][w];
        controlPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBounds(170,90,800,100);

        choice = new JButton("Your move");
        choice.setFont(new Font("Times-new", Font.BOLD, 20));

        text = new JTextField(10);
        notif = new JLabel("WELCOME)))");
        notif.setFont(new Font("Times-new", Font.BOLD, 20));


        controlPanel.add(choice);
        controlPanel.add(text);

        controlPanel.add(notif);
        add(controlPanel);

        playerStat = new JPanel(new GridLayout(2, 1, 1, 1));
        playerStat.setBounds(950,150,800,100);
        playerStat.setLayout(new BoxLayout(playerStat, BoxLayout.Y_AXIS));
        playerStat.setOpaque(false);
        score = new JLabel("");
        score.setFont(new Font("Times-new", Font.BOLD, 20));

        coins = new JLabel("");
        coins.setFont(new Font("Times-new", Font.BOLD, 20));

        playerStat.add(score);
        playerStat.add(coins);
        add(playerStat);
        add(panel);
    }

    void fill()
    {
        for(int i=0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Random rand = new Random();
                int rng = rand.nextInt(100);
                if(i == 0 && j == 0){
                    grid[i][j]= new JButton("_");
                }else if (rng < 50) {
                    grid[i][j] = new JButton("_");
                    grid[i][j].setBorder(new LineBorder(Color.CYAN, 3));
                } else if (rng < 70) {
                    grid[i][j] = new JButton("0");
                    grid[i][j].setBorder(new LineBorder(Color.BLACK, 3));
                } else if (rng < 85) {
                    grid[i][j] = new JButton("$");
                    grid[i][j].setBorder(new LineBorder(Color.GREEN, 3));
                } else {
                    grid[i][j] = new JButton("*");
                    grid[i][j].setBorder(new LineBorder(Color.RED, 3));
                }
                grid[i][j].setFont(new Font("Times-new", Font.BOLD, 20));
                panel.add(grid[i][j]);
            }
        }
    }

    int[][] wayCost(){
        
        int[][] D = new int[h][w];

        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                if (grid[i][j].getText() == "_"){
                    D[i][j]=2;
                }else if (grid[i][j].getText() == "0"){
                    D[i][j]=-1;
                }else if (grid[i][j].getText() == "$"){
                    D[i][j]=0;
                }else if (grid[i][j].getText() == "*"){
                    D[i][j]=10;
                }
            }
        }
        
        return D;
    }


    void output (int x, int y)
    {
        if (firstOpen){
            setVisible(true);
        }
        grid[x][y].setBorder(new LineBorder(Color.YELLOW, 5));
        firstOpen = false;
    }

    void way(int x, int y)
    {
        grid[x][y].setBorder(new LineBorder(Color.MAGENTA, 3));
    }


    boolean isLegalmove (int x, int y)
    {
        boolean result = false;
        if (x < 0 || x >= h || y < 0 || y >= w) {
            status = 1;
        } else if (grid[x][y].getText() == "0") {
            status = 2;
        } else {
            result = true;
            status = 0;
        }
        return result;
    }

    boolean collectCoin (int x, int y)
    {

        boolean fl= grid[x][y].getText() == "$";
        if (fl){
            grid[x][y].setText("_");
            status = 3;
        }
        return fl;
    }

    boolean stepOnTrap (int x, int y)
    {
        boolean ft= grid[x][y].getText() == "*";
        if (ft) {
            grid[x][y].setText("_");
            status = 4;
        }
        return ft;
    }

};


