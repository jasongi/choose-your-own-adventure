public class Choice
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
	private String desc;		//description of the choice
	private String filename;	//filename of the file the choice leads to



/*******************************************
			Alt Constructor method
IMPORTS: 	-inFilename (String)
			-inDesc (String)
EXPORTS: NONE
DESC: Sets class fields to import values.
********************************************/
	public Choice(String inFilename, String inDesc)
	{
		filename = new String(inFilename);
		desc = new String(inDesc);
	}



/*******************************************
			Copy Constructor method
IMPORTS: 	-inFilename (String)
			-inDesc (String)
EXPORTS: NONE
DESC: Sets class fields values of imported
Choice object.
********************************************/
	public Choice(Choice inChoice)
	{
		desc = inChoice.getDesc();
		filename = inChoice.getFilename();
	}



/*******************************************
		getFilename method (Accessor)
IMPORTS: NONE
EXPORTS: filename (String)
DESC: returns filename String (after copying
it) 
********************************************/
	public String getFilename()
	{
		return new String(filename);
	}



/*******************************************
		getDesc method (Accessor)
IMPORTS: NONE
EXPORTS: desc (String)
DESC: returns desc String (after copying it) 
********************************************/
	public String getDesc()
	{
		return new String(desc);
	}
}
