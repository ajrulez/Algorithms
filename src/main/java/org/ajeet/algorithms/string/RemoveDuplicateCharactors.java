package org.ajeet.algorithms.string;


public class RemoveDuplicateCharactors {
	
	 public static int remove(char[] input) {
	        int tmp = 0;
	        int seek = 0;
	        for (int i = 0; i < input.length; i++) {
	        	int x = 1 << (input[i] - 'a');
	            if ((tmp & (x)) > 0) {
	            	System.out.println("Duplicate found - " + input[i]);
	            } else {
	                tmp |= x;
	                input[seek] = input[i];
	                if(i < input.length - 1)
	                	seek++;
	            }
	            
	        }
	        input[seek+1] = '\0';
	        return seek;
	    }

	 //Test client
	 public static void main(String[] args) {
		char[] input = {'a', 'j','e','e','t'};
		remove(input);
		System.out.println(input);
	}
}
