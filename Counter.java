
// Класс для подсчёта животных. использует интерфейс AutoCloseable для объектов, обслуживаемых конструкцией try-with-resources.
public class Counter implements AutoCloseable {
    private static int count = 0;
    private boolean isClosed = false;

    public void add() {
        if (isClosed) {
            throw new IllegalStateException("Ошибка счётчика");
        }
        count++;
    }

    @Override
    public void close() {
        isClosed = true;
    }

    public static int getCount() {
        return count;
    }
}
