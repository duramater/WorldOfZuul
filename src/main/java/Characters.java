import java.util.HashSet;
import java.util.Set;

public class Characters {
    private String title;
    private Room currentRoom;
    private Set<Objects> possessions = new HashSet<>();

    public Characters(String title, Room currentRoom) {
        this.title = title;
        this.currentRoom = currentRoom;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public boolean addPossession(Objects object) {
        return possessions.add(object);
    }

    public Objects removePossession(Objects object) {
        if (possessions.remove(object)) {
            return object;
        } else {
            return null;
        }
    }

    public Objects getOnePossession(String objectName) {
        Objects returnObject = null;
        for(Objects item : possessions) {
            if(item.getItemName().equalsIgnoreCase(objectName)) {
                returnObject = item;
            }
        }
        return returnObject;
    }

    public String showAllPossessions() {
        String returnString = "Possessions:";
        if(possessions.size() <= 0) {
            returnString += " [None]";
            return returnString;
        }
        for(Objects item : possessions) {
            returnString += " [" + item.getItemName() + "]";
        }
        return returnString;
    }
}
