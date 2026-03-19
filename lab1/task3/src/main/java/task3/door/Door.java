package task3.door;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Door {
    private boolean isClosed;
    private Condition condition;
}
