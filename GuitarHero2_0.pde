/*    random distance? 
      if note pressed before line should point still be possible?
      
things to do - shorten code, three in a row speeds up, fix randomization
    
  - notes fade in different colors
  - note speeds up depending on specific note score
  - correct placement flashes upon asdf press
  - 
  - 
  
*/

int score;
int aLoc; // location
int sLoc;
int dLoc;
int fLoc;
int aSpd; // speed
int sSpd;
int dSpd;
int fSpd;
int aClr; // color
int sClr;
int dClr;
int fClr;
int aScr; // score
int sScr;
int dScr;
int fScr;
int aFla; // length of flash
int sFla;
int dFla;
int fFla;
boolean aLine; // on the line
boolean sLine;
boolean dLine;
boolean fLine;
boolean aDone; // point scored
boolean sDone;
boolean dDone;
boolean fDone;

void setup () {
  size (400,400); // should work for a variety of sizes
  score = 0;
  aSpd = 1;
  sSpd = 1;
  dSpd = 1;
  fSpd = 1;
  aScr = 0;
  sScr = 0;
  dScr = 0;
  fScr = 0;
  aFla = 0;
  sFla = 0;
  dFla = 0;
  fFla = 0;
  aLoc = 20; // at top
  sLoc = -80;
  dLoc = -180;
  fLoc = -280;
  aLine = false;
  sLine = false;
  dLine = false;
  fLine = false;
  aDone = false;
  sDone = false;
  dDone = false;
  fDone = false;
}

void draw () {
  background (0);
  stroke (255); // drawing line
  line (0, height-70, width, height-70);
  noStroke ();
  score = aScr+sScr+dScr+fScr; // total score
  aLoc = aLoc+aSpd;
  sLoc = sLoc+sSpd; // motion
  dLoc = dLoc+dSpd;
  fLoc = fLoc+fSpd;
  // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  if (aLoc >= height+(random(20,1000))) { // waits random amount of time before reset
    aLoc = -20; // reset to top
    if (aDone==false) {
      aScr = aScr-1; // lose point if no point scored
    } else aDone = false;
  }
  if (aFla >= 0) {
     fill (255, 0, 0, 150);
     ellipse (width/5, height-70, 40, 40);
     aFla = aFla-1; // decreases remaining flash duration
  }
  aClr = (int)((aLoc-20)*255/(height-40)); // aLoc(20 to (height-20)) = aClr(0 to 255)
  fill (aClr,0,0); // red
  ellipse (width/5, aLoc, 40, 40); // draws note
  aLine = false; // aLine reset
  if ((aLoc>(height-90))&&(aLoc<(height-50))) { // checks to see if on line
    aLine = true;
  }
  if (aScr>=0) {
    aSpd = 1 + (int) Math.abs(sScr/3); // three points until speed up
  }
  // SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
  if (sLoc >= height+(random(20,1000))) {
    sLoc = -20;
    if (sDone==false) {
      sScr = sScr-1;
    } else sDone = false;
  }
  if (sFla >= 0) {
    fill (255, 255, 0, 150);
    ellipse (2*width/5, height-70, 40, 40);
    sFla = sFla-1;
  }
  sClr = (int)((sLoc-20)*255/(height-40));
  fill (sClr, sClr, 0); // (x,0,x)=magenta (0,x,x)=cyan (x,x,0)=yellow
  ellipse (2*width/5, sLoc, 40, 40);
  sLine = false;
  if ((sLoc>(height-90))&&(sLoc<(height-50))) {
    sLine = true;
  }
  if (sScr>=0) {
    sSpd = 1 + (int) Math.abs(sScr/3);
  }
  // DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
  if (dLoc >= height+(random(20,1000))) {
    dLoc = -20;
    if (dDone==false) {
      dScr = dScr-1;
    } else dDone = false;
  }
  if (dFla >= 0) {
    fill (0, 255, 0, 150);
    ellipse (3*width/5, height-70, 40, 40);
    dFla = dFla-1;
  }
  dClr = (int)((dLoc-20)*255/(height-40));
  fill (0, dClr, 0); // blue
  ellipse (3*width/5, dLoc, 40, 40);
  dLine = false;
  if ((dLoc>(height-90))&&(dLoc<(height-50))) {
    dLine = true;
  }
  if (dScr>=0) {
    dSpd = 1 + (int) Math.abs(dScr/3);
  }
  // FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
  if (fLoc >= height+(random(20,1000))) { 
    fLoc = -20;
    if (fDone==false) {
      fScr = fScr-1;
    } else fDone = false;
  }
  if (fFla >= 0) {
    fill (0, 0, 255, 150);
    ellipse (4*width/5, height-70, 40, 40);
    fFla = fFla-1;
  }
  fClr = (int)((fLoc-20)*255/(height-40));
  fill (0, 0, fClr); // green
  ellipse (4*width/5, fLoc, 40, 40);
  fLine = false;
  if ((fLoc>(height-90))&&(fLoc<(height-50))) {
    fLine = true;
  } 
  if (fScr>=0) {
    fSpd = 1 + (int) Math.abs(fScr/3);
  }
  // printing score
  fill (255);
  textSize (30);
  text (score, width-80, 80);
}

void keyPressed () {
   if (key=='a') {
     aFla = 5; // flash duration
     if ((aLine==true)&&(aDone==false)) { // checks if on the line and not yet scored
       aScr = aScr+1;
       aDone = true; // point now scored
     } else aScr = aScr-1;
  } else
   if (key=='s') {
     sFla = 5;
     if ((sLine==true)&&(sDone==false)) {
       sScr = sScr+1;
       sDone = true;
     } else sScr = sScr-1;
  } else
   if (key=='d') {
     dFla = 5;
     if ((dLine==true)&&(dDone==false)) {
       dScr = dScr+1;
       dDone = true;
     } else dScr = dScr-1;
  } else
   if (key=='f') {
     fFla = 5;
     if ((fLine==true)&&(fDone==false)) {
       fScr = fScr+1;
       fDone = true;
     } else fScr = fScr-1;
  } 
}
