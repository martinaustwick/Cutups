/*
    Cutups by Martin Zaltz Austwick
 Written in Java-Processing with the Minim library
 
 */


import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

int sample = 10;

Table options;
int startTime, startStamp;
String name, time, folderPath;

Minim minim;
AudioPlayer interview;

Table tracking;

String [] filenames;
Boolean loaded = false;


void setup()
{
  size(800, 800);
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

void draw()
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

void randomer()
{
  noLoop();

  //start time in s
  startTime = int(random(120.0, (interview.length()/1000)-sample));

  interview.setLoopPoints(startTime*1000, (startTime+sample)*1000);
  interview.loop();

  startStamp = millis()/1000;
  loop();
}

void randomFile()
{
  noLoop();
  if (!(interview==null)) interview.close();
  System.gc();
  name = filenames[floor(random(0.001, filenames.length)-0.000000001)];
  println(name);   
  interview = minim.loadFile(folderPath + name);
  loop();
}

void loadPreferences()
{
    JSONObject prefs = loadJSONObject("preferences.json");
    folderPath = prefs.getString("path");
    sample = prefs.getInt("sampleLength");
}

void keyPressed()
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