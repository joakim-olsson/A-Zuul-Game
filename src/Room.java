/**
* @Author: Joakim Olsson <lomo133>
* @Date:   2018-11-07T17:44:01+01:00
 * @Last modified by:   lomo133
 * @Last modified time: 2018-11-07T17:52:41+01:00
*/


/**
* Class Room - a room in an adventure game.
*
* This class is part of the "World of Zuul" application.
* "World of Zuul" is a very simple, text based adventure game.
*
* A "Room" represents one location in the scenery of the game.  It is
* connected to other rooms via exits.  The exits are labelled north,
* east, south, west.  For each direction, the room stores a reference
* to the neighboring room, or null if there is no exit in that direction.
*
*/

import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Room
{
    private String description;
    private Map<String, Room> exits;
    private List<Item> items;

    /**
    * Create a room described "description". Initially, it has
    * no exits. "description" is something like "a kitchen" or
    * "an open court yard".
    * @param description The room's description.
    */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();

    }


    /**
    * Define an exit from this room.
    * @param direction The direction of the exit.
    * @param neighbor The room in the given direction.
    */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
    * Return a description of the room’s exits,
    * for example, "Exits: north west".
    * @return A description of the available exits.
    */
    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        if (getRoomItems() != "") {
            returnString += "\nItems in the room:\n";
            returnString += getRoomItems() + "\n";
        }

        return returnString;
    }

    /**
    * @param index The index of an item.
    * @return The item of that index.
    */
    public Item getItem(int index) {
        return getItem(index);

    }

    /**
    * @param itemName The name of the item.
    * @return The item searched for, otherwise returns null.
    */
    public Item getItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                return items.get(i);
            }
        }
        return null;
    }

    /**
    * @param itemName The name of the item.
    * Removes the item that is searched for.
    */
    public void removeItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                items.remove(i);
            }
        }
    }

    /**
    * Adds an item to the list of items.
    */
    public void setItem(Item item) {
        items.add(item);
    }

    /**
    * @return The items of a specific room.
    */
    public String getRoomItems() {
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i).getDescription() + " ";
        }
        return output;
    }

    /**
    * Return a long description of this room, of the form:
    * You are in the kitchen.
    * Exits: north west
    * @return A description of the room, including exits.
    */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
    * @return The description of the room.
    */
    public String getDescription()
    {
        return description;
    }

}
