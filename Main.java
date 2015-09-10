import io.*;
import java.io.*;
import java.util.*;
public class Main
{
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
/*******************************************
				Class Fields
********************************************/
	private DSAStack<TextFile> textfileStack;	// Stack of each 'page' you have successfully loaded: used to cycle back when things fail to load
	private DSAStack<Integer> choiceStack;		// Stack of each choice you have made: used to delete faulty choices
	private String directory;
	private TextFile currentTextFile;			// Pointer to the current textFile, this is kinda handy to have as a field because it's used a lot




/*******************************************
			Constructor method
IMPORTS: NONE
EXPORTS: NONE
DESC: Sets class field to null/empty values
********************************************/
	public Main()
	{
		textfileStack = new DSAStack<TextFile>();
		choiceStack = new DSAStack<Integer>();
		String directory = new String("");
		currentTextFile = null;
	}



/*******************************************
			runProgram method
IMPORTS: NONE
EXPORTS: NONE
DESC: Loads in the START text file then
starts the loop, accounting for errors along
the way! Once finished it calls writeLog to 
write the log.
********************************************/
	public void runProgram()
	{
		boolean badDir; //if this is true the directory is dodgey and not able to be run through. Use to restart runProgram so you pick a new Directory if the one you already picked is stuffed
		badDir = true;

		while (badDir)
		{
			try
			{
				String filename;
				boolean badStart = true;	//if this is true the START file in the directory didn't start properly so you need to redo the Directory input, kind of like badDir but only used in the initial loop
				filename = "START";			//the first filename is always START
				while(badStart)
				{
					directory = ConsoleInput.readLine("\nInput directory:"); //Inputting Directory
					try
					{
						currentTextFile = new TextFile(filename, directory);//Loads the .txt and _opt.csv files into a TextFile object
						textfileStack.push(currentTextFile);				//since it succeeded we put the textfile in the Stack for when we write to file
						badStart = false;									//exits the badStart loop because the start was good!
					}
					catch (IllegalArgumentException e)
					//triggered when the files don't load into the TextFile object for some reason
					{
						System.out.println("\nTry Again"); //will be accompanied by the IO error message that has been displayed in the TextFile bit.
					}
				}
				loop();			//excecutes the loop
				badDir = false;	//exits the badDir loop because if loop() managed to finish it means we managed to find an exit
			}
			catch (IllegalArgumentException e)
			//triggered if loop figures out the directory is cactus
			{
				System.out.println(e.getMessage());
				badDir = true;
			}
		}
		writeLog();	//writes the text files/choices to a file (or quits)
	}




/*******************************************
				loop method
IMPORTS: NONE
EXPORTS: NONE
DESC: the main loop for navigating the files,
it will print out options for the current
textfile, get you to input your choice, then
attempt to load the file related to that 
choice. It also does a lot of error checking
which is explained inside 
********************************************/
	private void loop()
	{
		/*DECLARATIONS*/
		boolean finish = false;	//this controls the loop, when the user selects an EXIT choice, it will put this it true and exit the loop (and subsequently the method)
		int choiceNo;			//variable for holding the current chocie
		String filename;		//this holds the current filename of the current 'page' in the story
		/*------------*/

		while (!finish)
		{
			try
			{
				System.out.println("\n" + currentTextFile.toString());	//outputs the contents of the current TextFile object (the story and the options)
				choiceNo = ConsoleInput.readInt("\nInput Choice ");		//prompts user to input a choice
				filename = currentTextFile.getChoice(choiceNo).getFilename();	//fetches the filename of the choice the user inputted.
				if (!filename.equals("EXIT"))	 
				//block executes if it isn't the end of the story
				{
					try
					{
						currentTextFile = new TextFile(filename, directory);	//creates a new text file from the new filename
						textfileStack.push(currentTextFile);					//adds the text file to the stack of text files visited
						choiceStack.push(choiceNo);								//adds the choice selected to get to the current text file
						if (currentTextFile.getNumEntries() == 1)
						/*this block checks to see that you aren't stuck in an infinite loop due to choices being removed because they're missing/corrupt.
						  it only figures out infinite loops that force you to pick one choice (see README for more info)*/
						{
							Iterator<TextFile> iterStack = textfileStack.iterator();//iterator declaration to search the stack to see if you've been at this page before
							iterStack.next();							//skip the first text file because that is the current one
							TextFile tempFile = iterStack.next();		//tempFile is a temp text file pointer for comparing
							if ((iterStack.hasNext()))					//doesn't bother if you are only one choice deep because there is nothing to compare.
							{
								boolean exit = false;	// is true when you get back to the start or when a textfile on the stack has more than one choice			
								while ((!exit))
								/*This loop cycles through your textfileStack and stops when it hits either a page identical to yours, the start page or a page with more than one
								  choice. If it hits the identical page first, it throws an exception because that means you're in an infinite loop*/
								{
									if (tempFile.getFilename().equals(filename))
									 	throw new IllegalArgumentException("\nCorrupt files (or your BOFH) have created an infinite loop, rolling back to before the loop.");
									else
									 	tempFile = iterStack.next();
									if (!(tempFile.getNumEntries() == 1))
										exit = true;
									else if (tempFile.getFilename().equals("START"))
										exit = true;
								}
							}
						}
					}
					catch (IllegalArgumentException e)
					/*Exception is caught here if there was a problem reading the file linked to the choice you made, or if you're stuck in a (simple) infinite
					loop. (README has definition of 'simple')*/
					{
						System.out.println(e.getMessage() + "\nTry Again"); //outputs error message and Try Again
						currentTextFile.removeChoice(choiceNo);	//since the last choice didn't work out, we remove it from that textfile
						while (currentTextFile.getNumEntries() == 0)
						//This block executes when there are no choices left that aren't broken. It keeps executing until it finds a page that has choices
						{
							System.out.println("\nAll choices are invalid, going back to previous page");
							textfileStack.pop();	//since the textfile is a dead end, we pop it off the stack
							if (!textfileStack.isEmpty())	
							/*	if it is empty, there are no choices that lead anywhere in any of the pages seeding from START.txt
								if it isn't then we 'roll back' to the previous text file on the stack, and remove the last choice made (because it was a dead end)*/
							{
								currentTextFile = textfileStack.top();
								currentTextFile.removeChoice(choiceStack.pop());
							}
							else throw new IllegalArgumentException("\nAll possible paths are filled with errors! Please select a new directory for a story that WORKS!");
						}
					}
				}
				else 
				//this block is only executed if the choice selected was an EXIT choice.
				{
					finish = true;
					choiceStack.push(choiceNo);
				}
			}
			catch (ArrayIndexOutOfBoundsException e)
			//this block is executed if you input a choice higher than the amount of choices
			{
				System.out.println("\nWhat you entered was not an option.\nTry Again.");
			}
		}
	}




/*******************************************
			writeLog method
IMPORTS: NONE
EXPORTS: NONE
DESC: This method executes after your story
successfully exits. It's purpose is to ask
if you want to save your story path or quit
and then do those things.
********************************************/
	private void writeLog()
	{
		/*DECLARATIONS*/	
		String filename;													//this holds the current filename of the file we are writing to
		boolean finish = false;												//this value is true when the writing is successful or the user chooses to quit
		DSAStack<TextFile> writeTextfileStack = new DSAStack<TextFile>();	//these will be the current stacks but back-to-front 
		DSAStack<Integer> writeChoiceStack = new DSAStack<Integer>();		//same thing
		/*------------*/

		while (!(textfileStack.isEmpty())) 				
		//loop reverses the stacks for writing
		{
			writeTextfileStack.push(textfileStack.pop());
			writeChoiceStack.push(choiceStack.pop());
		}
				choiceStack = null;			//not needed anymore, putting them out for the garbage man
				textfileStack = null;

		System.out.println(	"\nWell that was fun (wasn't it!?)\nWould you like to save the story path of your choices? I bet you would!" + 
							"\nIf you do, then type the filename of where you want to save it, otherwise, type Quit!");
		while (!finish)
		{
			filename = ConsoleInput.readLine("\nInput: ");
			if (!(filename.toLowerCase().equals("quit")))
			{
				FileWriter writer = null;	//this is the FileWriter, set to null to stop compilation errors
				try
				{
					writer = new FileWriter(filename + ".txt", false);	//opens new stream for writing, auto-appends txt to filename
					while (!writeTextfileStack.isEmpty())
					//this loops until there is nothing left of the text file stack
					{
						writer.write(new String(writeTextfileStack.top().getContents() + "\n" + 
												writeTextfileStack.top().getChoice(writeChoiceStack.pop()).getDesc()) + "\n"); //writes the text file and choice you picked to the file
												
						writeTextfileStack.pop();									//pops off the file because we don't need it anymore
					}
					writer.close();													//closes the stream
					finish = true;													//write success so the loop exits
					System.out.println("\nSaved to file " + filename + ".txt\n");	//success message
				}
				catch (IOException e)
				/*This block executes if something goes wrong file writing to the file. It closes the stream (if not already closed) 
				and sets finished to false so the loop runs agian*/
				{			
					if (writer != null) 
					{
						try 
						{
							writer.close();
						} 
						catch (IOException ex2) {}
					}
					System.out.println("\nError writing to file " + filename + ": " + e.getMessage() + " Lets try again shall we? Otherwise, type 'quit'"); //error message
					finish = false;		//so that the loop restarts
				}
			}
			else finish = true; 		//this line happens when you type quit (any case format)
		}
		System.out.println("\nSEEYA!"); //goodbye message
	}
}
