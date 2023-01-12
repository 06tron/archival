int score;
int aLoc;
int sLoc;
int dLoc;
int fLoc;
int aSpd;
int sSpd;
int dSpd;
int fSpd;
int aClr;
int sClr;
int dClr;
int fClr;
int aScr;
int sScr;
int dScr;
int fScr;
boolean aLine;
boolean sLine;
boolean dLine;
boolean fLine;
boolean aDone;
boolean sDone;
boolean dDone;
boolean fDone;

void setup () {
  size (600,700);
  score = 0;
  aLoc = -20;
  sLoc = -120;
  dLoc = -220;
  fLoc = -320;
  aSpd = 2;
  sSpd = 2;
  dSpd = 2;
  fSpd = 2;
  aScr = 0;
  sScr = 0;
  dScr = 0;
  fScr = 0;
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
  stroke (255);
  fill (255);
  text ("Score:"+score, width-80, 40);
  line (0, height-70, width, height-70);
  noStroke ();
  score = aScr+sScr+dScr+fScr;
  aLoc = aLoc+aSpd;
  sLoc = sLoc+sSpd;
  dLoc = dLoc+dSpd;
  fLoc = fLoc+fSpd;
  // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  if (aLoc >= height+(random(40,200))) {
    aLoc = -20;//backwards
    if (aDone==false) {
      aScr = aScr-1;
    } else aDone = false;
  }
  aClr = (int)((aLoc-20)*255/(height-40));// aLoc(20-(height-20)) = aClr(0-255)
  fill (aClr, 0, aClr);//color
  ellipse (width/5, aLoc, 40, 40);
  aLine = false;
  if ((aLoc>(height-90))&&(aLoc<(height-50))) {
    aLine = true;
  }
  if (aScr>=0) {
    aSpd = 1 + (int) Math.abs(sScr/10); //ten points until speed up
  }
  // SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
  if (sLoc >= height+(random(40,400))) {
    sLoc = -20;
    if (sDone==false) {
      sScr = sScr-1;
    } else sDone = false;
  }
  sClr = (int)((sLoc-20)*255/(height-40));
  fill (sClr, 0, 0);
  ellipse (2*width/5, sLoc, 40, 40);
  sLine = false;
  if ((sLoc>(height-90))&&(sLoc<(height-50))) {
    sLine = true;
  }
  if (sScr>=0) {
    sSpd = 1 + (int) Math.abs(sScr/10);
  }
  // DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
  if (dLoc >= height+(random(40,600))) {
    dLoc = -20;
    if (dDone==false) {
      dScr = dScr-1;
    } else dDone = false;
  }
  dClr = (int)((dLoc-20)*255/(height-40));
  fill (0, 0, dClr);
  ellipse (3*width/5, dLoc, 40, 40);
  dLine = false;
  if ((dLoc>(height-90))&&(dLoc<(height-50))) {
    dLine = true;
  }
  if (dScr>=0) {
    dSpd = 1 + (int) Math.abs(dScr/10);
  }
  // FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
  if (fLoc >= height+(random(40,800))) { 
    fLoc = -20;
    if (fDone==false) {
      fScr = fScr-1;
    } else fDone = false;
  }
  fClr = (int)((fLoc-20)*255/(height-40));
  fill (0, fClr, 0);
  ellipse (4*width/5, fLoc, 40, 40);
  fLine = false;
  if ((fLoc>(height-90))&&(fLoc<(height-50))) {
    fLine = true;
  } 
  if (fScr>=0) {
    fSpd = 1 + (int) Math.abs(fScr/10);
  }
}

void keyPressed () {
  if (key=='a') {
     if ((aLine==true)&&(aDone==false)) {
       aScr = aScr+1;
       aDone = true;
     } else aScr = aScr-1;
  }
  if (key=='s') {
     if ((sLine==true)&&(sDone==false)) {
       sScr = sScr+1;
       sDone = true;
     } else sScr = sScr-1;
  } 
  if (key=='d') {
     if ((dLine==true)&&(dDone==false)) {
       dScr = dScr+1;
       dDone = true;
     } else dScr = dScr-1;
  } 
  if (key=='f') {
     if ((fLine==true)&&(fDone==false)) {
       fScr = fScr+1;
       fDone = true;
     } else fScr = fScr-1;
  } 
  
  
}
