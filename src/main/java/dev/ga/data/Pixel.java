package dev.ga.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
public class Pixel implements IData<Pixel> {

    private int x;
    private int y;

    @Override
    public Pixel getData() {
        return this;
    }

    @Override
    public void crossOver(IData<?>[] data) {
        for (IData<?> dat : data) {
            if (dat instanceof Pixel pix) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    x = pix.getX();
                }

                if (ThreadLocalRandom.current().nextBoolean()) {
                    y = pix.getY();
                }
            }
        }
    }
}
