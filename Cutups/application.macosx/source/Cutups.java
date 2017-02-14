import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Cutups extends PApplet {

/*
    Cutups by Martin Zaltz Austwick
 Written in Java-Processing with the Minim library
 
 */









int sample = 10;

Table options;
int startTime, startStamp;
String name, time, folderPath;

Minim minim;
AudioPlayer interview;

Table tracking;

String [] filenames;
Boolean loaded = false;


public void setup()
{
  
  tracking = loadTable("tracking.csv", "header");
  minim = new Minim(this);


  //hardcoded folder path
  folderPath = "/Users/martinaustwick/Music/iTunes/iTunes Media/Podcasts/Getting Better Acquainted/";
  //folderPath = "/Users/mza/Music/iTunes/iTunes Media/Podcasts/99% Invisible/";

  loadPreferences();
  filenames = (new File(folderPath)).list();    

  textSize(30);
  randomFile();
  randomer();


  //ff(30);
  startStamp = millis()/1000;
}

//void folderSelected(File selection) {
//  //this is basically a callback function - very unjavalike
//   if (selection == null) 
//   {
//     println("Window was closed or the user hit cancel.");
//   } 
//   else {
//     println("User selected " + selection.getAbsolutePath());
//     folderPath = selection.getAbsolutePath();



//     loaded = true;
//     loop();
// }


//}

public void draw()
{

  background(0);
  text(name, 20, 200);
  time = nf(startTime/60, 2, 0) + ":" + nf(startTime%60, 2, 0);
  text(time, 20, 300);
  //text(millis()/1000 - startStamp, 20, 400);
  int p = (interview.position()/1000)-startTime;
  text("Current time  " + p/60 + ":" + nf(p%60, 2, 0), 20, 450);


  //println(frameRate);
}

public void randomer()
{
  noLoop();

  //start time in s
  startTime = PApplet.parseInt(random(120.0f, (interview.length()/1000)-sample));

  interview.setLoopPoints(startTime*1000, (startTime+sample)*1000);
  interview.loop();

  startStamp = millis()/1000;
  loop();
}

public void randomFile()
{
  noLoop();
  if (!(interview==null)) interview.close();
  System.gc();
  name = filenames[floor(random(0.001f, filenames.length)-0.000000001f)];
  println(name);   
  interview = minim.loadFile(folderPath + name);
  loop();
}

public void loadPreferences()
{
    JSONObject prefs = loadJSONObject("preferences.json");
    folderPath = prefs.getString("path");
    sample = prefs.getInt("sampleLength");
}

public void keyPressed()
{
  if (key=='r')
  {
    randomer();
  }

  if (key=='f')
  {
    randomFile();
    randomer();
  }

  if (key=='t')
  {
    TableRow tee = tracking.addRow();
    tee.setString("File", name);
    tee.setString("timestart", time);
    tee.setInt("duration", sample);
    saveTable(tracking, "data/tracking.csv");
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Cutups" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
