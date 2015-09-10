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
public class DSAStack<E> implements Iterable<E>
{
	private DSALinkedList<E> stack;

	public DSAStack() //default constructor
	{
		stack = new DSALinkedList<E>(); 
	}

	public boolean isEmpty() //checks stack for emptyness
	{
		return (stack.isEmpty());
	}

	public void push(E value) //puts value on the stack
	{
		stack.insertFirst(value);
	}

	public E pop()
	{
		E value = stack.removeFirst();
		return (value); //returns value ontop of the stack
	}

	public E top()
	{
		return (stack.peekFirst()); //returns the last value added to the stack
	}

	public Iterator<E> iterator() 
	{
		return stack.iterator(); // Expose list’s iterator
	}
}
