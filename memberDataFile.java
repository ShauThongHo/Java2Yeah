import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * memberDataFile: handles saving and loading the reader profiles
 * using a plain text CSV file (members_data.csv).
 *
 * Each line in the file stores:
 *   MemberID, Name, Phone, Email, RegisterDate
 *
 * We use Scanner (to read) and PrintWriter (to write).
 * No databases are used - only a simple text file.
 */
public class memberDataFile {

    // the file where the reader profiles are stored
    private static final String FILE_NAME = "members_data.csv";

    // ------------------------------------------------------------------
    // loadMembers(): reads every profile from the CSV file and returns
    // them as a Member[] array. If the file does not exist yet, it is
    // created empty (readers register themselves - no default members).
    // ------------------------------------------------------------------
    public static Member[] loadMembers() {
        Member[] members = new Member[0];   // start with an empty array

        try {
            File file = new File(FILE_NAME);

            // no file yet means no members have registered - create it empty
            if (!file.exists()) {
                file.createNewFile();
                return members;
            }

            // STEP 1: first pass - count how many profiles are in the file.
            // We need the count first because arrays have a fixed size.
            int count = 0;
            Scanner counter = new Scanner(file);
            while (counter.hasNextLine()) {
                if (!counter.nextLine().trim().isEmpty()) {
                    count++;
                }
            }
            counter.close();

            // STEP 2: create an array that is exactly the right size
            members = new Member[count];

            // STEP 3: second pass - read each line and turn it into a Member
            Scanner reader = new Scanner(file);
            int index = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();

                // skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // each line is: MemberID,Name,Phone,Email,RegisterDate
                String[] parts = line.split(",");

                // only use the line if it really has 5 parts
                if (parts.length == 5) {
                    members[index] = new Member(
                            parts[0],   // member id
                            parts[1],   // name
                            parts[2],   // phone
                            parts[3],   // email
                            parts[4]);  // register date
                    index++;
                }
            }
            reader.close();

            // some lines may have been skipped (malformed) - trim the array
            // so there are no null holes left at the end
            if (index < members.length) {
                Member[] trimmed = new Member[index];
                System.arraycopy(members, 0, trimmed, 0, index);
                members = trimmed;
            }

        } catch (IOException e) {
            System.err.println("Error reading members file: " + e.getMessage());
        }

        return members;
    }

    // ------------------------------------------------------------------
    // saveMembers(): writes every Member in the Member[] array into the
    // CSV file.
    // ------------------------------------------------------------------
    public static void saveMembers(Member[] members) {
        PrintWriter writer = null;

        try {
            // PrintWriter can create the file if it does not exist
            writer = new PrintWriter(FILE_NAME);

            // write one line per member
            for (int i = 0; i < members.length; i++) {
                Member m = members[i];
                writer.println(m.getMemberId() + ","
                        + m.getName() + ","
                        + m.getPhone() + ","
                        + m.getEmail() + ","
                        + m.getRegisterDate());
            }

        } catch (IOException e) {
            System.err.println("Error saving members file: " + e.getMessage());
        } finally {
            // always close the file, even if an error happened
            if (writer != null) {
                writer.close();
            }
        }
    }

    // ------------------------------------------------------------------
    // nextMemberId(): works out the next member ID (M001, M002, ...).
    // It scans the existing IDs, finds the biggest number, and adds 1,
    // so duplicate IDs can never happen.
    // ------------------------------------------------------------------
    public static String nextMemberId(Member[] members) {
        int maxNumber = 0;

        for (int i = 0; i < members.length; i++) {
            // skip empty slots defensively
            if (members[i] == null) {
                continue;
            }
            String id = members[i].getMemberId();
            // IDs look like "M001" - strip the "M" and read the number
            if (id != null && id.length() > 1) {
                try {
                    int number = Integer.parseInt(id.substring(1));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // ignore IDs that are not "M<number>"
                }
            }
        }

        // pad with leading zeros, e.g. M001, M002, ... M099, M100
        return "M" + String.format("%03d", maxNumber + 1);
    }

    // ------------------------------------------------------------------
    // growMemberArray(): copies a Member[] into a new array that is one
    // bigger (arrays cannot grow by themselves).
    // ------------------------------------------------------------------
    public static Member[] growMemberArray(Member[] oldArray) {
        Member[] newArray = new Member[oldArray.length + 1];
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }
}
