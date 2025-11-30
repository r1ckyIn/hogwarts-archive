import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student account in the Hogwarts Archive system.
 */
public class Student {
    private int studentNumber;
    private String name;
    private List<SpellBook> currentlyRenting;
    private List<SpellBook> rentalHistory;

    /**
     * Creates a new student with the given number and name.
     * @param studentNumber The unique student number
     * @param name The student's name
     */
    public Student(int studentNumber, String name) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.currentlyRenting = new ArrayList<>();
        this.rentalHistory = new ArrayList<>();
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public List<SpellBook> getCurrentlyRenting() {
        return currentlyRenting;
    }

    public List<SpellBook> getRentalHistory() {
        return rentalHistory;
    }

    /**
     * Adds a spellbook to the currently renting list.
     * @param spellbook The spellbook to rent
     */
    public void rentSpellbook(SpellBook spellbook) {
        currentlyRenting.add(spellbook);
    }

    /**
     * Removes a spellbook from currently renting and adds to history.
     * @param spellbook The spellbook to return
     * @return true if successfully returned, false otherwise
     */
    public boolean returnSpellbook(SpellBook spellbook) {
        if (currentlyRenting.remove(spellbook)) {
            rentalHistory.add(spellbook);
            return true;
        }
        return false;
    }

    /**
     * Returns all currently rented spellbooks and adds them to history.
     * @return List of returned spellbooks
     */
    public List<SpellBook> returnAllSpellbooks() {
        List<SpellBook> returned = new ArrayList<>(currentlyRenting);
        rentalHistory.addAll(currentlyRenting);
        currentlyRenting.clear();
        return returned;
    }

    @Override
    public String toString() {
        return studentNumber + ": " + name;
    }
}