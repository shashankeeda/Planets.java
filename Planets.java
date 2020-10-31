//Shashank Eeda
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
//Jpanel and ActionListener are both abstract/interfaces used to access paintComponent and actionPerformed
//JPanel contains the paintComponent method
//ActionListener contains the actionPerformed method
public class Planets extends JPanel implements ActionListener {
    //Data members used in this program, list stores the celestial bodies
    double acc=0;
    double force=0;
    double force2=0;
    String buffer="";
    //Code will execute every 5 milliseconds
    Timer tm=new Timer(5,this);
    BufferedReader br=null;
    List2<CelestialBodies> list;
    double pixerLength=0;
    int fileLength;
    //getInput reads the file and stores all the information in BufferedReader variable called br
    private void getInput(String fileName) {
        //buffer is used to try and count how many lines of data we have.
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String(allBytes);
            br=new BufferedReader(new FileReader("Planets.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    //createBodies reads the data file, creates celestial bodies and puts them all into a list
    public void createBodies(BufferedReader b){
        String[] stringArray;
        stringArray=buffer.split("\n");
        fileLength=stringArray.length-2;
        //This puts the contents of the Planets.txt file into both temp and br
        //If ArrayList is the first line it will use an ArrayList, otherwise it will use a LinkedList
        try {
            BufferedReader temp=new BufferedReader(new FileReader("Planets.txt"));
            temp.readLine();
            temp.readLine();
            if (b.readLine().equals("ArrayList")) {
                list = new ArrayList2<CelestialBodies>();
            }
            else {
                list = new LinkedList2<CelestialBodies>();
            }

            pixerLength=Double.parseDouble(b.readLine());

            //This is the part that creates the object and adds to the list and splits by the comma
            //We go through a loop and add the bodies to the list called list
            while(temp.readLine()!=null) {
                String[] arr=b.readLine().split(",");
                CelestialBodies CB=new CelestialBodies(arr[0],Double.parseDouble(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]),
                        Double.parseDouble(arr[4]),Double.parseDouble(arr[5]),Integer.parseInt(arr[6]));
                list.add(CB);
            }
            b.close();
            temp.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    //paintComponent repainsts the pictures every time with the help of ActionPerformed
    //ArrayList colors contains variety of colors that allow you to create different
    //planets with different colors
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ArrayList<Color> colors=new ArrayList<Color>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        colors.add(Color.ORANGE);
        colors.add(Color.WHITE);
        colors.add(Color.DARK_GRAY);
        int t=colors.size()-1;
        g.setColor(Color.RED);
        //fillOval is the method that is creating the planets
        //And a forloop is used to loop through the planets
        for(int i=0;i<fileLength;i++) {
            if(t>=i)
                t=0;
            g.setColor(colors.get(i));
            g.fillOval((int)Math.round(list.get(i).xcoord+list.get(i).xvel), (int)Math.round(list.get(i).ycoord+list.get(i).yvel), list.get(i).size, list.get(i).size);
        }
        tm.start();
    }
    //This is the pythagorean theorem that is used to calculate it
    public double pythagorean(double x, double y){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
    /*This is the action performed method, which is the most important method,
    it calculates the average force and changes the velocity by dividing by the mass and pixerlength
    the velocity is then added to the total distance. If the delta distance gets close to zero
    then we add the force by nothing.
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        //acc gets the mass of the individual planet each time
        //All if and elses are almost the same, just comparing different things
        for(int i=0;i<fileLength;i++){
            acc = list.get(i).mass;
            for(int j=0;j<fileLength;j++){
                if(i!=j) {
                    if (list.get(i).xcoord < list.get(j).xcoord) {
                        if(list.get(i).xcoord-list.get(j).xcoord<-3||list.get(i).xcoord-list.get(j).xcoord>3)
                            force += ((6.67 * Math.pow(10, -11))) * ((list.get(i).mass * list.get(j).mass)) / ((Math.pow(list.get(i).xcoord - list.get(j).xcoord, 2)));
                        else
                            force+=0;
                    }
                    else{
                        if(list.get(i).xcoord-list.get(j).xcoord<-3||list.get(i).xcoord-list.get(j).xcoord>3)
                            force -= ((6.67 * Math.pow(10, -11))) * ((list.get(i).mass * list.get(j).mass)) / ((Math.pow(list.get(i).xcoord - list.get(j).xcoord, 2)));
                        else
                            force-=0;
                    }
                    if(list.get(i).ycoord<list.get(j).ycoord) {
                        if(list.get(i).ycoord-list.get(j).ycoord<-3||list.get(i).ycoord-list.get(j).ycoord>3)
                            force2 += ((6.67 * Math.pow(10, -11))) * ((list.get(i).mass * list.get(j).mass)) / ((Math.pow(list.get(i).ycoord - list.get(j).ycoord, 2)));
                        else
                            force2+=0;
                    }
                    else {
                        if(list.get(i).ycoord-list.get(j).ycoord<-3||list.get(i).ycoord-list.get(j).ycoord>3)
                            force2 -= ((6.67 * Math.pow(10, -11))) * ((list.get(i).mass * list.get(j).mass)) / ((Math.pow(list.get(i).ycoord - list.get(j).ycoord, 2)));
                        else
                            force2-=0;
                    }
                }

            }

            //list.get(i).xvel=(force/acc)/pixerLength;
            //list.get(i).yvel=(force2/acc)/pixerLength;

            //This is used to make sure weather or not we have a negative velocity or a
            //positive velocity, depending on the type of velocity we will accumulate it differenctly

            if(fileLength>1) {
                if (force < 0) {
                    list.get(i).xvel = Math.sqrt((pythagorean(force, force2) / acc) / pixerLength) * -1;
                } else{
                    list.get(i).xvel = Math.sqrt((pythagorean(force, force2) / acc) / pixerLength);
                }
                if (force2 < 0) {
                    list.get(i).yvel = Math.sqrt((pythagorean(force, force2) / acc) / pixerLength) * -1;
                } else{
                    list.get(i).yvel = Math.sqrt((pythagorean(force, force2) / acc) / pixerLength);
                }
            }

            //We are changing the xcoord and ycoord every time to calculate a different force each time
            list.get(i).xcoord += list.get(i).xvel;
            list.get(i).ycoord += list.get(i).yvel;

            force=0;
            force2=0;
            acc=0;

        }

        repaint();
    }

    public static void main(String[] args) {
        //This is the main function which creates a planet object and creates the JFrame and runs the methods accordingly
        Planets t=new Planets();
        t.getInput("Planets.txt");
        t.createBodies(t.br);
        //By setting the visible to true, the JFrame will execute the two important methods
        JFrame jf=new JFrame();
        jf.setTitle("Planets");
        jf.setSize(768,768);
        jf.add(t);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
    //This is the celestialbodies class with all the data members and a contructor that is created.
    //There are 6 data members and they will all be initialized in the constructor
    public class CelestialBodies {
        String name;
        double mass;
        double xcoord;
        double ycoord;
        double xvel;
        double yvel;
        int size;
        public CelestialBodies(String name, double mass, double xcoord, double ycoord, double xvel, double yvel, int size){
            this.name=name;
            this.mass=mass;
            this.xcoord=xcoord;
            this.ycoord=ycoord;
            this.xvel=xvel;
            this.yvel=yvel;
            this.size=size;
        }
    }

}
