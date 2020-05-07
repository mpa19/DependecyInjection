package complex;

import common.DependencyException;

public interface Injector {

    <E> void registerConstant(Class<E> name, E value) throws DependencyException, DependencyException;

    <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<E>... parameters) throws DependencyException;

    <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<E>... parameters) throws DependencyException;

    <E> E getObject(Class<E> name) throws DependencyException;
}
