DSA120 ASSIGNMENT ONE
BY JASON GIANCONO
16065985

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

To run the program, type ~$java Start from the terminal.


Cool things it does/deals with:
- removes elements from choices when they turn out to be duds (or all their children turn out the duds, etc etc)

- When a 'page' in the story runs out of choices, it falls back to the previous page (pages kept in a stack adt) and then deletes the choice that was a dud (choice numbers kept in a stack adt)

- If there is only one choice for a 'page', it looks back through you path history to see if you've been on that page before and if every page between the to two visits only has one choice. If this is true, then you're stuck in an infinte loop and it will roll you back to before the infinite loop by popping textfiles off the stack. Good for when one of the options is corrupt and the other sends you into infinite loop*. Only good for 'simple' infinite loops (ie ones that only have one option for text file during one circuit of the loop.

- If it 'rolls back' to the beginning and the START page has no more choices, it asks for a new directory because the current one had no way to get to the exit.

- if it 'rolls back' due to corrupt pages, the pages it rolls back won't be written to the file.



Known Defects:
- This will keep using up more and more memory if you decide to keep looping through your story when you have a choice not to because it keeps adding to your story stack, could cause a crash if you do it like a million times.

- Uses a bit more memory than the simplest attempt at this program, but only because it deals with so many possible errors.

- is pretty big, but only because it deals with so many possible errors.
 
* - more complicated infinite loops don't get escaped from. Eg if you get two or more options leading to 2 or more other infinite loops it probably won't detect this. Would be hard and time consuming to fix this, and only a BOFH would make a story that sent you to multiple loops that don't have exits, so I'm leaving it out.
