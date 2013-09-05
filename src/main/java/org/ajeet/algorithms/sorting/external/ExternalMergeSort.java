package org.ajeet.algorithms.sorting.external;

import java.util.*;
import java.io.*;
 
 
public class ExternalMergeSort {
	//This should be configurable by client
    private static  final int MAXTEMPFILES = 1024;

    /**
     * Maximum size of a block that can be sorted in-memory. 
     * 
     * @param filetobesorted
     * @return block size
     */
    private static long calculateBlockSize(File filetobesorted) {
        long freemem = Runtime.getRuntime().freeMemory();
        return freemem/2;
    }
 
    /**
     * Here we are getting one piece\block from large file and sorting it and saving it to secondary storage.
     * We maintain only MAXTEMPFILES opened tmp files. If we will not restrict it, than it will cause system failure due to too many opened files.
     * 
     * @param file
     * @param cmp
     * @return
     * @throws IOException
     */
    private static List<File> splitAndSort(File file, Comparator<String> cmp) throws IOException {
        List<File> inpuFiles = new ArrayList<File>();
        List<File> outFiles = new ArrayList<File>();
        BufferedReader fbr = new BufferedReader(new FileReader(file));
        long blocksize = calculateBlockSize(file);
        try{
            List<String> elements =  new ArrayList<String>();
            String line = "";
            try {
                while(line != null) {
                    long currentblocksize = 0;
                    while((currentblocksize < blocksize) && ((line = fbr.readLine()) != null) ){ 
                        if(line != null && !line.equals("")){
                        	elements.add(line);
                        }
                        currentblocksize += line.length(); 
                    }                    
                    if(inpuFiles.size() >= MAXTEMPFILES - 1){
                    	 if(outFiles.size() >= MAXTEMPFILES - 1){
                    		 outFiles = merge(outFiles, null, cmp);                    		 
                    	 }
                    	outFiles.addAll(merge(inpuFiles, sortAndSave(elements,cmp), cmp));
                    	inpuFiles.clear();
                    } else {
                        inpuFiles.add(sortAndSave(elements,cmp));
                    }
                    elements.clear();
                }
            } catch(EOFException oef) {
                if(elements.size()>0) {
                	inpuFiles.add(sortAndSave(elements,cmp));
                	elements.clear();
                }
            }
        } catch(Exception ex){
        	ex.printStackTrace();
        }finally {
            fbr.close();
        }
        inpuFiles.addAll(outFiles);
        return inpuFiles;
    }
 
    private static List<File> merge(List<File> files, File file,Comparator<String> cmp) throws IOException{
    	if(file != null){
    		files.add(file);
    	}
    	if(files.size() >= MAXTEMPFILES){
    		List<File> result = new ArrayList<File>(1);
            File newtmpfile = File.createTempFile("mergeInBatch", "flatfile");
            newtmpfile.deleteOnExit();
            mergeKWay(files, newtmpfile, cmp);
    		files.clear();
    		result.add(newtmpfile);
    		return result;
     	} 
    	return files;
    }
 
    /**
     * Its a straigforward in-memory sequential sorting process, we can use any efficient & stable algorithm here.
     * So i am using java's in built implementation
     * 
     * @param tmplist
     * @param cmp
     * @return
     * @throws IOException
     */
    private static File sortAndSave(List<String> tmplist, Comparator<String> cmp) throws IOException  {
        Collections.sort(tmplist,cmp);  // 
        File newtmpfile = File.createTempFile("sortInBatch", "flatfile");
        newtmpfile.deleteOnExit();
        BufferedWriter fbw = new BufferedWriter(new FileWriter(newtmpfile));
        try {
            for(String r : tmplist) {
                fbw.write(r);
                fbw.newLine();
            }
        } finally {
            fbw.close();
        }
        return newtmpfile;
    }
 
    /**
     * Here we are merging a set of sorted files. Its a kind of K-way merge, and we are using a priority queue to merge these files.
     * 
     * @param files
     * @param outputfile
     * @param cmp
     * @return
     * @throws IOException
     */
    private static int mergeKWay(List<File> files, File outputfile, final Comparator<String> cmp) throws IOException {
        PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<BinaryFileBuffer>(11, 
            new Comparator<BinaryFileBuffer>() {
              public int compare(BinaryFileBuffer i, BinaryFileBuffer j) {
                return Integer.compare(Integer.parseInt(i.peek()), Integer.parseInt(j.peek()));
              }
            }
        );
        for (File f : files) {
            BinaryFileBuffer bfb = new BinaryFileBuffer(f);
            pq.add(bfb);
        }
        BufferedWriter fbw = new BufferedWriter(new FileWriter(outputfile));
        int rowcounter = 0;
        try {
            while(pq.size()>0) {
                BinaryFileBuffer bfb = pq.poll();
                String r = bfb.pop();
                fbw.write(r);
                fbw.newLine();
                ++rowcounter;
                if(bfb.empty()) {
                    bfb.fbr.close();
                    bfb.originalfile.delete();// we don't need you anymore
                } else {
                    pq.add(bfb); // add it back
                }
            }
        } finally { 
            fbw.close();
            for(BinaryFileBuffer bfb : pq ) {
            	bfb.close();
            	bfb.originalfile.delete();
            }
        }
        return rowcounter;
    }
 
    public static void sort(File input, File output, Comparator<String> comparator) throws IOException{
        List<File> list = splitAndSort(input, comparator) ;
        mergeKWay(list, output, comparator);
    }
    
    
    public static void main(String[] args) throws IOException {
        if(args.length<2) {
            System.out.println("please provide input and output file names");
            return;
        }
        String inputfile = args[0];
        String outputfile = args[1];
        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String r1, String r2){
                return Integer.compare(Integer.parseInt(r1), Integer.parseInt(r2));}};
        sort(new File(inputfile), new File(outputfile), comparator);
    }
} 
