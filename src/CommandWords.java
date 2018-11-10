/**
* @Author: Joakim Olsson <lomo133>
* @Date:   2018-11-07T17:33:34+01:00
 * @Last modified by:   lomo133
 * @Last modified time: 2018-11-07T17:43:13+01:00
*/


/**
* This class is part of the "World of Zuul" application.
* "World of Zuul" is a very simple, text based adventure game.
*
* This class holds an enumeration of all command words known to the game.
* It is used to recognise commands as they are typed in.
*
*/

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "cry", "inventory", "pickup"
    };

    /**
    * Constructor - initialise the command words.
    */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
    * Print all valid commands to user.
    */
    public String getCommandList() {
        String commands = "";
        for(String command : validCommands) {
            commands += command + " ";
        }
        return commands;
    }

    /**
    * Check whether a given String is a valid command word.
    * @return true if a given string is a valid command,
    * false if it isn't.
    */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
            return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
}
