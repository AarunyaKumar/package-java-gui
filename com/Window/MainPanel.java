package com.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

abstract class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private int width, height;
    boolean loopPaint = false, loopOneTime = false;
    int loopTime = 40;
    Point mousePosition = new Point(-1, -1);
    public boolean mouseDown = false;
    ArrayList<Integer> key = new ArrayList<>();
    public static final int UP = 38, DOWN = 40, LEFT = 37, RIGHT = 39;
    void start(int width, int height, boolean useKey){
        this.width = width;
        this.height = height;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        if(useKey){
            this.addKeyListener(this);
            this.setFocusable(true);
            this.requestFocusInWindow();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if((loopPaint)||(loopOneTime)){
            paintPrivate(g2D);
            if(loopOneTime){
                loopOneTime = false;
            }else{
                try{
                    Thread.sleep(loopTime);
                    repaint();
                }catch(Exception error){
                    loopPaint = false;
                }
            }
        }
    }

    abstract protected void paintPrivate(Graphics2D g2D);

    @Override
    public void keyTyped(KeyEvent e) {
        keyType(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!key.contains(e.getKeyCode())){
            key.add(e.getKeyCode());
        }
        keyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key.remove((Object)e.getKeyCode());
        keyRelease(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClick(e, e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        mousePress(e, e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
        mouseRelease(e, e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseExit(e, e.getX(), e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseEnter(e, e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = e.getPoint();
        mouseDrag(e, e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint();
        mouseMove(e, e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelMove(e, e.getX(), e.getY());
    }




    public void keyType(KeyEvent e) {}

    public void keyPress(KeyEvent e) {}

    public void keyRelease(KeyEvent e) {}

    public void mouseClick(MouseEvent e, int x, int y) {}

    public void mousePress(MouseEvent e, int x, int y) {}

    public void mouseRelease(MouseEvent e, int x, int y) {}

    public void mouseEnter(MouseEvent e, int x, int y) {}

    public void mouseExit(MouseEvent e, int x, int y) {}

    public void mouseDrag(MouseEvent e, int x, int y) {}

    public void mouseMove(MouseEvent e, int x, int y) {}

    public void mouseWheelMove(MouseWheelEvent e, int x, int y) {}
}
