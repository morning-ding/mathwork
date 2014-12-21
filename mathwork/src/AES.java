import java.util.Arrays;

import abstractmath.Field28;  
public class AES {

     /**
     * Turns array of bytes into string
     *
     * @param buf	Array of bytes to convert to hex string
     * @return	Generated hex string
     */
	 public static Field28 f = new Field28();
     public void AES(byte[][] input){//byte[4][4][8]
    	 final byte[][][] matrix = {{{0,0,0,0,0,0,1,0},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,1}},
    			 {{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,1,0},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,0,1}},
    			 {{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,1,0},{0,0,0,0,0,0,1,1}},
    			 {{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,1,0}}};
    	 byte[][] output = new byte[4][8];
    	 output[0] = f.add(f.mul(matrix[0][0], input[0]), f.mul(matrix[0][1], input[1]),
    			 f.mul(matrix[0][2], input[2]), f.mul(matrix[0][3], input[3]));
    	 output[1] = f.add(f.mul(matrix[1][0], input[0]), f.mul(matrix[1][1], input[1]),
    			 f.mul(matrix[1][2], input[2]), f.mul(matrix[1][3], input[3]));
    	 output[2] = f.add(f.mul(matrix[2][0], input[0]), f.mul(matrix[2][1], input[1]),
    			 f.mul(matrix[2][2], input[2]), f.mul(matrix[2][3], input[3]));
    	 output[3] = f.add(f.mul(matrix[3][0], input[0]), f.mul(matrix[3][1], input[1]),
    			 f.mul(matrix[3][2], input[2]), f.mul(matrix[3][3], input[3]));
    	 System.out.println(Arrays.deepToString(output));
    	 }
     
     public static void main(String[] args) {
    	 AES aes = new AES();
    	 byte[][] code = new byte[4][8];
    	 for(int i = 0; i < 4; i++){
    		 for(int j = 0; j < 8; j++)
    			 code[i][j] = (byte) (Math.random()*2);
    	 }
    	 aes.AES(code);
     }
   }