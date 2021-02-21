package interfaces;

import java.util.List;

public interface IBaseOperation<E> {

    int create();

    void update();

    int delete();

    E read();

    List<E> readList();

}
