package simple;

public interface Factory {
    Object create(Object... parameters) throws DependencyException;
}
