import processing.core.PApplet;

public class Main extends PApplet {

    public static void main(String[] args){
        PApplet.main("Main", args);
    }

    public void settings() {
        size(700,700);
    }

    private MinesweeperGame game;
    private GridItem noGame;
    private int rows, columns;
    private int numBombs;
    private boolean isOver;
    private float centerX;
    private float centerY;
    private boolean generated;

    public void setup() {
        noStroke();
        rows = 6;
        columns = 12;
        numBombs = (int) (rows * columns * 0.2);
        centerX = width/2;
        centerY = height/2;
        generated = false;
        isOver = false;
        double[] nwCoords = {-centerX, -centerY};
        Point boardNW = new Point(nwCoords);
        game = new MinesweeperGame(boardNW, width, height, rows, columns);
        renderGame();
        textSize(16);
    }

    private void renderGame() {
        background(255);
        pushMatrix();
        translate(centerX, centerY);
        isOver = game.isOver();
        game.testRender(this);
        if (mouseButton == CENTER) {
            fill(0);
            text(game.getInfo(mouseX - centerX, mouseY - centerY), 30 - centerX, 30 - centerY);
        }
        popMatrix();
    }

    public void draw() {
        if (generated && mouseButton == CENTER) {
            renderGame();
        }
    }

    public void mousePressed() {
        if (!generated) {
            game.generateTiles(mouseX-centerX, mouseY-centerY, numBombs);
            generated = true;
            game.leftClick(mouseX-centerX, mouseY-centerY);
        } else if (mouseButton == LEFT) {
            game.leftClick(mouseX-centerX, mouseY-centerY);
        } else if (mouseButton == RIGHT) {
            game.rightClick(mouseX-centerX, mouseY-centerY);
        }
        renderGame();
    }

    public void keyPressed() {
        if (key == 'r' && isOver) {
            generated = false;
        } else {
            if (keyCode == ENTER) {
                game.getBoard().enterUncover();
            } else if (keyCode == TAB) {
                game.getBoard().tabFlag();
            }
        }
        renderGame();
    }

}
