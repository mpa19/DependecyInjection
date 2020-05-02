package complex;

public interface Factory<E> {
    E create(Object... parameters) throws DependencyException;
}
