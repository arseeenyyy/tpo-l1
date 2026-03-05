package task3.person;

import task3.door.Condition;
import task3.door.Door;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import task3.monster.Monster;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Person {
    private String name;
    private boolean isAlive;
    private int power;

    public void closeDoor(Door door, List<Monster> monsters) {
        if (!isAlive) {
            System.out.println("Лежит мертвым где-то на корабле.");
            return;
        }
        System.out.print(name + " уперся плечом в дверь кабины, стараясь запереть ее,");
        int monstersPower = monsters.stream().filter(Monster::isAlive).mapToInt(Monster::getPower).sum();
        if (door.getCondition().equals(Condition.GOOD)) {
            System.out.print(" она была хорошо подогнана и ему удалось плотно закрыть дверь.");
            door.setClosed(true);
            if (!monsters.isEmpty()) {
                System.out.print("Так " + name + " удалось спастись от толпы монстров. ");
            }
        } else if (door.getCondition().equals(Condition.BAD)) {
            System.out.print(" но она была плохо подогнана и оставались большие щели. ");
            door.setClosed(true);
            if (!(monsters.isEmpty() || monsters.stream().noneMatch(Monster::isAlive))) {
                if (power > monstersPower) {
                    System.out.println("Маленькие мохнатые ручки просовывались во все щели, пальцы на них были перепачканы " +
                            "чернилами; безумно верещали какие-то тоненькие голоса. ");
                    for (Monster monster : monsters) {
                        monster.setScreaming(true);
                    }
                    door.setClosed(true);
                } else {
                    System.out.print(" но монстры оказались достаточно сильными и сломали дверь. ");
                    door.setCondition(Condition.BROKEN);
                    door.setClosed(false);
                    fight(monsters, monstersPower);
                }
            }
        } else if (door.getCondition().equals(Condition.BROKEN)) {
            System.out.print(" но она была сломана и закрыть дверь просто невозможно. ");
            if (!(monsters.isEmpty() || monsters.stream().noneMatch(Monster::isAlive))) {
                fight(monsters, monstersPower);
            }
        }
    }

    private void fight(List<Monster> monsters, int monstersPower){
        System.out.print("\n" + name + " вступил в бой с монстрами");
        if (power > monstersPower) {
            for (Monster monster : monsters) {
                monster.setAlive(false);
            }
            System.out.print(" и убил всех до единого из мохнатых чудищ. ");
        } else {
            System.out.print(" и был убит мохнатыми чудищами. ");
            isAlive = false;
        }
    }
}
