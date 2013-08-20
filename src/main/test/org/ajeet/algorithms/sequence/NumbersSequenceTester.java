package org.ajeet.algorithms.sequence;

import junit.framework.Assert;

import org.junit.Test;

public class NumbersSequenceTester {
	
	@Test
	public void test1(){
		String seq = "9899100101103104105";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(102, result);
	}
	
	@Test
	public void test2(){
		String seq = "1234567810";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(9, result);
		
	}
	
	@Test
	public void test3(){
		String seq = "12345678911";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(10, result);
		
	}
	
	@Test
	public void test4(){
		String seq = "909192939496979899";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(95, result);
		
	}
	
	@Test
	public void test5(){
		String seq = "100011000210004";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(10003, result);
		
	}

	@Test
	public void test6(){
		String seq = "121314121316";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(121315, result);
		
	}

	@Test
	public void test7(){
		String seq = "9899100102103";
		int result = NumbersSequence.missingNumber(seq);
		Assert.assertEquals(101, result);
		
	}

}
