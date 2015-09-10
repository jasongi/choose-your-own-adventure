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
public class DSALinkedList<E> implements Iterable<E>
{
	private DSAListNode<E> head;
	private DSAListNode<E> tail;

	public DSALinkedList() //makes empty list
	{
		head = null;
		tail = null;
	}

	public void insertFirst(E newValue) // inserts element into head position of list
	{
		DSAListNode<E> newNd = new DSAListNode<E>(newValue);
		if (head == null)
		{
			head = newNd;
			tail = newNd;
		}
		else
		{
			newNd.setNext(head);
			head = newNd;
		}
	}
	public void insertLast(E newValue) // inserts element into tail position of list
	{
		DSAListNode<E> newNd = new DSAListNode<E>(newValue);
		if (head == null)
		{
			head = newNd;
			tail = newNd;
		}
		else
		{
			DSAListNode<E> currNd = tail;
			tail = newNd;
			currNd.setNext(tail);
		}
	}

	public boolean isEmpty() //checks to see if list is empty
	{
		return (head ==null);
	}

	public E peekFirst()		//returns first value (if it exists)
	{
		if (head == null)
		{
			throw new IllegalArgumentException("list empty1");
		}
		else
		{
			return (head.getValue());
		}
	}

	public Iterator<E> iterator() //returns an iterator
	{
		return new DSALinkedListIterator<E>(this);
	}

	public E peekLast()				//returns tail element
	{
		if (head == null)
		{
			throw new IllegalArgumentException("list empty2");
		}
		else
		{
			return (tail.getValue());
		}
	}

	public E removeFirst()			//removes head element
	{
		E nodeValue;
		if (head == null)
		{
			throw new IllegalArgumentException("list empty3");
		}
		else
		{
			nodeValue = head.getValue();
			head = head.getNext();
		}
		return nodeValue;
	}

	public E removeLast()			//removes tail element
	{
		E nodeValue;
		if (head == null)
		{
			throw new IllegalArgumentException("list empty4");
		}
		else if (head == tail)
		{
			nodeValue = tail.getValue();
			head = null;
			tail = null;
		}
		else
		{
			nodeValue = tail.getValue();
			DSAListNode<E> prevNd = null;
			DSAListNode<E> currNd = head;
			while (currNd.getNext()!=null)
			{
				prevNd = currNd;
				currNd = currNd.getNext();
			}
			prevNd.setNext(null);
			tail = prevNd;
		}	
		return nodeValue;
	}

	private class DSAListNode<E>
	{
		private E value;
		private DSAListNode<E> next;

		public DSAListNode(E inValue)
		{
			value = inValue;
			next = null;
		}

		public E getValue()
		{
			return value;
		}

		public void setValue(E inValue)
		{
			value = inValue;
		}

		public DSAListNode<E> getNext()
		{
			return next;
		}

		public void setNext(DSAListNode<E> newNext)
		{
			next = newNext;
		}
	}
	
	private class DSALinkedListIterator<E> implements Iterator<E>
	{
		private DSALinkedList<E>.DSAListNode<E> iterNext;

		public DSALinkedListIterator(DSALinkedList<E> theList)
		{
			iterNext = theList.head;
		}

		public boolean hasNext()
		{
			return (!(iterNext == null));
		}

		public E next()
		{
			E value;
			if (iterNext == null)
			{
				value = null;
			}
			else
			{
				value = iterNext.getValue();
				iterNext = iterNext.getNext();
			}
		return value;
		}
	
		public void remove()
		{
			throw new UnsupportedOperationException("Not Supported");
		}
	}
}
