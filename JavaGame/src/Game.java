import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Toolkit.getDefaultToolkit;

public class Game extends JFrame implements ActionListener {
    JPanel choicePanel, controlPanel;
    JLabel question;
    JButton case1, case2, case3, case4, caseAI, choice;
    Field F;
    Unit you;
    Color c;
    JComponent background;
    JOptionPane yesNo, playerStats;
    int play_again;
    int quitConf;
    BufferedImage myImage;
    Thread blink;
    Game() throws IOException {

        super("Java Game");
        setBounds(0, 0, getDefaultToolkit().getScreenSize().width,getDefaultToolkit().getScreenSize().height - 100); //Set the width and height of the JFrame.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        myImage = ImageIO.read(new File("C:\\Users\\yannt\\IdeaProjects\\JavaGame\\src\\img1.jpg"));
        Image resized = myImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

        background = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(resized, 0, 0, background);
            }
        };

        setContentPane(background);

        yesNo = new JOptionPane();

        F = new Field();
        F.fill();

        question= new JLabel("CHOOSE YOUR CHARACTER...", JLabel.CENTER);
        question.setFont(new Font("Times-new", Font.BOLD, 35));
        question.setBounds(350, 80, 600,100);

        choicePanel = new JPanel(new GridLayout(3, 2, 12, 12));
        choicePanel.setBounds(250, 250, getWidth() - 500, getHeight() - 400);
        choicePanel.setOpaque(false);

        case1 = new JButton("TRACER");
        case1.setFont(new Font("Times-new", Font.BOLD, 15));

        case2 = new JButton("JUMPER");
        case2.setFont(new Font("Times-new", Font.BOLD, 15));

        case3 = new JButton("CROSSER");
        case3.setFont(new Font("Times-new", Font.BOLD, 15));

        case4 = new JButton("KING");
        case4.setFont(new Font("Times-new", Font.BOLD, 15));

        caseAI = new JButton ("_AI_");
        caseAI.setFont(new Font("Times-new", Font.BOLD, 20));

        case1.addActionListener(this);
        case2.addActionListener(this);
        case3.addActionListener(this);
        case4.addActionListener(this);
        caseAI.addActionListener(this);

        add(question);
        choicePanel.add(case1);
        choicePanel.add(case2);
        choicePanel.add(case3);
        choicePanel.add(case4);
        choicePanel.add(caseAI);

        add(choicePanel);


        blink = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    question.setVisible(false);

                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    question.setVisible(true);
                }

            }
        });

        blink.start();

        setVisible(true);

        F.choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                F.notif.setText("");

                char key;
                int old_X, old_Y;

                if(e.getSource() == F.choice)
                {
                    old_X = you.Get_x();
                    old_Y = you.Get_y();
                    key = F.text.getText().charAt(0);

                    if (key == ' ' || (key != 'o' && key != 'a' && key != 'w' && key != 'd' && key != 'x' && key != 'q' && key != 'e' && key != 'z' && key != 'c')) {
                        F.notif.setText("NOTHING ENTERED!");
                        c = Color.WHITE;
                        F.choice.setBackground(c);

                    }else{
                        you.move(key);

                        if (F.status == -1) {
                            c = Color.ORANGE;
                            F.choice.setBackground(c);

                            JOptionPane.showMessageDialog(playerStats, "YOUR TOTAL SCORE IS " + you.GetScore() + ".\nYOU COLLECTED " + you.GetCoins() + " COIN(S).\nYOU LOST " + (3 - you.getHp()) + " LIVE(S).",
                                    "YOUR_STATS", JOptionPane.INFORMATION_MESSAGE);

                            quitConf = JOptionPane.showConfirmDialog(yesNo, "DO YOU REALLY WANT TO QUIT?",
                                    "QUIT_CONFIRMATION", JOptionPane.YES_NO_OPTION);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    if(quitConf == JOptionPane.YES_OPTION){
                                        F.thread.interrupt();
                                        JOptionPane.showMessageDialog(yesNo, "GOODBYE)))",
                                                "GAME_QUIT", JOptionPane.INFORMATION_MESSAGE);
                                        F.threadOpen = false;
                                        F.dispose();
                                        dispose();

                                    }
                                }
                            }).start();
                        }

                        if (F.status == 0) {
                            F.notif.setText("Good move)");
                            c = Color.CYAN;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 1) {
                            F.notif.setText("Out of bounds!!!");
                            c = Color.PINK;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 2) {
                            F.notif.setText("Obstacle(");
                            c = Color.ORANGE;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 3) {
                            F.notif.setText("Coins collected)))");
                            c = Color.GREEN;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 4) {
                            F.notif.setText("One live lost(((");
                            c = Color.RED;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 5) {

                            F.notif.setText("Last live lost(((");
                            c = Color.RED;
                            F.choice.setBackground(c);

                            JOptionPane.showMessageDialog(playerStats, "YOUR TOTAL SCORE IS " + you.GetScore() + ".\nYOU COLLECTED " + you.GetCoins() + " COIN(S).",
                                    "GAME_OVER", JOptionPane.INFORMATION_MESSAGE);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    F.setVisible(false);
                                }
                            }).start();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1500);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    play_again = JOptionPane.showConfirmDialog(yesNo, "DO YOU WANNA PLAY AGAIN?",
                                            "PLAY_AGAIN", JOptionPane.YES_NO_OPTION);
                                    if(play_again == JOptionPane.YES_OPTION){
                                        resetDatas();
                                    }else{
                                        F.thread.interrupt();
                                        F.dispose();
                                        dispose();
                                    }
                                }
                            }).start();

                        }

                        F.output(you.Get_x(), you.Get_y());
                        if(you.Get_x() != old_X || you.Get_y() != old_Y){
                            F.way(old_X, old_Y);
                        }
                    }
                    F.text.setText("");
                    F.score.setText("SCORE  : " + you.GetScore());
                    F.coins.setText("COINS  : " + you.GetCoins());
                }
            }
        });

        F.text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {

                F.notif.setText("");

                char key;
                int old_X, old_Y;

                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    old_X = you.Get_x();
                    old_Y = you.Get_y();
                    key = F.text.getText().charAt(0);

                    if (key == ' ' || (key != 'o' && key != 'a' && key != 'w' && key != 'd' && key != 'x' && key != 'q' && key != 'e' && key != 'z' && key != 'c')) {
                        F.notif.setText("INVALID(");
                        c = Color.WHITE;
                        F.choice.setBackground(c);

                    }else{
                        you.move(key);

                        if (F.status == -1) {
                            c = Color.ORANGE;
                            F.choice.setBackground(c);

                            JOptionPane.showMessageDialog(playerStats, "YOUR TOTAL SCORE IS " + you.GetScore() + ".\nYOU COLLECTED " + you.GetCoins() + " COIN(S).\nYOU LOST " + (3 - you.getHp()) + " LIVE(S).",
                                    "YOUR_STATS", JOptionPane.INFORMATION_MESSAGE);

                            quitConf = JOptionPane.showConfirmDialog(yesNo, "DO YOU REALLY WANT TO QUIT?",
                                    "QUIT_CONFIRMATION", JOptionPane.YES_NO_OPTION);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    if(quitConf == JOptionPane.YES_OPTION){
                                        F.thread.interrupt();
                                        JOptionPane.showMessageDialog(yesNo, "GOODBYE)))",
                                                "GAME_QUIT", JOptionPane.INFORMATION_MESSAGE);
                                        F.threadOpen = false;
                                        F.dispose();
                                        dispose();

                                    }
                                }
                            }).start();
                        }

                        if (F.status == 0) {
                            F.notif.setText("Good move)");
                            c = Color.CYAN;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 1) {
                            F.notif.setText("Out of bounds!!!");
                            c = Color.PINK;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 2) {
                            F.notif.setText("Obstacle(");
                            c = Color.ORANGE;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 3) {
                            F.notif.setText("Coins collected)))");
                            c = Color.GREEN;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 4) {
                            F.notif.setText("One live lost(((");
                            c = Color.RED;
                            F.choice.setBackground(c);
                        }

                        if (F.status == 5) {

                            F.notif.setText("Last live lost(((");
                            c = Color.RED;
                            F.choice.setBackground(c);

                            JOptionPane.showMessageDialog(playerStats, "YOUR TOTAL SCORE IS " + you.GetScore() + ".\nYOU COLLECTED  " + you.GetCoins() + " COIN(S).",
                                    "GAME_OVER", JOptionPane.INFORMATION_MESSAGE);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    F.setVisible(false);
                                }
                            }).start();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1500);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    play_again = JOptionPane.showConfirmDialog(yesNo, "DO YOU WANNA PLAY AGAIN?",
                                            "PLAY_AGAIN", JOptionPane.YES_NO_OPTION);
                                    if(play_again == JOptionPane.YES_OPTION){
                                        resetDatas();
                                    }else{
                                        F.thread.interrupt();
                                        F.dispose();
                                        dispose();
                                    }
                                }
                            }).start();

                        }

                        F.output(you.Get_x(), you.Get_y());

                        if(you.Get_x() != old_X || you.Get_y() != old_Y){
                            F.way(old_X, old_Y);
                        }
                    }
                    F.text.setText("");
                    F.score.setText("SCORE  : " + you.GetScore());
                    F.coins.setText("COINS   : " + you.GetCoins());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

        });

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == case1){
            you = new Tracer (F, 0, 0);
        }else if(e.getSource() == case2){
            you = new Jumper(F, 0, 0);
        }else if(e.getSource() == case3){
            you = new Crosser(F, 0, 0);
        }else if(e.getSource() == case4) {
            you = new King(F, 0, 0);
        }else{
            you = new King(F, 0, 0);
            F.controlPanel.setVisible(false);
        }

        blink.interrupt();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(750);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                F.output(you.Get_x(), you.Get_y());
                F.score.setText("SCORE  : " + you.GetScore());
                F.coins.setText("COINS   : " + you.GetCoins());
            }
        }).start();

    }

    void resetDatas ()
    {
        F.firstOpen = true;

        if(F.thread.isInterrupted()){
            F.threadOpen = true;
            F.thread.start();
        }

        F.text.setText("");
        F.notif.setText("WELCOME)))");
        F.choice.setBackground(null);
        F.score.setText("");
        F.coins.setText("");
        F.panel.removeAll();
        F.fill();
        F.revalidate();
        F.repaint();
        you = null;
    }

};
