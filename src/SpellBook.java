import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spellbook in the Hogwarts Archive system.
 */
public class SpellBook {
    private int serialNumber;
    private String title;
    private String inventor;
    private String type;
    private Integer currentRenter; // Student number, null if available
    private List<Integer> rentalHistory; // List of student numbers

    /**
     * Creates a new spellbook.
     * @param serialNumber The unique serial number
     * @param title The spellbook title
     * @param inventor The inventor's name
     * @param type The spellbook type
     */
    public SpellBook(int serialNumber, String title, String inventor, String type) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.inventor = inventor;
        this.type = type;
        this.currentRenter = null;
        this.rentalHistory = new ArrayList<>();
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getInventor() {
        return inventor;
    }

    public String getType() {
        return type;
    }

    public Integer getCurrentRenter() {
        return currentRenter;
    }

    public List<Integer> getRentalHistory() {
        return rentalHistory;
    }

    public boolean isAvailable() {
        return currentRenter == null;
    }

    /**
     * Rents this spellbook to a student.
     * @param studentNumber The student number
     * @return true if successful, false if already rented
     */
    public boolean rent(int studentNumber) {
        if (currentRenter != null) {
            return false;
        }
        currentRenter = studentNumber;
        return true;
    }

    /**
     * Returns this spellbook from the current renter.
     * @return true if successful, false if not rented
     */
    public boolean returnBook() {
        if (currentRenter == null) {
            return false;
        }
        rentalHistory.add(currentRenter);
        currentRenter = null;
        return true;
    }

    /**
     * Returns the short string representation.
     * Format: Title (Inventor)
     */
    public String getShortString() {
        return title + " (" + inventor + ")";
    }

    /**
     * Returns the long string representation.
     * Format: SerialNumber: Title (Inventor, Type)\nStatus
     */
    public String getLongString() {
        StringBuilder sb = new StringBuilder();
        sb.append(serialNumber).append(": ").append(title)
          .append(" (").append(inventor).append(", ").append(type).append(")\n");

        if (currentRenter == null) {
            sb.append("Currently available.");
        } else {
            sb.append("Rented by: ").append(currentRenter).append(".");
        }

        return sb.toString();
    }

    /**
     * Checks if this spellbook is a copy of another (same title and inventor).
     * @param other Another spellbook
     * @return true if they are copies
     */
    public boolean isCopyOf(SpellBook other) {
        return this.title.equals(other.title) && this.inventor.equals(other.inventor);
    }

    @Override
    public String toString() {
        return getShortString();
    }
}