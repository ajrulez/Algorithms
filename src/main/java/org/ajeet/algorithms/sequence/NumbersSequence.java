package org.ajeet.algorithms.sequence;

/**
 * Solution for : http://www.careercup.com/question?id=5564407157358592
 *
 */
public final class NumbersSequence {
		
	private static int numDigits(String seq){
		int digits = 1;
		int position =0;
		int requiredSuccessAttampts = seq.length() - 2;
		int success = 0;
		boolean hasTwoTypesNumbers = false;
		for(int i=0; i < seq.length(); i++){			
			int diff = 1; 		// Because the numbers are in sequence order so diff should be 1.
			int failures = 0;   // If failures are three than move to next iteration
			    // We need at least two success 	
			boolean oldsuccess = false;
			if(digits != 1){
				String sey1 = seq.substring(position, seq.length());
				requiredSuccessAttampts = ((sey1.length())/(digits)) + success - 1;
			}
			for(int j=position; j<=seq.length(); j = j+digits){	
				if(success >= requiredSuccessAttampts){
					System.out.println("This sequence has " + digits + " digit numbers.");
					if(hasTwoTypesNumbers){
						return --digits;
					}
					return digits;
				}
				//If only two numbers are there.
				if(seq.length() == digits *2 ){
					if(hasTwoTypesNumbers){
						return --digits;
					}
					return digits;
				}
				String num1Str = seq.substring(j, j+digits);
				int num1  = Integer.parseInt(num1Str);
				int num2  = Integer.parseInt(seq.substring(j+digits, j+digits+digits));
				int tmpDiff = num2 - num1;
				//If these are in sequence or a missing number between these two numbers.
				if((tmpDiff == diff) || ((num2 == (num1 + 2)) && !(failures > 0))){
					++success;
					if(success >= requiredSuccessAttampts){
						System.out.println("This sequence has " + digits + " digit numbers.");
						if(hasTwoTypesNumbers){
							return --digits;
						}
						return digits;
					}						
				} else if((oldsuccess & (num1 != num2) && !(failures > 0)) && (seq.length() >= (j+digits+digits+1))){
					//boolean isLastDigit9 = false;
					String nextNum = num1+1 + "";
					if((num1Str.lastIndexOf("9") == num1Str.length()-1)  || (nextNum.lastIndexOf("9") == nextNum.length()-1)){
						int num3  = Integer.parseInt(seq.substring(j+digits, j+digits+digits+1));
						tmpDiff =  num3 - num1;
						if(tmpDiff == diff || tmpDiff == diff+1){
							position = j+digits;
							++digits;
							++success;
							if(success >= requiredSuccessAttampts){
								return --digits;
							}
							hasTwoTypesNumbers = true;
							break;
						}
					}

				} else {
				    	   	++failures;
				    	   	if((failures == requiredSuccessAttampts) || (failures == 2)){
				    	   		System.out.println("This sequence does not contains " +  digits + " digit numbers so trying " + (digits + 1) + " digit numbers");
				    	   		++digits;
				    	   		success = 0;
				    	   		hasTwoTypesNumbers = false;
				    	   		break;
				    	   	}
				       }
				if(tmpDiff < 0){
	    	   		++digits;
	    	   		break;
				}
				if(tmpDiff == 1){
					oldsuccess = true;
				}
				
   		 }
			
		}
		return 0;
	}
	
	private static int getMissingNumber(String seq, int numdigits){
		return getMissingNumber(seq,0, numdigits);
		
	}
	
	
	private static int getMissingNumber(String seq, int position, int digits){
		if(position+digits+digits >= seq.length()+1){
			return 0;
		}
		String num1Str = seq.substring(position, position+digits);
		int num1  = Integer.parseInt(num1Str);
		int num2  = Integer.parseInt(seq.substring(position+digits, position+digits+digits));
		int tmpDiff = num2 - num1;
		if(num1Str.lastIndexOf("9") == num1Str.length()-1){
			int num3  = Integer.parseInt(seq.substring(position+digits, position+digits+digits+1));
			tmpDiff =  num3 - num1;
			if(tmpDiff == 2){
				return num1 +1;
			}
			position+=digits;
			++digits;
			return getMissingNumber(seq, position, digits);
		}
		if (tmpDiff !=0 && tmpDiff != 1){
			return (num1 + 1);
		}

		return getMissingNumber(seq, position+digits, digits);
		
	}
	
	public static int missingNumber(String seq){
		int digits = numDigits(seq);
		return getMissingNumber(seq, digits);

	}
	
	//Test client
	public static void main(String[] args) {
		String seq = "9899100101103104105";
		int digits = numDigits(seq);
		//System.out.println(digits);
		System.out.println("This is the missing number: " + getMissingNumber(seq, digits));
	}

}
