import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String roomName;
    private String description;
    private Objects secretObject = null;
    private boolean isCurrentRoom = false;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Objects> items;
    private HashMap<String, Characters> characters;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String roomName, String description, Objects secretObject)
    {
        this.roomName = roomName;
        this.description = description;
        if(secretObject != null) {
            this.secretObject = new Objects(secretObject.getItemName(), secretObject.getItemWeight());
        }
        exits = new HashMap<>();
        items = new HashMap<>();
        characters = new HashMap<>();
    }

    /**
     * Retrieves whether the this room is the current room.
     * @return The true is this is the current room, false otherwise.
     */
    public boolean getIsCurrentRoom()
    {
        return isCurrentRoom;
    }

    /**
     * Sets this room as the currently active room.
     * @param isCurrentRoom The value of whether this room is now the current room.
     */
    public void setIsCurrentRoom(boolean isCurrentRoom)
    {
        this.isCurrentRoom = isCurrentRoom;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Sets the secret Objects item needed to exit this room.
     * @param secretObject The direction of the exit.
     */
    public void setSecretObject(Objects secretObject)
    {
        this.secretObject = secretObject;
    }

    /**
     * Define an Objects item in this room.
     * @param object  The Objects item which is added to the room.
     */
    public void addObject(Objects object)
    {
        items.put(object.getItemName(), object);
    }

    /**
     * Define aCharacters character in this room.
     * @param character  The Characters character which is added to the room.
     */
    public void admitCharacter(Characters character) {
        characters.put(character.getTitle(), character);
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Return the item that is found in this room with the given name
     * "itemName". If there is no item with that itemName, return null.
     * @param itemName The item's name.
     * @return The Objects item with given itemName.
     */
    public Objects getObject(String itemName)
    {
        return items.get(itemName);
    }

    /**
     * Return the character that is found in this room with the given name
     * "characterNae". If there is no room in that direction, return null.
     * @param characterName The character's name.
     * @return The character with the given characterName.
     */
    public Characters getCharacter(String characterName)
    {
        return characters.get(characterName);
    }

    /**
     * Removes and returns the item that is found in this room with the given name
     * "itemName". If there is no item with that itemName, don't remove anything and return null.
     * @param itemName The item's name.
     * @return The Objects item found with given itemName or null.
     */
    public Objects removeObject(String itemName)
    {
        return items.remove(itemName);
    }

    /**
     * Removes and returns the character that is found in this room with the given name
     * "characterName". If there is no character with that characterName, don't remove anything and return null.
     * @param characterName The character's name.
     * @return The Characters character found with given characterName or null.
     */
    public Characters removeCharacter(String characterName) {
        return characters.remove(characterName);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * @return The summary description of the room
     * (the one that was defined in the constructor).
     */
    public String getRoomSummary()
    {
        String info;
        if (isCurrentRoom) {
            info = "\n\t\t\t\t[[[[[[[[[[You are here!!!]]]]]]]]]\n\n";
        } else {
            info = "";
        }
        return "\t\t\t\t[[[[[[[[[[" + roomName + "]]]]]]]]]]" + "\n" + info + getExitString() + ".\n\n" + getObjectString() + ".\n\n" + getCharacterString() + ".\n\n"
                + (getSecretOutName().equalsIgnoreCase("") ? "Secret Object: \"None\"" : "Secret Object: " + getSecretOutName());
    }

    /**
     * @return The name of the room
     * (the one that was defined in the constructor).
     */
    public String getRoomName()
    {
        return roomName;
    }

    /**
     * @return The name of the Objects item needed to exit this room
     * (the one that was defined in the constructor).
     */
    public String getSecretOutName()
    {
        if(secretObject != null) {
            return secretObject.getItemName();
        } else {
            return "";
        }
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Objects: potion mask boots
     *     Characters: warrior shark knight
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description +".\n" + getExitString() + ".\n\n" + getObjectString() + ".\n\n" + getCharacterString() + ".\n\n"
                + (getSecretOutName().equalsIgnoreCase("") ? "Secret Object: \"None\"" : "Secret Object: " + getSecretOutName());
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:\n\t";
        Set<String> keys = exits.keySet();
        if(exits.size() <= 0) {
            returnString += "[None]";
            return returnString;
        }
        for(String exit : keys) {
            returnString += " [" + exit + "]";
        }
        return returnString;
    }

    /**
     * Return a string describing the room's objects, for example
     * "Objects: potion mask boots".
     * @return Details of the room's objects.
     */
    private String getObjectString()
    {
        String returnString = "Objects:\n\t";
        Set<String> keys = items.keySet();
        if(items.size() <= 0) {
            returnString += "[None]";
            return returnString;
        }
        for(String item : keys) {
            returnString += " [" + item + "]";
        }
        return returnString;
    }

    /**
     * Return a string describing the room's objects, for example
     * "Objects: potion mask boots".
     * @return Details of the room's objects.
     */
    private String getCharacterString()
    {
        String returnString = "Characters:\n\t";
        Set<String> keys = characters.keySet();
        if(characters.size() <= 0) {
            returnString += "[None]";
            return returnString;
        }
        for(String character : keys) {
            returnString += " [" + character + "]";
        }
        return returnString;
    }
}

