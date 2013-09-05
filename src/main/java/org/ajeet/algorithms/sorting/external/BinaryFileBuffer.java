package org.ajeet.algorithms.sorting.external;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class BinaryFileBuffer  {
    public static int BUFFERSIZE = 2048;
    public BufferedReader fbr;
    public File originalfile;
    private String cache;
    private boolean empty;
     
    public BinaryFileBuffer(File f) throws IOException {
        originalfile = f;
        fbr = new BufferedReader(new FileReader(f), BUFFERSIZE);
        reload();
    }
     
    public boolean empty() {
        return empty;
    }
     
    private void reload() throws IOException {
        try {
          if((this.cache = fbr.readLine()) == null){
            empty = true;
            cache = null;
          } else{
            empty = false;
          }
      } catch(EOFException oef) {
        empty = true;
        cache = null;
      }
    }
     
    public void close() throws IOException {
        fbr.close();
    }
     
    //Read
    public String peek() {
        if(empty()) return null;
        return cache.toString();
    }
    
    //Read and remove current
    public String pop() throws IOException {
      String answer = peek(); // Read current line
      reload(); 			  // reload next line for next read operation
      return answer;
    }
    
    public void delete(){
    	originalfile.delete();
    }
}