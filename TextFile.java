import java.io.*;
import java.util.*;
/*  
    Copyright (C) 2012 Jason Giancono (jasongiancono@gmail.com)

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
*/
public class TextFile
{
/*******************************************
				Class Fields
********************************************/
	private String fileContents;	//string with the contents of the .txt file inside
	private Choice[] choices;		//array of all the choices you can make with this text file
	private int numEntries;		//count of the number of choices in the Choice array
	private String filename; 		//stores filename of the textfile




/*******************************************
			Constructor method
IMPORTS: 	-inFilename (string)
			-directory
EXPORTS: NONE
DESC: Sets the filename to inFilename and 
then executes the private readTXT and readCSV
methods 
********************************************/
	public TextFile(String inFilename, String directory)
	{
		filename = inFilename;
		readTXT(directory);
		readCSV(directory);
	}



/*******************************************
		removeChoice method (Mutator)
IMPORTS: n (int)
EXPORTS: NONE
DESC: Removes the 'n'th choice and shuffles 
the rest of them down.
********************************************/	
	public void removeChoice(int n)
	{
		choices[n-1] = null;			//make the element null
		for (int ii = n; ii < numEntries; ii++)
		//shuffles the rest of the elements down one
		{
			choices[ii-1] = choices[ii];
		}
		choices[numEntries-1] = null; 	//sets the last element to null
		numEntries--;					//decreases the number of element count
	}




/*******************************************
		toString method (Accessor)
IMPORTS: NONE
EXPORTS: tempstr (String)
DESC: Constructs a string consisting of the
contents of the txt file and all the choices
which are numbered and returns it 
********************************************/
	public String toString()
	{
		String tempStr;
		tempStr = new String("\n" + fileContents + "\n\n---------------");
		for(int ii = 0; ii < numEntries; ii++)
		//outputs each choice
		{
			tempStr = tempStr + "\n" + (ii+1) + ") " + choices[ii].getDesc();
		}
		return tempStr;
	}



/*******************************************
		getChoice method (Accessor)
IMPORTS: n (int)
EXPORTS: NONE
DESC: Returns n choice from the choices array.
********************************************/	
	public Choice getChoice(int n)
	{
		if (n > numEntries)	//checks to see if import is lower than number of choices (can't trust choice.length because elements might be null)
			throw new ArrayIndexOutOfBoundsException("");
		return choices[n-1];
	}



/*******************************************
		getNumEntries method (Accessor)
IMPORTS: NONE
EXPORTS: numEntries (int)
DESC: Returns number of choices field.
********************************************/	
	public int getNumEntries()
	{
		return numEntries;
	}



/*******************************************
		getContents method (Accessor)
IMPORTS: NONE
EXPORTS: fileContents (String)
DESC: Returns the file contents field.
********************************************/	
	public String getContents()
	{
		return new String(fileContents);
	}



/*******************************************
		getFilename method (Accessor)
IMPORTS: NONE
EXPORTS: filename (String)
DESC: Returns the filename field.
********************************************/	
	public String getFilename()
	{
		return filename;
	}



/*******************************************
		getFilename method (Accessor)
IMPORTS: inFile (TextFile)
EXPORTS: equals
DESC: Returns whether or inFile's filename
and this objects filename are the same.
********************************************/		
	public boolean equals(TextFile inFile)
	{
		return (filename.equals(inFile.getFilename()));
	}



/*******************************************
		readTXT method (helper method)
IMPORTS: directory (String)
EXPORTS: NONE
DESC: Reads the text file and puts it's
contents into the fileContents field
********************************************/			
	private void readTXT(String directory)
	{
		/*DECLARATIONS*/
		BufferedReader buffer;
		FileReader reader = null;
		String line;			// a temp variable to load lines into
		/*************/

		try	
		{
			boolean firstLine = true;				
			fileContents = new String("");							
			reader = new FileReader(System.getProperty("user.dir") + "/" + directory + "/" +  filename + ".txt");				
			buffer = new BufferedReader(reader);
			line = buffer.readLine();							
			while (line != null)
			//will loop through this block until you reach the end of the file
			{

				if (firstLine != true)
				{
					fileContents = fileContents + "\n" + line;		//if not the first line tack a newline character onto end of current string
				}
				else
				{
					fileContents = fileContents + line;				//if it is the first line then don't
					firstLine = false;
				}
				line = buffer.readLine();		//get the next line
			}
			buffer.close();		//close the reader
		}
		catch (IOException e)
		//This block executes when the file fails to be read
		{
			if (reader != null) 
			//try to close the file if it isn't already closed
			{
				try 
				{
					reader.close(); 
				} 
				catch (IOException ex2) {}
			}
			System.out.println("\nError fetching file " + filename + ".txt: " + e.getMessage());	//error message
			throw new IllegalArgumentException("");	//throw an exception for Main to catch
		}
	}



/*******************************************
		readCSV method (helper method)
IMPORTS: directory (String)
EXPORTS: NONE
DESC: Reads the csv and puts it's
contents into the choices array field
********************************************/			
	private void readCSV(String directory)
	{
		/*****DECLARATIONS******/
		BufferedReader buffer;
		FileReader reader = null;
		String line;
		/***********************/

		try
		{
			String fname = new String(""); 	//temp variable for the filename field in Choice class
			String desc = new String("");	//temp variable for the desc field in Choice class
			numEntries = 0;					//numEntries starts at 0 because there are no choices yet
			choices = new Choice[5];		//initialising the choices array
			reader = new FileReader(System.getProperty("user.dir") + "/" + directory + "/" +  filename + "_opt.csv");	//opens file from filename field
			buffer = new BufferedReader(reader);
			line = buffer.readLine();	//gets the first line
			while(line != null)
			//loops while the fetched line
			{
				try
				{
					if (numEntries < 5)	//limit of five choices
					{
						StringTokenizer strTok = new StringTokenizer(line, ",", false);	//declares a new tokenizer
						desc = strTok.nextToken();			//gets the first column of csv
						fname = strTok.nextToken();			//gets the second column of csv (ignores any more columns)
						choices[numEntries] = new Choice(fname, desc);	//constructs a new choice file for the line
						numEntries++;				//adds a successful entry to the count
						line = buffer.readLine();	//gets the next line
					}
					else
					{
						System.out.println(	"\nfile: " + filename + "_opt.csv" + " has too many options - the rest will be ignored");
						line = null;
					}
				}
				catch (NoSuchElementException e)
				//this block executes if the line has no commas in it, StringTokenizer will throw this exception
				{
					System.out.println("\nChoice on line " + (numEntries+1) + " of file: " + filename + "_opt.csv" + " is not in the correct format, it will be omitted"); //error message
					line = buffer.readLine();	//get the next line (ignoring this one)
				}
			}
			if (numEntries == 0)
			//block executes if file is empty or all entries aren't in correct format
				throw new IOException("No entries in file (or all entries are in an incorrect format)");
			buffer.close();	
		}
		catch (IOException e)
		//only executes if error reading file/file is not right
		{
			if (reader != null) 
			//close the reader if it's not closed
			{
				try 
				{
					reader.close(); 
				} 
				catch (IOException ex2) {}
			}
			System.out.println("\nError fetching file " + filename + "_opt.csv: " + e.getMessage()); // error message
			throw new IllegalArgumentException(""); //throw exception for Main
		}
	}
}
