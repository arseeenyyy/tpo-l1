package task3;

import task3.door.Condition;
import task3.door.Door;
import task3.monster.Monster;
import org.junit.jupiter.api.Test;
import task3.person.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonCloseDoorWithChaseTest {
    void checkStates(Person person, Door door, List<Monster> monsters,
                     boolean personAlive, boolean doorClosed, Condition doorCondition,
                     List<Boolean> monstersAlive, List<Boolean> monstersScreaming) {
        assertEquals(personAlive, person.isAlive());
        assertEquals(doorClosed, door.isClosed());
        assertEquals(doorCondition, door.getCondition());

        for (int i = 0; i < monsters.size(); i++) {
            assertEquals(monstersAlive.get(i), monsters.get(i).isAlive());
            assertEquals(monstersScreaming.get(i), monsters.get(i).isScreaming());
        }
    }

    @Test
    void deadPerson_nothingChanges() {
        Person person = new Person("John", false, 10);
        Door door = new Door(false, Condition.GOOD);
        Monster monster = new Monster(true, false, 5);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                false, false, Condition.GOOD,
                List.of(true), List.of(false));
    }

    @Test
    void goodDoor_noMonsters() {
        Person person = new Person("John", true, 10);
        Door door = new Door(false, Condition.GOOD);

        person.closeDoor(door, List.of());

        checkStates(person, door, List.of(),
                true, true, Condition.GOOD,
                List.of(), List.of());
    }

    @Test
    void goodDoor_withMonsters() {
        Person person = new Person("John", true, 10);
        Door door = new Door(false, Condition.GOOD);
        Monster monster = new Monster(true, false, 3);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                true, true, Condition.GOOD,
                List.of(true), List.of(false));
    }

    @Test
    void badDoor_noMonsters() {
        Person person = new Person("John", true, 10);
        Door door = new Door(false, Condition.BAD);

        person.closeDoor(door, List.of());

        checkStates(person, door, List.of(),
                true, true, Condition.BAD,
                List.of(), List.of());
    }

    @Test
    void badDoor_personStronger() {
        Person person = new Person("John", true, 20);
        Door door = new Door(false, Condition.BAD);
        Monster monster = new Monster(true, false, 5);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                true, true, Condition.BAD,
                List.of(true), List.of(true));
    }

    @Test
    void badDoor_monstersStronger() {
        Person person = new Person("John", true, 5);
        Door door = new Door(false, Condition.BAD);
        Monster monster = new Monster(true, false, 10);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                false, false, Condition.BROKEN,
                List.of(true), List.of(false));
    }

    @Test
    void brokenDoor_personStronger() {
        Person person = new Person("John", true, 20);
        Door door = new Door(false, Condition.BROKEN);
        Monster monster = new Monster(true, false, 5);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                true, false, Condition.BROKEN,
                List.of(false), List.of(false));
    }

    @Test
    void brokenDoor_monstersStronger() {
        Person person = new Person("John", true, 5);
        Door door = new Door(false, Condition.BROKEN);
        Monster monster = new Monster(true, false, 10);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                false, false, Condition.BROKEN,
                List.of(true), List.of(false));
    }

    @Test
    void badDoor_allMonstersDead() {
        Person person = new Person("John", true, 10);
        Door door = new Door(false, Condition.BAD);
        Monster monster = new Monster(false, false, 5);

        person.closeDoor(door, List.of(monster));

        checkStates(person, door, List.of(monster),
                true, true, Condition.BAD,
                List.of(false), List.of(false));
    }
}