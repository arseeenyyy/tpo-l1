package task3.monster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Monster {
    private boolean isAlive;
    private boolean isScreaming;
    private int power;
}
