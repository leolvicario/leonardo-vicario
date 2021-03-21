package demo.automation.challenge.utilities;

import java.util.function.Supplier;

public class Lazy {

    private Lazy() {}
    public static <Z> Supplier<Z> lazily(Supplier<Z> supplier) {
        return new Supplier<Z>() {
            Z value;

            @Override
            public Z get() {
                if (value == null) {
                    value = supplier.get();
                }
                return value;
            }
        };
    }
}
