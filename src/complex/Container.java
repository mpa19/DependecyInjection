package complex;

import common.DependencyException;
import utils.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Container implements Injector {

    private enum ObjectType {CONSTANT, FACTORY, SINGLETON}

    private final Map<Class<?>, Arguments<ObjectType, Object, Object>> registered;

    private final Map<Class<?>, Object> singleton;

    private final ArrayList<Class<?>> creating;


    Container() {
        this.registered = new HashMap<>();
        this.singleton = new HashMap<>();
        this.creating = new ArrayList<>();
    }

    @Override
    public <E> void registerConstant(Class<E> name, E value) throws DependencyException {
        if(registered.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        registered.put(name, new Arguments<>(ObjectType.CONSTANT, value, null));
    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        if(registered.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        registered.put(name, new Arguments<>(ObjectType.FACTORY, creator, parameters));
    }

    @Override
    public <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        if(registered.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        registered.put(name, new Arguments<>(ObjectType.SINGLETON, creator, parameters));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
        Arguments<ObjectType, Object, Object> value = registered.get(name);
        if(value == null)
            throw new DependencyException(new DependencyException("The key was not found in the map."));
        if(creating.contains(name)) throw new DependencyException(new common.DependencyException("Dependency cycle."));

        switch(value.getType()) {
            case FACTORY:
                creating.add(name);
                return ((Factory<E>)value.getFactoryVal()).create(funAux(value, name));

            case CONSTANT:
                return (E) value.getFactoryVal();

            case SINGLETON:
                creating.add(name);
                if(!singleton.containsKey(name)){
                    E val = ((Factory<E>)value.getFactoryVal()).create(funAux(value, name));
                    singleton.put(name, val);
                    return val;
                }
                creating.remove(name);
                return (E) singleton.get(name);
        }
        throw new DependencyException(new common.DependencyException("The ObjectType was neither FACTORY, CONSTANT or SINGLETON."));
    }

    private Object[] funAux(Arguments<ObjectType, Object, Object> value, Class<?> name) throws DependencyException {
        Class<?>[] values = (Class<?>[]) value.getDependencies();

        Object[] params = new Object[values.length];
        int cont = 0;

        for(Class<?> x : values){
            params[cont] = this.getObject(x);
            cont++;
        }

        creating.remove(name);
        return params;
    }
}
