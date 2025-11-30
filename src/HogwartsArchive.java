import java.util.*;

/**
 * Main class for the Hogwarts Archive application.
 */
public class HogwartsArchive {
    private Archive archive;
    private Scanner scanner;

    public HogwartsArchive() {
        this.archive = new Archive();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        HogwartsArchive app = new HogwartsArchive();
        app.run();
    }

    /**
     * Main loop that processes commands.
     */
    public void run() {
        boolean running = true;

        while (running) {
            System.out.print("user: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            running = processCommand(input);

            // Add blank line after each command output
            if (running) {
                System.out.println();
            }
        }

        scanner.close();
    }

    /**
     * Processes a command and returns whether to continue running.
     * @param input The command string
     * @return false if EXIT command, true otherwise
     */
    private boolean processCommand(String input) {
        String[] tokens = input.split("\\s+", 2);
        String command = tokens[0].toUpperCase();

        // Handle EXIT
        if (command.equals("EXIT")) {
            System.out.println("Ending Archive process.");
            return false;
        }

        // Handle COMMANDS
        if (command.equals("COMMANDS")) {
            printCommands();
            return true;
        }

        // Handle other commands
        String args = tokens.length > 1 ? tokens[1] : "";
        handleCommand(command, args);

        return true;
    }

    /**
     * Handles all commands except EXIT and COMMANDS.
     */
    private void handleCommand(String command, String args) {
        switch (command) {
            case "LIST":
                handleListCommand(args);
                break;
            case "NUMBER":
                handleNumberCopies(args);
                break;
            case "TYPE":
                handleTypeCommand(args);
                break;
            case "INVENTOR":
                handleInventorCommand(args);
                break;
            case "SPELLBOOK":
                handleSpellbookCommand(args);
                break;
            case "STUDENT":
                handleStudentCommand(args);
                break;
            case "RENT":
                handleRentCommand(args);
                break;
            case "RELINQUISH":
                handleRelinquishCommand(args);
                break;
            case "ADD":
                handleAddCommand(args);
                break;
            case "SAVE":
                handleSaveCommand(args);
                break;
            case "COMMON":
                handleCommonCommand(args);
                break;
            default:
                // Ignore invalid commands
                break;
        }
    }

    // ==================== LIST Commands ====================

    private void handleListCommand(String args) {
        String[] parts = args.toUpperCase().split("\\s+");

        if (parts.length == 0 || parts[0].isEmpty()) {
            return;
        }

        boolean isLong = parts.length > 1 && parts[1].equals("LONG");

        switch (parts[0]) {
            case "ALL":
                listAll(isLong);
                break;
            case "AVAILABLE":
                listAvailable(isLong);
                break;
            case "TYPES":
                listTypes();
                break;
            case "INVENTORS":
                listInventors();
                break;
        }
    }

    private void listAll(boolean isLong) {
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<SpellBook> spellbooks = archive.getAllSpellbooks();
        for (int i = 0; i < spellbooks.size(); i++) {
            SpellBook s = spellbooks.get(i);
            System.out.println(isLong ? s.getLongString() : s.getShortString());
            // Add blank line after each long format entry, except the last one
            if (isLong && i < spellbooks.size() - 1) {
                System.out.println();
            }
        }
    }

    private void listAvailable(boolean isLong) {
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<SpellBook> available = archive.getAvailableSpellbooks();

        if (available.isEmpty()) {
            System.out.println("No spellbooks available.");
            return;
        }

        for (int i = 0; i < available.size(); i++) {
            SpellBook s = available.get(i);
            System.out.println(isLong ? s.getLongString() : s.getShortString());
            // Add blank line after each long format entry, except the last one
            if (isLong && i < available.size() - 1) {
                System.out.println();
            }
        }
    }

    private void listTypes() {
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<String> types = archive.getAllTypes();
        for (String type : types) {
            System.out.println(type);
        }
    }

    private void listInventors() {
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<String> inventors = archive.getAllInventors();
        for (String inventor : inventors) {
            System.out.println(inventor);
        }
    }

    // ==================== NUMBER COPIES Command ====================

    private void handleNumberCopies(String args) {
        if (!args.toUpperCase().equals("COPIES")) {
            return;
        }

        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        Map<String, Integer> copies = archive.getNumberOfCopies();
        for (Map.Entry<String, Integer> entry : copies.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // ==================== TYPE and INVENTOR Commands ====================

    private void handleTypeCommand(String type) {
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<SpellBook> spellbooks = archive.getSpellbooksByType(type);
        if (spellbooks.isEmpty()) {
            System.out.println("No spellbooks with type " + type + ".");
            return;
        }

        for (SpellBook s : spellbooks) {
            System.out.println(s.getShortString());
        }
    }

    private void handleInventorCommand(String inventor) {
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<SpellBook> spellbooks = archive.getSpellbooksByInventor(inventor);
        if (spellbooks.isEmpty()) {
            System.out.println("No spellbooks by " + inventor + ".");
            return;
        }

        for (SpellBook s : spellbooks) {
            System.out.println(s.getShortString());
        }
    }

    // ==================== SPELLBOOK Commands ====================

    private void handleSpellbookCommand(String args) {
        String[] parts = args.split("\\s+");

        if (parts.length == 0 || parts[0].isEmpty()) {
            return;
        }

        if (parts[0].toUpperCase().equals("HISTORY")) {
            if (parts.length > 1) {
                spellbookHistory(parts[1]);
            }
        } else {
            // SPELLBOOK <serialNumber> [LONG]
            int serialNumber;
            try {
                serialNumber = Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                return;
            }

            boolean isLong = parts.length > 1 && parts[1].toUpperCase().equals("LONG");
            spellbookInfo(serialNumber, isLong);
        }
    }

    private void spellbookInfo(int serialNumber, boolean isLong) {
        SpellBook spellbook = archive.getSpellbook(serialNumber);
        if (spellbook == null) {
            if (!archive.hasSpellbooks()) {
                System.out.println("No spellbooks in system.");
            } else {
                System.out.println("No such spellbook in system.");
            }
            return;
        }
        System.out.println(isLong ? spellbook.getLongString() : spellbook.getShortString());
    }

    private void spellbookHistory(String serialNumberStr) {
        int serialNumber;
        try {
            serialNumber = Integer.parseInt(serialNumberStr);
        } catch (NumberFormatException e) {
            return;
        }

        if (!archive.hasSpellbooks()) {
            System.out.println("No such spellbook in system.");
            return;
        }

        SpellBook spellbook = archive.getSpellbook(serialNumber);
        if (spellbook == null) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<Integer> history = spellbook.getRentalHistory();
        if (history.isEmpty()) {
            System.out.println("No rental history.");
            return;
        }

        for (Integer studentNumber : history) {
            System.out.println(studentNumber);
        }
    }

    // ==================== STUDENT Commands ====================

    private void handleStudentCommand(String args) {
        String[] parts = args.split("\\s+");

        if (parts.length == 0 || parts[0].isEmpty()) {
            return;
        }

        // Check if it's STUDENT SPELLBOOKS <number> or STUDENT HISTORY <number>
        if (parts[0].toUpperCase().equals("SPELLBOOKS") && parts.length > 1) {
            int studentNumber;
            try {
                studentNumber = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return;
            }
            studentSpellbooks(studentNumber);
        } else if (parts[0].toUpperCase().equals("HISTORY") && parts.length > 1) {
            int studentNumber;
            try {
                studentNumber = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return;
            }
            studentHistory(studentNumber);
        } else {
            // STUDENT <number>
            int studentNumber;
            try {
                studentNumber = Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                return;
            }
            studentInfo(studentNumber);
        }
    }

    private void studentInfo(int studentNumber) {
        if (!archive.hasStudents()) {
            System.out.println("No students in system.");
            return;
        }

        Student student = archive.getStudent(studentNumber);
        if (student == null) {
            System.out.println("No such student in system.");
            return;
        }

        System.out.println(student.toString());
    }

    private void studentSpellbooks(int studentNumber) {
        if (!archive.hasStudents()) {
            System.out.println("No students in system.");
            return;
        }

        Student student = archive.getStudent(studentNumber);

        if (student == null) {
            System.out.println("No such student in system.");
            return;
        }

        List<SpellBook> renting = student.getCurrentlyRenting();
        if (renting.isEmpty()) {
            System.out.println("Student not currently renting.");
            return;
        }

        for (SpellBook s : renting) {
            System.out.println(s.getShortString());
        }
    }

    private void studentHistory(int studentNumber) {

        if (!archive.hasStudents()) {
            System.out.println("No students in system.");
            return;
        }
        
        Student student = archive.getStudent(studentNumber);

        if (student == null) {
            System.out.println("No such student in system.");
            return;
        }

        List<SpellBook> history = student.getRentalHistory();
        if (history.isEmpty()) {
            System.out.println("No rental history for student.");
            return;
        }

        for (SpellBook s : history) {
            System.out.println(s.getShortString());
        }
    }

    // ==================== RENT and RELINQUISH Commands ====================

    private void handleRentCommand(String args) {
        String[] parts = args.split("\\s+");
        if (parts.length < 2) {
            return;
        }

        int studentNumber, serialNumber;
        try {
            studentNumber = Integer.parseInt(parts[0]);
            serialNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return;
        }

        String result = archive.rentSpellbook(studentNumber, serialNumber);
        System.out.println(result);
    }

    private void handleRelinquishCommand(String args) {
        String[] parts = args.split("\\s+");

        if (parts.length == 0 || parts[0].isEmpty()) {
            return;
        }

        if (parts.length > 1 && parts[0].toUpperCase().equals("ALL")) {
            // RELINQUISH ALL <studentNumber>
            int studentNumber;
            try {
                studentNumber = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return;
            }

            String result = archive.relinquishAllSpellbooks(studentNumber);
            System.out.println(result);
        } else if (parts.length >= 2) {
            // RELINQUISH <studentNumber> <serialNumber>
            int studentNumber, serialNumber;
            try {
                studentNumber = Integer.parseInt(parts[0]);
                serialNumber = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return;
            }

            String result = archive.relinquishSpellbook(studentNumber, serialNumber);
            System.out.println(result);
        }
    }

    // ==================== ADD Commands ====================

    private void handleAddCommand(String args) {
        String[] parts = args.split("\\s+", 2);

        if (parts.length == 0 || parts[0].isEmpty()) {
            return;
        }

        String subCommand = parts[0].toUpperCase();
        String subArgs = parts.length > 1 ? parts[1] : "";

        switch (subCommand) {
            case "STUDENT":
                addStudent(subArgs);
                break;
            case "SPELLBOOK":
                addSpellbook(subArgs);
                break;
            case "COLLECTION":
                addCollection(subArgs);
                break;
        }
    }

    private void addStudent(String name) {
        if (name.isEmpty()) {
            return;
        }

        archive.addStudent(name);
        System.out.println("Success.");
    }

    private void addSpellbook(String args) {
        String[] parts = args.split("\\s+");
        if (parts.length < 2) {
            return;
        }

        String filename = parts[0];
        int serialNumber;
        try {
            serialNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return;
        }

        String result = archive.addSpellbookFromCSV(filename, serialNumber);
        System.out.println(result);
    }

    private void addCollection(String filename) {
        if (filename.isEmpty()) {
            return;
        }

        String result = archive.addCollectionFromCSV(filename);
        System.out.println(result);
    }

    // ==================== SAVE Command ====================

    private void handleSaveCommand(String args) {
        String[] parts = args.split("\\s+", 2);

        if (parts.length > 0 && parts[0].toUpperCase().equals("COLLECTION")) {
            String filename = parts.length > 1 ? parts[1] : "";
            if (!filename.isEmpty()) {
                String result = archive.saveCollectionToCSV(filename);
                System.out.println(result);
            }
        }
    }

    // ==================== COMMON Command ====================

    private void handleCommonCommand(String args) {
        String[] parts = args.split("\\s+");

        if (parts.length < 2) {
            return;
        }

        // Parse student numbers
        List<Integer> studentNumbers = new ArrayList<>();
        Set<Integer> uniqueNumbers = new HashSet<>();

        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (uniqueNumbers.contains(num)) {
                    System.out.println("Duplicate students provided.");
                    return;
                }
                uniqueNumbers.add(num);
                studentNumbers.add(num);
            } catch (NumberFormatException e) {
                System.out.println("No such student in system.");
                return;
            }
        }

        if (!archive.hasStudents()) {
            System.out.println("No students in system.");
            return;
        }

        // Check if all students exist
        for (int num : studentNumbers) {
            if (archive.getStudent(num) == null) {
                System.out.println("No such student in system.");
                return;
            }
        }

        // Check if spellbooks exist
        if (!archive.hasSpellbooks()) {
            System.out.println("No spellbooks in system.");
            return;
        }

        List<SpellBook> common = archive.findCommonSpellbooks(studentNumbers);

        if (common.isEmpty()) {
            System.out.println("No common spellbooks.");
            return;
        }

        for (SpellBook s : common) {
            System.out.println(s.getShortString());
        }
    }

    // ==================== COMMANDS Help ====================

    private void printCommands() {
        System.out.println("EXIT ends the archive process");
        System.out.println("COMMANDS outputs this help string");
        System.out.println();
        System.out.println("LIST ALL [LONG] outputs either the short or long string for all spellbooks");
        System.out.println("LIST AVAILABLE [LONG] outputs either the short or long string for all available spellbooks");
        System.out.println("NUMBER COPIES outputs the number of copies of each spellbook");
        System.out.println("LIST TYPES outputs the name of every type in the system");
        System.out.println("LIST INVENTORS outputs the name of every inventor in the system");
        System.out.println();
        System.out.println("TYPE <type> outputs the short string of every spellbook with the specified type");
        System.out.println("INVENTOR <inventor> outputs the short string of every spellbook by the specified inventor");
        System.out.println();
        System.out.println("SPELLBOOK <serialNumber> [LONG] outputs either the short or long string for the specified spellbook");
        System.out.println("SPELLBOOK HISTORY <serialNumber> outputs the rental history of the specified spellbook");
        System.out.println();
        System.out.println("STUDENT <studentNumber> outputs the information of the specified student");
        System.out.println("STUDENT SPELLBOOKS <studentNumber> outputs the spellbooks currently rented by the specified student");
        System.out.println("STUDENT HISTORY <studentNumber> outputs the rental history of the specified student");
        System.out.println();
        System.out.println("RENT <studentNumber> <serialNumber> loans out the specified spellbook to the given student");
        System.out.println("RELINQUISH <studentNumber> <serialNumber> returns the specified spellbook from the student");
        System.out.println("RELINQUISH ALL <studentNumber> returns all spellbooks rented by the specified student");
        System.out.println();
        System.out.println("ADD STUDENT <name> adds a student to the system");
        System.out.println("ADD SPELLBOOK <filename> <serialNumber> adds a spellbook to the system");
        System.out.println();
        System.out.println("ADD COLLECTION <filename> adds a collection of spellbooks to the system");
        System.out.println("SAVE COLLECTION <filename> saves the system to a csv file");
        System.out.println();
        System.out.println("COMMON <studentNumber1> <studentNumber2> ... outputs the common spellbooks in students' history");
    }
}