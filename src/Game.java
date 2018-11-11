/**
* @Author: Joakim Olsson <lomo133>
* @Date:   2018-11-05T18:05:37+01:00
 * @Last modified by:   lomo133
 * @Last modified time: 2018-11-11T01:03:03+01:00
*/

import java.util.List;
import java.util.ArrayList;


/**
*  This class is the main class of the "World of Zuul" application.
*  "World of Zuul" is a very simple, text based adventure game.  Users
*  can walk around some scenery. That's all. It should really be extended
*  to make it more interesting!
*
*  To play this game, create an instance of this class and call the "play"
*  method.
*
*  This main class creates and initialises all the others: it creates all
*  rooms, creates the parser and starts the game.  It also evaluates and
*  executes the commands that the parser returns.
*
*/

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private List<Item> inventory = new ArrayList<>();
    Room outside, hallway, garden, upstairs, livingRoom, cellar, leftDoor,
    rightDoor;

    /**
    * Create the game and initialise its internal map.
    */
    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    /**
    * Create all the rooms and link their exits together.
    * Create items in rooms.
    * Puts starting items in printInventory.
    */
    private void createRooms()
    {

        // create the rooms
        outside = new Room("somwhere outdoors");
        hallway = new Room("in a hallway");
        garden = new Room("in a garden");
        upstairs = new Room("upstairs");
        livingRoom = new Room("in a living room");
        cellar = new Room("in a cellar");
        leftDoor = new Room("in a weird looking room" + "\n" + "And you are trapped!");
        rightDoor = new Room("in a weird smelling room");


        // initialise room exits
        outside.setExit("north", hallway);
        outside.setExit("south", garden);
        hallway.setExit("outside", outside);
        hallway.setExit("up", upstairs);
        hallway.setExit("right", livingRoom);
        hallway.setExit("down", cellar);
        livingRoom.setExit("left", hallway);
        garden.setExit("north", outside);
        upstairs.setExit("down", hallway);
        upstairs.setExit("leftDoor", leftDoor);
        upstairs.setExit("rightDoor", rightDoor);
        cellar.setExit("up", hallway);

        // starting items
        inventory.add(new Item("knife", 0.5));

        // items in rooms
        cellar.setItem(new Item("woodenKey", 500));

        currentRoom = outside;  // start game outside
    }

    /**
    *  Main play routine.  Loops until end of play.
    */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (currentRoom == rightDoor) {
                System.out.println("You should feel like a winner, congratulations!");
                finished = true;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
    * Print out the opening message for the player.
    */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the world with a house!");
        System.out.println("World with a house is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getExitString());
    }

    /**
    * Given a command, process (that is: execute) the command.
    * @param command The command to be processed.
    * @return true If the command ends the game, false otherwise.
    */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("cry")) {
            cry();
        }
        else if (commandWord.equals("inventory")) {
            printInventory();
        }
        else if (commandWord.equals("pickup")) {
            getItem(command);
        }
        return wantToQuit;
    }

    /**
    * Print out the items in inventory.
    */
    private void printInventory() {
        String output = "";
        for (int i = 0; i < inventory.size(); i++) {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("You have: " + output);
    }

    /**
    * Print out some help information.
    * Here we print some stupid, cryptic message and a list of the
    * command words.
    */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around somewhere unknown.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
    * Gives a description of the room
    */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
    * print out the cry command.
    */
    private void cry() {
        System.out.println("You started to cry");
    }

    /**
    * Try to go in one direction. If there is an exit, enter
    * the new room, otherwise print an error message.
    * Checks if the user has the key to open the door,
    * otherwise the user can't enter.
    */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == rightDoor
        && inventory.contains(checkItem("woodenKey"))) {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        else if (nextRoom == rightDoor) {
            System.out.println("This door is locked");
        }

        else if(nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private Item checkItem(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getDescription().equals(itemName)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    /**
    * "pickup" was entered. Check the rest of the command to see
    * whether the user really wants to pickup the item.
    * Add the item to the inventory if the item is in the room
    * and removes it from the room.
    */
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("pickup what?");
            return;
        }

        String item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("That item is not here");
        }
        else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Picked up: " + item);
        }
    }

    /**
    * "Quit" was entered. Check the rest of the command to see
    * whether the user really quit the game.
    * @return true, if this command quits the game, false otherwise.
    */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
