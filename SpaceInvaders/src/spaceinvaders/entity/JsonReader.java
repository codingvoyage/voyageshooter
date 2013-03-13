package spaceinvaders.entity;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads Json Data Files with the GSON Library
 * @author Brian Yang
 */
public class JsonReader {
    
    /**
     * JSON filepath
     */
    private String file;
    
    /**
     * Parsed group of entities
     */
    private Entities data;
    
    /**
     * Construct a new JsonReader for Entities
     * @param file path of JSON file
     */
    public JsonReader(String file) {
        this.file = file;
    }
    
    /**
     * Read and parse the JSON file
     * @return boolean indicating whether or not the JSON file was successfully parsed
     */
    public boolean readJson(){
        try {
           
            // read the JSON file
            BufferedReader read = new BufferedReader(new FileReader(file));

            // pass the file to Gson and create a group of Entities from that
            data = new Gson().fromJson(read, Entities.class);
            read.close();
            
        } catch(IOException e) {
            
            System.out.println("JSON file does not exist!");
            return false;
            
        } 
        
        System.out.println("Successfully read JSON file!");
        return true;
    }
    
    /**
     * Accessors for Entities group
     * @return the parsed Entities
     */
    public Entities getEntities() {
        return data;
    }
}
