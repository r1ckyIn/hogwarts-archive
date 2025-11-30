import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the collection of students and spellbooks in the Hogwarts Archive.
 */
public class Archive {
    private Map<Integer, Student> students; // studentNumber -> Student
    private Map<Integer, SpellBook> spellbooks; // serialNumber -> SpellBook
    private int nextStudentNumber;

    public Archive() {
        this.students = new HashMap<>();
        this.spellbooks = new HashMap<>();
        this.nextStudentNumber = 100000;
    }

    // ==================== Student Operations ====================

    /**
     * Adds a new student to the system.
     * @param name The student's name
     * @return The new student object
     */
    public Student addStudent(String name) {
        Student student = new Student(nextStudentNumber++, name);
        students.put(student.getStudentNumber(), student);
        return student;
    }

    /**
     * Gets a student by student number.
     * @param studentNumber The student number
     * @return The student, or null if not found
     */
    public Student getStudent(int studentNumber) {
        return students.get(studentNumber);
    }

    /**
     * Checks if any students exist in the system.
     * @return true if students exist
     */
    public boolean hasStudents() {
        return !students.isEmpty();
    }

    // ==================== Spellbook Operations ====================

    /**
     * Adds a spellbook to the system.
     * @param spellbook The spellbook to add
     * @return true if added, false if serial number already exists
     */
    public boolean addSpellbook(SpellBook spellbook) {
        if (spellbooks.containsKey(spellbook.getSerialNumber())) {
            return false;
        }
        spellbooks.put(spellbook.getSerialNumber(), spellbook);
        return true;
    }

    /**
     * Gets a spellbook by serial number.
     * @param serialNumber The serial number
     * @return The spellbook, or null if not found
     */
    public SpellBook getSpellbook(int serialNumber) {
        return spellbooks.get(serialNumber);
    }

    /**
     * Checks if any spellbooks exist in the system.
     * @return true if spellbooks exist
     */
    public boolean hasSpellbooks() {
        return !spellbooks.isEmpty();
    }

    /**
     * Gets all spellbooks sorted by serial number.
     * @return List of spellbooks
     */
    public List<SpellBook> getAllSpellbooks() {
        return spellbooks.values().stream()
                .sorted(Comparator.comparingInt(SpellBook::getSerialNumber))
                .collect(Collectors.toList());
    }

    /**
     * Gets all available spellbooks sorted by serial number.
     * @return List of available spellbooks
     */
    public List<SpellBook> getAvailableSpellbooks() {
        return spellbooks.values().stream()
                .filter(SpellBook::isAvailable)
                .sorted(Comparator.comparingInt(SpellBook::getSerialNumber))
                .collect(Collectors.toList());
    }

    /**
     * Gets all unique types in alphabetical order.
     * @return List of types
     */
    public List<String> getAllTypes() {
        return spellbooks.values().stream()
                .map(SpellBook::getType)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Gets all unique inventors in alphabetical order.
     * @return List of inventors
     */
    public List<String> getAllInventors() {
        return spellbooks.values().stream()
                .map(SpellBook::getInventor)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Gets all spellbooks of a specific type.
     * @param type The type
     * @return List of spellbooks
     */
    public List<SpellBook> getSpellbooksByType(String type) {
        return spellbooks.values().stream()
                .filter(s -> s.getType().equalsIgnoreCase(type))
                .sorted(Comparator.comparingInt(SpellBook::getSerialNumber))
                .collect(Collectors.toList());
    }

    /**
     * Gets all spellbooks by a specific inventor.
     * @param inventor The inventor
     * @return List of spellbooks
     */
    public List<SpellBook> getSpellbooksByInventor(String inventor) {
        return spellbooks.values().stream()
                .filter(s -> s.getInventor().equalsIgnoreCase(inventor))
                .sorted(Comparator.comparingInt(SpellBook::getSerialNumber))
                .collect(Collectors.toList());
    }

    /**
     * Gets the number of copies of each unique spellbook (by title and inventor).
     * @return Map of spellbook description to count
     */
    public Map<String, Integer> getNumberOfCopies() {
        Map<String, Integer> copies = new LinkedHashMap<>();

        // Group by title and inventor
        Map<String, List<SpellBook>> groups = spellbooks.values().stream()
                .collect(Collectors.groupingBy(s -> s.getTitle() + "|" + s.getInventor()));

        // Sort by title alphabetically and create result map
        groups.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().get(0).getTitle()))
                .forEach(e -> {
                    SpellBook first = e.getValue().get(0);
                    copies.put(first.getShortString(), e.getValue().size());
                });

        return copies;
    }

    // ==================== Rental Operations ====================

    /**
     * Rents a spellbook to a student.
     * @param studentNumber The student number
     * @param serialNumber The spellbook serial number
     * @return Result message
     */
    public String rentSpellbook(int studentNumber, int serialNumber) {
        if (!hasStudents()) {
            return "No students in system.";
        }
        Student student = students.get(studentNumber);
        if (student == null) {
            return "No such student in system.";
        }

        if (!hasSpellbooks()) {
            return "No spellbooks in system."; 
        }        

        SpellBook spellbook = spellbooks.get(serialNumber);
        if (spellbook == null) {
            return "No such spellbook in system.";
        }

        if (!spellbook.isAvailable()) {
            return "Spellbook is currently unavailable.";
        }

        spellbook.rent(studentNumber);
        student.rentSpellbook(spellbook);
        return "Success.";
    }

    /**
     * Returns a spellbook from a student.
     * @param studentNumber The student number
     * @param serialNumber The spellbook serial number
     * @return Result message
     */
    public String relinquishSpellbook(int studentNumber, int serialNumber) {
        if (!hasStudents()) {
            return "No students in system.";
        }
        Student student = students.get(studentNumber);
        if (student == null) {
            return "No such student in system.";
        }

        if (!hasSpellbooks()) {
            return "No spellbooks in system."; 
        }

        SpellBook spellbook = spellbooks.get(serialNumber);
        if (spellbook == null || !student.returnSpellbook(spellbook)) {
            return "Unable to return spellbook.";
        }

        spellbook.returnBook();
        return "Success.";
    }

    /**
     * Returns all spellbooks from a student.
     * @param studentNumber The student number
     * @return Result message
     */
    public String relinquishAllSpellbooks(int studentNumber) {
        if (!hasStudents()) {
            return "No students in system.";
        }
        Student student = students.get(studentNumber);
        if (student == null) {
            return "No such student in system.";
        }

        List<SpellBook> returned = student.returnAllSpellbooks();
        for (SpellBook spellbook : returned) {
            spellbook.returnBook();
        }

        return "Success.";
    }

    /**
     * Finds common spellbooks in the rental history of multiple students.
     * @param studentNumbers List of student numbers
     * @return List of common spellbooks in alphabetical order
     */
    public List<SpellBook> findCommonSpellbooks(List<Integer> studentNumbers) {
        if (studentNumbers.isEmpty()) {
            return new ArrayList<>();
        }

        // Get the rental history of the first student
        Student firstStudent = students.get(studentNumbers.get(0));
        if (firstStudent == null) {
            return new ArrayList<>();
        }

        Set<SpellBook> common = new HashSet<>(firstStudent.getRentalHistory());

        // Intersect with other students' histories
        for (int i = 1; i < studentNumbers.size(); i++) {
            Student student = students.get(studentNumbers.get(i));
            if (student == null) {
                return new ArrayList<>();
            }
            common.retainAll(student.getRentalHistory());
        }

        // Sort alphabetically by title
        return common.stream()
                .sorted(Comparator.comparing(SpellBook::getTitle))
                .collect(Collectors.toList());
    }

    // ==================== CSV Operations ====================

    /**
     * Adds a spellbook from a CSV file.
     * @param filename The CSV file path
     * @param serialNumber The serial number to add
     * @return Result message
     */
    public String addSpellbookFromCSV(String filename, int serialNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                // Skip header row if present
                if (firstLine) {
                    firstLine = false;
                    if (line.trim().startsWith("serialNumber") || line.trim().startsWith("serial")) {
                        continue;
                    }
                }

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        int serial = Integer.parseInt(parts[0].trim());
                        if (serial == serialNumber) {
                            String title = parts[1].trim();
                            String inventor = parts[2].trim();
                            String type = parts[3].trim();

                            SpellBook spellbook = new SpellBook(serial, title, inventor, type);
                            if (addSpellbook(spellbook)) {
                                return "Successfully added: " + spellbook.getShortString() + ".";
                            } else {
                                return "Spellbook already exists in system.";
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Skip lines with invalid serial numbers
                        continue;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return "No such file.";
        } catch (IOException e) {
            return "Error reading file.";
        }
        return "No such spellbook in file.";
    }

    /**
     * Adds a collection of spellbooks from a CSV file.
     * @param filename The CSV file path
     * @return Result message
     */
    public String addCollectionFromCSV(String filename) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                // Skip header row if present
                if (firstLine) {
                    firstLine = false;
                    if (line.trim().startsWith("serialNumber") || line.trim().startsWith("serial")) {
                        continue;
                    }
                }

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        int serial = Integer.parseInt(parts[0].trim());
                        String title = parts[1].trim();
                        String inventor = parts[2].trim();
                        String type = parts[3].trim();

                        SpellBook spellbook = new SpellBook(serial, title, inventor, type);
                        if (addSpellbook(spellbook)) {
                            count++;
                        }
                    } catch (NumberFormatException e) {
                        // Skip lines with invalid serial numbers
                        continue;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return "No such collection.";
        } catch (IOException e) {
            return "Error reading file.";
        }

        if (count == 0) {
            return "No spellbooks have been added to the system.";  // 添加这个检查 / Add this check
        }
        return count + " spellbooks successfully added.";
    }

    /**
     * Saves all spellbooks to a CSV file.
     * @param filename The CSV file path
     * @return Result message
     */
    public String saveCollectionToCSV(String filename) {
        if (!hasSpellbooks()) {
            return "No spellbooks in system.";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("serialNumber,title,inventor,type");
            List<SpellBook> sorted = getAllSpellbooks();
            for (SpellBook s : sorted) {
                writer.println(s.getSerialNumber() + "," + s.getTitle() + "," +
                             s.getInventor() + "," + s.getType());
            }
            return "Success.";
        } catch (IOException e) {
            return "Error writing file.";
        }
    }

    /**
     * Main method that delegates to HogwartsArchive.
     * This exists for compatibility with Ed platform testing.
     */
    public static void main(String[] args) {
        HogwartsArchive.main(args);
    }
}