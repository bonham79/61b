package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;


public class MemoryGame {
    private int round = 1;
    Font font = new Font("Monaco", Font.BOLD, 30);
    private int width;
    private int height;
    private Random rand;
    private String display = "Watch!";
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = (long) Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white); 
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }


    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String answer = "";
        while (answer.length() < n) {
            answer = answer + CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        return answer;
    }

    public void topDisplay(String s) {
        StdDraw.setFont();
        StdDraw.text(.10 * width, .9 * height, "Round: " + Integer.toString(round));
        StdDraw.text(width / 2, .9 * height, s);
        StdDraw.text(.9 * width, .9 * height, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
        StdDraw.line(0, .85 * height, width, .85 * height);
        StdDraw.setFont(font);
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen

        //Clear
        //text
        StdDraw.clear(Color.BLACK);
        topDisplay(display);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
        StdDraw.pause(750);
        StdDraw.clear(Color.BLACK);
        topDisplay(display);
        StdDraw.show();
        StdDraw.pause(750);
        //TODO: If game is not over, display relevant game information at the top of the screen

    }

    public void flashSequence(String letters) {
        display = "Watch!";
        for(int i = 0, n = letters.length() ; i < n ; i++) {
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
        }

        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = "";
        display = "Type!";
        while (input.length() < n) {
            if(StdDraw.hasNextKeyTyped()) {
                input = input + Character.toString(StdDraw.nextKeyTyped());
            }
            StdDraw.clear(Color.BLACK);
            topDisplay(display);
            StdDraw.text(width / 2, height / 2, input);
            StdDraw.show();
        }
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        while (true) {
            String answer = generateRandomString(round);
            display = "";
            drawFrame("Round: " + Integer.toString(round));
            flashSequence(answer);
            String response = solicitNCharsInput(round);
            if (response.equals(answer)) {
                ++round;
                drawFrame("GOOD JOB!");
                continue;
            } else {break;}
        }
        drawFrame("Game Over.  You made it to round: " + Integer.toString(round));
        //TODO: Establish Game loop
    }

}
