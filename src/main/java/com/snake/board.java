/*
 *  Made for college assignments/personal projects. Do not use without permission
 */
package com.snake;

/**
 *
 * @author echa
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class board extends JPanel implements ActionListener{
    private final int bWidth = 300; //size of board
    private final int bHeight = 300;
    private final int dotSize = 10; //size of food and snake
    private final int allDots = 900; //max dots on board
    private final int randPos = 29; //randomize position of spawn
    private final int delay = 140; //game speed
    
    private final int x[] = new int[allDots]; //store position of snake dots
    private final int y[] = new int[allDots];
    
    private int dots;
    private int appleX;
    private int appleY;
    
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private boolean inGame=true;
    
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    public board(){
        
        initBoard();
    }
    /*
    Initialize board
    */
    private void initBoard(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        
        setPreferredSize(new Dimension(bWidth, bHeight));
        loadImages();
        initGame();
    }
    
    private void loadImages(){
        ImageIcon iiDot=new ImageIcon("src/resources/dot.png");
        ball=iiDot.getImage();
        
        ImageIcon iiApple=new ImageIcon("src/resources/apple.png");
        apple=iiApple.getImage();
        
        ImageIcon iiHead=new ImageIcon("src/resources/head.png");
        head=iiHead.getImage();
    }
    /*
    Create snake and randomly put the apple
    */
    private void initGame(){
        
        dots=3;
        
        for(int i=0;i<dots;i++){
            x[i]=50-i*10;
            y[i]=50;
        }
        
        locateApple();
        
        timer=new Timer(delay,this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g){
        
        if(inGame){
            
            g.drawImage(apple, appleX, appleY, this);
            
            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head,x[i],y[i],this);
                }else{
                    g.drawImage(ball, x[i], y[i], this);
                }
            }
            
            Toolkit.getDefaultToolkit().sync();
            
        }else{
            
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g){
        
        String msg="Game Over";
        Font small=new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr=getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (bWidth-metr.stringWidth(msg))/2, bHeight/2);
    }
    
    /*
    Checks if head colides with apple
    */
    private void checkApple(){
        
        if((x[0]==appleX) && (y[0]==appleY)){
            
            dots++;
            locateApple();
        }
    }
    
    private void move(){
        
        for(int i=dots;i>0;i--){
            x[i]=x[(i-1)];
            y[i]=y[(i-1)];
        }
        
        if(leftDirection){
            x[0]-=dotSize;
        }
        if(rightDirection){
            x[0]+=dotSize;
        }
        if(upDirection){
            y[0]-=dotSize;
        }
        if(downDirection){
            y[0]+=dotSize;
        }
    }
    
    private void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i])){
                inGame=false;
            }
        }
        
        if(y[0]>=bHeight){
            inGame=false;
        }
        
        if(y[0]<0){
            inGame=false;
        }
        
        if(x[0]>=bWidth){
            inGame=false;
        }
        
        if(y[0]<0){
            inGame=false;
        }
        
        if(!inGame){
            timer.stop();
        }
    }
    private void locateApple(){
        int r=(int)(Math.random()*randPos);
        appleX=((r*dotSize));
        
        r=(int)(Math.random()*randPos);
        appleY=((r*dotSize));
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(inGame){
            checkApple();
            checkCollision();
            move();
            
        }
        
        repaint();
    }
}
