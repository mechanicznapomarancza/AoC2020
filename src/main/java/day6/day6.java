package day6;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

public class day6 {

    public static final String path = "src/main/resources/input_day6";
    public static final List<String> input = getInput();

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part2() {
        System.out.println("------ PART 2 ------");
        List<Group> groupAnswers = getGroups();
        int sum = 0;
        for (Group group : groupAnswers) {
            commonAnswers(group);
            sum += group.getCount();
        }
        System.out.println("Answer: " + sum);
    }

    private static void part1() {
        System.out.println("------ PART 1 ------");
        List<Group> groupAnswers = getGroups();
        int sum = 0;
        for (Group group : groupAnswers) {
            distinctAnswers(group);
            sum += group.getCount();
        }
        System.out.println("Answer: " + sum);
    }

    private static void commonAnswers(Group group) {
        List<Character> common = new ArrayList<>();
        for (Character answer : group.getAnswers()) {
            if (group.getAnswers().stream().filter(Predicate.isEqual(answer)).count() == group.getNumberOfPeople()) {
                common.add(answer);
            }
        }
        group.setCommonAnswers(new HashSet<>(common));
        group.setCount(group.getCommonAnswers().size());
    }

    private static void distinctAnswers(Group group) {
        group.setDistinctAnswers(new HashSet<>(group.getAnswers()));
        group.setCount(group.getDistinctAnswers().size());
    }

    private static List<Group> getGroups() {
        List<Group> allGroups = new ArrayList<>();
        List<Character> result = new ArrayList<>();
        int numberOfPeople = 0;
        for (int i = 0; i < input.size(); i++) {
            if (!input.get(i).equals("")) {
                numberOfPeople += 1;
                for (char c : input.get(i).toCharArray()) {
                    result.add(c);
                }
            } else {
                allGroups.add(createGroup(result, numberOfPeople));
                result.clear();
                numberOfPeople = 0;
            }
            if (i == input.size() - 1) {
                allGroups.add(createGroup(result, numberOfPeople));
            }
        }
        return allGroups;
    }

    private static Group createGroup(List<Character> answers, int numberOfPeople) {
        Group newGroup = new Group();
        newGroup.getAnswers().addAll(answers);
        newGroup.setNumberOfPeople(numberOfPeople);
        return newGroup;
    }

    public static List<String> getInput() {
        Path filePath = Paths.get(path);
        List<String> input = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(filePath).useDelimiter("\n");
            while (scanner.hasNext()) {
                input.add(scanner.next());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.of(input).orElse(Collections.emptyList());
    }
}