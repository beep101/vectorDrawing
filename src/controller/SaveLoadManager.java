package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Model;

public class SaveLoadManager{
	private Model loadedModel;
	private List<String> loadedLog;
	
	public SaveLoadManager() {
		loadedModel=null;
		loadedLog=null;
	}
	
	public void modelDone() {
		loadedModel=null;
	}
	
	public void logDone() {
		loadedLog=null;
	}
	
	public boolean saveModel(String filename, Model model) {
		FileOutputStream file=null;
	    ObjectOutputStream out=null;
		try {
			file = new FileOutputStream(filename);
			out = new ObjectOutputStream(file);
			
			out.writeObject(model);
			
			out.close();
			file.close();
		}catch (FileNotFoundException e) {
			return false;
		}catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean loadModel(String filename) {
		try
        {    
            FileInputStream file = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(file); 

            loadedModel=(Model)in.readObject();

            in.close(); 
            file.close(); 
        } 
          
        catch(IOException ex) {
        	modelDone();
        	return false;
        } 
        catch(ClassNotFoundException ex) {
        	modelDone();
        	return false;
        }
		return true;
	}
	
	public boolean saveLog(String filename,String log) {
		BufferedWriter file=null;
		try {
			file = new BufferedWriter(new FileWriter(filename));
			
			file.write(log);
			
			file.close();
		}catch (FileNotFoundException e) {
			return false;
		}catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean loadLog(String filename) {
		try
        {    
			BufferedReader file = new BufferedReader (new FileReader(filename)); 

            loadedLog=new ArrayList<String>();
            String temp=null;
            while((temp=file.readLine())!=null)
            	loadedLog.add(temp);
            
            file.close(); 
        }catch (FileNotFoundException e) {
        	logDone();
			return false; 
        }catch(IOException ex) {
        	modelDone();
        	return false;
        } 
		return true;
	}
	
	public Model getLoadedModel() {
		return loadedModel;
	}

	public List<String> getLoadedLog() {
		return loadedLog;
	}

}
