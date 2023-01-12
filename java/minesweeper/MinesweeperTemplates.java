package mpc.sweep;

/**
 * CLASS, has no instances or methods, only final variables. Used to create GridItems with colored pixels
 * TODO reformat
 */
public final class MinesweeperTemplates {

    /**
     * VARIABLES, bytes that represent an amount of color in an argb color system
     *  add 128 to go from (-128 to 127) to (0 to 255)
     */
    private static final byte full = 127; // 255
    private static final byte half = 0; // 128
    private static final byte none = -128; // 0

    /**
     * VARIABLES, letters that represent colors, to be used in the artistic templates
     */
    private static final byte[] S = {full,half,half,half}; // Shadow: dark grey
    private static final byte[] L = {full, 120, 120, 120}; // Light: light grey
    private static final byte[] G = {full,  63,  63,  63}; // Grey: middle grey
    private static final byte[] B = {full,none,none,full}; // Blue: for #1
    private static final byte[] V = {full,none,half,none}; // Green: for #2
    private static final byte[] R = {full,full,none,none}; // Red: for #3
    private static final byte[] D = {full,none,none,half}; // Dark Blue: for #4
    private static final byte[] M = {full,half,none,none}; // Dark Red: for #5
    private static final byte[] C = {full,none,half,half}; // Cyan: for #6
    private static final byte[] K = {full,none,none,none}; // Black: for #7
    private static final byte[] N = {none,0,0,0}; // completely transparent

    /**
     * VARIABLE, a byte[][][] that has a single byte[] that represents a transparent color in argb
     */
    public static final byte[][][] NOTHING = {
            {N},
    };

    /**
     * VARIABLES, the rest of the variables in this class are pixel-art templates for minesweeper
     */
    public static final byte[][][] COVERED_TILE = {
            {L,L,L,L,L,L,L,L,L,L,L,L,L,L,L,G},
            {L,L,L,L,L,L,L,L,L,L,L,L,L,L,G,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,G,S,S,S,S,S,S,S,S,S,S,S,S,S,S},
            {G,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S},
    };

    public static final byte[][][] FLAGGED_TILE = {
            {L,L,L,L,L,L,L,L,L,L,L,L,L,L,L,G},
            {L,L,L,L,L,L,L,L,L,L,L,L,L,L,G,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,R,R,G,G,G,G,G,S,S},
            {L,L,G,G,G,R,R,R,R,G,G,G,G,G,S,S},
            {L,L,G,G,R,R,R,R,R,G,G,G,G,G,S,S},
            {L,L,G,G,G,R,R,R,R,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,R,R,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,K,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,G,G,K,G,G,G,G,G,S,S},
            {L,L,G,G,G,G,K,K,K,K,G,G,G,G,S,S},
            {L,L,G,G,K,K,K,K,K,K,K,K,G,G,S,S},
            {L,L,G,G,K,K,K,K,K,K,K,K,G,G,S,S},
            {L,L,G,G,G,G,G,G,G,G,G,G,G,G,S,S},
            {L,G,S,S,S,S,S,S,S,S,S,S,S,S,S,S},
            {G,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S},
    };

    public static final byte[][][] BOMB = {
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,K,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,K,N,N,N,N,N,N,N},
            {N,N,N,N,K,N,K,K,K,K,K,N,K,N,N,N},
            {N,N,N,N,N,K,K,K,K,K,K,K,N,N,N,N},
            {N,N,N,N,K,K,L,L,K,K,K,K,K,N,N,N},
            {N,N,N,N,K,K,L,L,K,K,K,K,K,N,N,N},
            {N,N,K,K,K,K,K,K,K,K,K,K,K,K,K,N},
            {N,N,N,N,K,K,K,K,K,K,K,K,K,N,N,N},
            {N,N,N,N,K,K,K,K,K,K,K,K,K,N,N,N},
            {N,N,N,N,N,K,K,K,K,K,K,K,N,N,N,N},
            {N,N,N,N,K,N,K,K,K,K,K,N,K,N,N,N},
            {N,N,N,N,N,N,N,N,K,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,K,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
    };

    public static final byte[][][] RED_BOMB = {
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,M,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,M,N,N,N,N,N,N,N},
            {N,N,N,N,M,N,M,M,M,M,M,N,M,N,N,N},
            {N,N,N,N,N,M,M,M,M,M,M,M,N,N,N,N},
            {N,N,N,N,M,M,L,L,M,M,M,M,M,N,N,N},
            {N,N,N,N,M,M,L,L,M,M,M,M,M,N,N,N},
            {N,N,M,M,M,M,M,M,M,M,M,M,M,M,M,N},
            {N,N,N,N,M,M,M,M,M,M,M,M,M,N,N,N},
            {N,N,N,N,M,M,M,M,M,M,M,M,M,N,N,N},
            {N,N,N,N,N,M,M,M,M,M,M,M,N,N,N,N},
            {N,N,N,N,M,N,M,M,M,M,M,N,M,N,N,N},
            {N,N,N,N,N,N,N,N,M,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,M,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
    };

    public static final byte[][][] NOT_BOMB = {
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,K,N,N,N,N,N,N,N},
            {N,N,R,R,N,N,N,N,K,N,N,N,R,R,N,N},
            {N,N,N,R,R,N,K,K,K,K,K,R,R,N,N,N},
            {N,N,N,N,R,R,K,K,K,K,R,R,N,N,N,N},
            {N,N,N,N,K,R,R,L,K,R,R,K,K,N,N,N},
            {N,N,N,N,K,K,R,R,R,R,K,K,K,N,N,N},
            {N,N,K,K,K,K,K,R,R,K,K,K,K,K,K,N},
            {N,N,N,N,K,K,R,R,R,R,K,K,K,N,N,N},
            {N,N,N,N,K,R,R,K,K,R,R,K,K,N,N,N},
            {N,N,N,N,R,R,K,K,K,K,R,R,N,N,N,N},
            {N,N,N,R,R,N,K,K,K,K,K,R,R,N,N,N},
            {N,N,R,R,N,N,N,N,K,N,N,N,R,R,N,N},
            {N,N,N,N,N,N,N,N,K,N,N,N,N,N,N,N},
            {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
    };

    public static final byte[][][][] NUMBERS = {

            NOTHING, // in place of zero

            { // ONE
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,N,B,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,B,B,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,B,B,B,N,N,N,N,N,N},
                    {N,N,N,N,B,B,B,B,B,B,B,B,N,N,N,N},
                    {N,N,N,N,B,B,B,B,B,B,B,B,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // TWO
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,V,V,V,V,V,V,V,V,N,N,N,N},
                    {N,N,N,V,V,V,V,V,V,V,V,V,V,N,N,N},
                    {N,N,N,V,V,V,N,N,N,N,V,V,V,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,V,V,V,N,N,N},
                    {N,N,N,N,N,N,N,N,V,V,V,V,N,N,N,N},
                    {N,N,N,N,N,N,V,V,V,V,V,N,N,N,N,N},
                    {N,N,N,N,V,V,V,V,V,N,N,N,N,N,N,N},
                    {N,N,N,V,V,V,V,N,N,N,N,N,N,N,N,N},
                    {N,N,N,V,V,V,V,V,V,V,V,V,V,N,N,N},
                    {N,N,N,V,V,V,V,V,V,V,V,V,V,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // THREE
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,R,R,R,R,R,R,R,R,R,N,N,N,N},
                    {N,N,N,R,R,R,R,R,R,R,R,R,R,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,R,R,R,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,R,R,R,N,N,N},
                    {N,N,N,N,N,N,R,R,R,R,R,R,N,N,N,N},
                    {N,N,N,N,N,N,R,R,R,R,R,R,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,R,R,R,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,R,R,R,N,N,N},
                    {N,N,N,R,R,R,R,R,R,R,R,R,R,N,N,N},
                    {N,N,N,R,R,R,R,R,R,R,R,R,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // FOUR
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,D,D,D,N,D,D,D,N,N,N,N},
                    {N,N,N,N,N,D,D,D,N,D,D,D,N,N,N,N},
                    {N,N,N,N,D,D,D,N,N,D,D,D,N,N,N,N},
                    {N,N,N,N,D,D,D,N,N,D,D,D,N,N,N,N},
                    {N,N,N,D,D,D,D,D,D,D,D,D,D,N,N,N},
                    {N,N,N,D,D,D,D,D,D,D,D,D,D,N,N,N},
                    {N,N,N,N,N,N,N,N,N,D,D,D,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,D,D,D,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,D,D,D,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,D,D,D,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // FIVE
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,M,M,M,M,M,M,M,M,M,M,N,N,N},
                    {N,N,N,M,M,M,M,M,M,M,M,M,M,N,N,N},
                    {N,N,N,M,M,M,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,M,M,M,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,M,M,M,M,M,M,M,M,M,N,N,N,N},
                    {N,N,N,M,M,M,M,M,M,M,M,M,M,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,M,M,M,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,M,M,M,N,N,N},
                    {N,N,N,M,M,M,M,M,M,M,M,M,M,N,N,N},
                    {N,N,N,M,M,M,M,M,M,M,M,M,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // SIX
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,C,C,C,C,C,C,C,C,N,N,N,N},
                    {N,N,N,C,C,C,C,C,C,C,C,C,N,N,N,N},
                    {N,N,N,C,C,C,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,C,C,C,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,C,C,C,C,C,C,C,C,C,N,N,N,N},
                    {N,N,N,C,C,C,C,C,C,C,C,C,C,N,N,N},
                    {N,N,N,C,C,C,N,N,N,N,C,C,C,N,N,N},
                    {N,N,N,C,C,C,N,N,N,N,C,C,C,N,N,N},
                    {N,N,N,C,C,C,C,C,C,C,C,C,C,N,N,N},
                    {N,N,N,N,C,C,C,C,C,C,C,C,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // SEVEN
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,K,K,K,K,K,K,K,K,K,K,N,N,N},
                    {N,N,N,K,K,K,K,K,K,K,K,K,K,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,K,K,K,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,K,K,K,N,N,N},
                    {N,N,N,N,N,N,N,N,N,K,K,K,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,K,K,K,N,N,N,N},
                    {N,N,N,N,N,N,N,N,K,K,K,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,K,K,K,N,N,N,N,N},
                    {N,N,N,N,N,N,N,K,K,K,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,K,K,K,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

            { // EIGHT
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,S,S,S,S,S,S,S,S,N,N,N,N},
                    {N,N,N,S,S,S,S,S,S,S,S,S,S,N,N,N},
                    {N,N,N,S,S,S,N,N,N,N,S,S,S,N,N,N},
                    {N,N,N,S,S,S,N,N,N,N,S,S,S,N,N,N},
                    {N,N,N,N,S,S,S,S,S,S,S,S,N,N,N,N},
                    {N,N,N,N,S,S,S,S,S,S,S,S,N,N,N,N},
                    {N,N,N,S,S,S,N,N,N,N,S,S,S,N,N,N},
                    {N,N,N,S,S,S,N,N,N,N,S,S,S,N,N,N},
                    {N,N,N,S,S,S,S,S,S,S,S,S,S,N,N,N},
                    {N,N,N,N,S,S,S,S,S,S,S,S,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
                    {N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N},
            },

    };

}
