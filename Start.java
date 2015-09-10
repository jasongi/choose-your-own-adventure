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
public class Start
{
	/*Start is just a class that jump starts the program and has the only static class in the program*/
	public static void main(String [] args)
	{
		try
		{
			Main main;
			main = new Main();
			main.runProgram();
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		System.exit(0);
	}
}
