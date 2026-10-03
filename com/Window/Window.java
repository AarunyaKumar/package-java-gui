package com.Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Window extends MainPanel{
    public static final int FULL_SCREEN = 0, MAXIMUM_SIZE = 1, SELECT_SIZE = 2;
    JFrame frame = new JFrame();
    WindowListener windowListener;
    Graphics2D g2D;
    public int width, height;

    public void createWindow(int state, Dimension size, String name, JFrame relativeTo, boolean decorated, Image image, boolean useKey, boolean changeSizeOnResize){
        if(name!=null)
            frame.setTitle(name);
        frame.setUndecorated(!decorated);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(image);
        frame.setSize(size.width, size.height);
        switch (state){
            case FULL_SCREEN -> {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
            }
            case MAXIMUM_SIZE -> {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(false);
            }
            case SELECT_SIZE -> {
                if(size==null) {
                    frame.setSize(200, 200);
                }else{
                    frame.setSize(size.width, size.height);
                }
            }
        }
        frame.setLocationRelativeTo(relativeTo);
        frame.add(this);
        frame.setVisible(true);
        width = frame.getWidth();
        height = frame.getHeight();
        if(changeSizeOnResize)
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    width = frame.getWidth();
                    height = frame.getHeight();
                }
            });
        this.start(width, height,  useKey);
    }

    public void addWindowListener(WindowListener windowListener){this.windowListener = windowListener;}

    public JFrame getJFrame(){return frame;}

    public void setGraphics2D(Graphics2D g2D){if(g2D!=null)this.g2D = g2D;}

    public boolean key(int a){
        return key.contains(a);
    }
    public int mouseX(){
        return mousePosition.x;
    }
    public int mouseY(){
        return mousePosition.y;
    }
    public void startLoop(){
        this.loopPaint = true;
        this.repaint();
    }
    public void startLoop(int time){
        this.loopPaint = true;
        this.loopTime = time;
        this.repaint();
    }
    public void stopLoop(){
        this.loopPaint = false;
        loopOnce();
    }
    public void loopOnce(){
        this.loopOneTime = true;
        this.repaint();
    }
    public boolean isPainting(){
        return this.loopPaint;
    }
    public void loopOnce(int time){
        this.loopOneTime = true;
        this.loopTime = time;
        this.repaint();
    }

    public void drawFullScreen(Color color){
        g2D.setPaint(color);
        g2D.fillRect(0, 0, width, height);
    }
    public void setPaint(Color color){
        g2D.setPaint(color);
    }
    public void setPaint(){
        g2D.setPaint(Color.black);
    }
    public void drawString(String text, int x, int y, boolean center){
        if(center){
            g2D.drawString(text, x-g2D.getFontMetrics().stringWidth(text)/2, y);
        }else{
            g2D.drawString(text, x, y);
        }
    }
    public void drawRect(int x, int y, int width, int height){
        g2D.drawRect(x, y, width, height);
    }
    public void fillRect(int x, int y, int width, int height){
        g2D.fillRect(x, y, width, height);
    }
    public void drawRect(int x, int y, int width, int height, int size){
        for (int i = 0; i < size; i++)
            g2D.drawRect(x+i, y+i, width-i*2, height-i*2);
    }
    public void fillAndDrawRect(Color innerColor, Color outerColor, int x, int y, int width, int height){
        g2D.setPaint(innerColor);
        g2D.fillRect(x, y, width, height);
        g2D.setPaint(outerColor);
        g2D.drawRect(x, y, width, height);
    }
    public void fillAndDrawRect(Color innerColor, Color outerColor, int x, int y, int width, int height, int size){
        g2D.setPaint(innerColor);
        g2D.fillRect(x, y, width, height);
        g2D.setPaint(outerColor);
        for (int i = 0; i < size; i++)
            g2D.drawRect(x+i, y+i, width-i*2, height-i*2);
    }

    public void drawRect(Rectangle rect){
        g2D.drawRect(rect.x, rect.y, rect.width, rect.height);
    }
    public void fillRect(Rectangle rect){
        g2D.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
    public void drawRect(Rectangle rect, int size){
        for (int i = 0; i < size; i++)
            g2D.drawRect(rect.x+i, rect.y+i, rect.width-i*2, rect.height-i*2);
    }
    public void fillAndDrawRect(Color innerColor, Color outerColor, Rectangle rect){
        g2D.setPaint(innerColor);
        g2D.fillRect(rect.x, rect.y, rect.width, rect.height);
        g2D.setPaint(outerColor);
        g2D.drawRect(rect.x, rect.y, rect.width, rect.height);
    }
    public void fillAndDrawRect(Color innerColor, Color outerColor, Rectangle rect, int size){
        g2D.setPaint(innerColor);
        g2D.fillRect(rect.x, rect.y, rect.width, rect.height);
        g2D.setPaint(outerColor);
        for (int i = 0; i < size; i++)
            g2D.drawRect(rect.x+i, rect.y+i, rect.width-i*2, rect.height-i*2);
    }

    public void drawOval(int x, int y, int width, int height){
        g2D.drawOval(x, y, width, height);
    }
    public void fillOval(int x, int y, int width, int height){
        g2D.fillOval(x, y, width, height);
    }
    public void fillAndDrawOval(Color innerColor, Color outerColor, int x, int y, int width, int height){
        g2D.setPaint(innerColor);
        g2D.fillOval(x, y, width, height);
        g2D.setPaint(outerColor);
        g2D.drawOval(x, y, width, height);
    }
    public void drawLine(int x1, int y1, int x2, int y2){
        g2D.drawLine(x1, y1, x2, y2);
    }
    public void drawDot(int x, int y){
        g2D.drawLine(x, y, x, y);
    }
    public void drawDot(Point p){
        g2D.drawLine(p.x, p.y, p.x, p.y);
    }
    public void drawPolygon(int[] x, int[] y){
        g2D.drawPolygon(x, y, x.length);
    }
    public void fillPolygon(int[] x, int[] y){
        g2D.fillPolygon(x, y, x.length);
    }
    public void fillAndDrawPolygon(Color innerColor, Color outerColor, int[] x, int[] y){
        g2D.setPaint(innerColor);
        g2D.fillPolygon(x, y, x.length);
        g2D.setPaint(outerColor);
        g2D.drawPolygon(x, y, x.length);
    }
    public void drawPolygon(Polygon polygon){
        g2D.drawPolygon(polygon);
    }
    public void fillPolygon(Polygon polygon){
        g2D.fillPolygon(polygon);
    }
    public void fillAndDrawPolygon(Color innerColor, Color outerColor, Polygon polygon){
        g2D.setPaint(innerColor);
        g2D.fillPolygon(polygon);
        g2D.setPaint(outerColor);
        g2D.drawPolygon(polygon);
    }
    public void clearRect(int x, int y, int width, int height){
        g2D.clearRect(x, y, width, height);
    }
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle){
        g2D.drawArc(x, y, width, height, startAngle, arcAngle);
    }
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle){
        g2D.fillArc(x, y, width, height, startAngle, arcAngle);
    }
    public void fillAndDrawArc(Color innerColor, Color outerColor, int x, int y, int width, int height, int startAngle, int arcAngle){
        g2D.setPaint(innerColor);
        g2D.fillArc(x, y, width, height, startAngle, arcAngle);
        g2D.setPaint(outerColor);
        g2D.drawArc(x, y, width, height, startAngle, arcAngle);
    }
    public void drawRoundRect(int x, int y, int width, int height, int widthRound, int heightRound){
        g2D.drawRoundRect(x, y, width, height, widthRound, heightRound);
    }
    public void drawRoundRect(int x, int y, int width, int height, int widthRound, int heightRound, int size){
        for(int i=0; i<size; i++)
            g2D.drawRoundRect(x+i, y+i, width-i*2, height-i*2, widthRound, heightRound);
    }
    public void fillRoundRect(int x, int y, int width, int height, int widthRound, int heightRound){
        g2D.fillRoundRect(x, y, width, height, widthRound, heightRound);
    }
    public void fillAndDrawRoundRect(Color innerColor, Color outerColor, int x, int y, int width, int height, int widthRound, int heightRound){
        g2D.setPaint(innerColor);
        g2D.fillRoundRect(x, y, width, height, widthRound, heightRound);
        g2D.setPaint(outerColor);
        g2D.drawRoundRect(x, y, width, height, widthRound, heightRound);
    }
    public void fillAndDrawRoundRect(Color innerColor, Color outerColor, int x, int y, int width, int height, int widthRound, int heightRound, int size){
        g2D.setPaint(innerColor);
        g2D.fillRoundRect(x, y, width, height, widthRound, heightRound);
        g2D.setPaint(outerColor);
        for(int i=0; i<size; i++)
            g2D.drawRoundRect(x+i, y+i, width-i*2, height-i*2, widthRound, heightRound);
    }

    public void drawImage(BufferedImage img, int x, int y){
        g2D.drawImage(img, null, x, y);
    }
    public void drawImageAtCenter(BufferedImage img, int x, int y){
        g2D.drawImage(img, null, (int)(x-img.getWidth()*0.5), (int)(y-img.getHeight()*0.5));
    }
    public void drawImage(BufferedImage img, int x, int y, int direction){
        AffineTransform affineTransform = AffineTransform.getTranslateInstance(x, y);
        affineTransform.rotate(Math.toRadians(direction), img.getWidth()*0.5, img.getHeight()*0.5);
        g2D.drawImage(img, affineTransform, null);
    }
    public void drawImageAtCenter(BufferedImage img, int x, int y, int direction){
        AffineTransform affineTransform = AffineTransform.getTranslateInstance(x-img.getWidth()*0.5, y-img.getHeight()*0.5);
        affineTransform.rotate(Math.toRadians(direction), img.getWidth()*0.5, img.getHeight()*0.5);
        g2D.drawImage(img, affineTransform, null);
    }
    public void setTextSize(int size){
        g2D.setFont(new Font("Comic Sans MS", Font.PLAIN, size));
    }


    @Override
    protected void paintPrivate(Graphics2D g2D) {
        this.g2D = g2D;
        if(windowListener!=null){
            windowListener.paint(g2D);
        }
    }
    public void hideCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");
        setCursor(invisibleCursor);
    }
    public void showCursor(){
        setCursor(Cursor.getDefaultCursor());
    }

    public static boolean intersects(int x, int y, Rectangle rect){
        return ((rect.x<=x)&&(rect.x+rect.width>=x)&&(rect.y<=y)&&(rect.y+rect.height>=y));
    }
    public static boolean intersects(Point p, Rectangle rect){
        return ((rect.x<=p.x)&&(rect.x+rect.width>=p.x)&&(rect.y<=p.y)&&(rect.y+rect.height>=p.y));
    }
    public static boolean intersects(int x, int y, int rectX, int rectY, int rectW, int rectH){
        return ((rectX<=x)&&(rectX+rectW>=x)&&(rectY<=y)&&(rectY+rectH>=y));
    }
    public static boolean intersects(Point p, int rectX, int rectY, int rectW, int rectH){
        return ((rectX<=p.x)&&(rectX+rectW>=p.x)&&(rectY<=p.y)&&(rectY+rectH>=p.y));
    }

    public static double getValue(double T, double S, double E, double X){
        X++;
        double sub = E-S;
        double ratio = T/sub;
        double num = X/ratio;
        return S+num;
    }

    public void destroyWindow(){
        frame.dispose();
    }

    public static void print(String text){
        System.out.print(text);
    }
    public static void println(String text){
        System.out.println(text);
    }

    public static int random(int a, int b){
        return ((int)(Math.random()*(b-a+1))+a);
    }
    public static int random(int a){
        return ((int)(Math.random()*(a+1)));
    }

    public static int getDistance(int x1, int y1, int x2, int y2){
        return (int)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public static double getDirection(int x1, int y1, int x2, int y2){
        double theta = Math.atan2(y1-y2, x1-x2);
        theta += Math.PI/2.0;
        return Math.toDegrees(theta);
    }

    public static Point move(float x, float y, float direction, float move){
        return new Point((int)getSin(x, direction, move), (int)getCos(y, direction, move));
    }
    public static Point move(Point position, float direction, float move){
        return new Point((int)getSin(position.x, direction, move), (int)getCos(position.y, direction, move));
    }
    public static double getSin(float x, float direction, float move){
        return x+move*Math.sin(Math.toRadians(direction));
    }
    public static double getCos(float y, float direction, float move){
        return y+move*Math.cos(Math.toRadians(direction));
    }
    public static BufferedImage setBufferedImage(BufferedImage bufferedimage, int newW, int newH){
        Image tmp = bufferedimage.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage returnImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = returnImage.createGraphics();
        g2D.drawImage(tmp, 0, 0, null);
        g2D.dispose();
        return returnImage;
    }
    public void centerMouse() {
        try {
            Robot r = new Robot();
            r.mouseMove(width/2, height/2);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage readImage(String location) throws IOException {
        return ImageIO.read(new File(location));
    }
    public static void saveImage(BufferedImage bufferedImage, String location) throws IOException {
        ImageIO.write(bufferedImage, "png", new File(location));
    }
    public static void saveImage(BufferedImage bufferedImage, String location, String extension) throws IOException {
        ImageIO.write(bufferedImage, extension, new File(location));
    }

    public String[] readFile(String location) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        if(!((location.indexOf(".txt")==location.length()-4)&&(location.length()!=3))){
            location = location+".txt";
        }
        File file = new File(location);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            lines.add(st);

        return lines.toArray(new String[0]);
    }
    public void deleteFolder(String location){
        File[] files = new File(location).listFiles();
        for(int i = 0; i< Objects.requireNonNull(files).length; i++){
            if(files[i].isDirectory()){
                deleteFolder(files[i].toString());
            }else{
                files[i].delete();
            }
        }
        new File(location).delete();
    }
    public void createFolder(String location){
        new File(location).mkdirs();
    }

    public int vw(float x){
        return (int) ((double)(x/100)*width);
    }
    public int vh(float y){
        return (int) ((double)(y/100)*height);
    }
}
