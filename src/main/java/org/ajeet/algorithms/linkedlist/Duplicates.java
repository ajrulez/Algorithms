package org.ajeet.algorithms.linkedlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Delete the repeated elements in a singly linked list in O(n) time complexity without using extra space. Linked list contains elements in unsorted order 
 * Given input is:
 *   A->C->B->C->A->E->D->L>M->K
 *   Start = A;
 *
 *
 */
public final class Duplicates {
	
	public static List<Integer> findDuplicatesArray(int[] sequence){
		List<Integer> result = new ArrayList<Integer>();
		int bitarray = 0;
		
		for(int i=0; i< sequence.length; i++){
			int x = 1;
			x = x << sequence[i];
			
			if((bitarray & x) != 0){
				System.out.println("Duplicate found in given array: " + sequence[i]);
			} else {
				bitarray = bitarray | x;
			}
		}
		return null;
	}
	
	public static List<Integer> findDuplicatesLinkedList(LinkedList<Integer> sequence){
		List<Integer> result = new ArrayList<Integer>();
		int bitarray = 0;
		Iterator<Integer> iterator = sequence.iterator();
		
		while(iterator.hasNext()){
			Integer num = iterator.next();
			int x = 1;
			x = x << num;
			
			if((bitarray & x) != 0){
				System.out.println("Duplicate found in given linked list: " + num);
				iterator.remove();
			} else {
				bitarray = bitarray | x;
			}
		}
		System.out.println(sequence);
		return null;
	}
	
	//Test Client
	public static void main(String[] args) {
		//int[] input = {4,2,3,3,1,5,6,9,4};
		//findDuplicatesArray(input);
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(4);
		list.add(2);
		list.add(3);
		list.add(3);
		list.add(1);
		list.add(5);
		list.add(6);
		list.add(9);
		list.add(4);
		findDuplicatesLinkedList(list);
	}

}
