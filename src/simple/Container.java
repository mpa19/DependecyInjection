package simple;

import common.DependencyException;
import utils.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Container implements Injector {

    private enum ObjectType {CONSTANT, FACTORY, SINGLETON}

    private final Map<String, Arguments<ObjectType, Object, Object>> registered;

    private final Map<String, Object> singleton;

    private final ArrayList<String> creating;

    Container() {
        this.registered = new HashMap<>();
        this.singleton = new HashMap<>();
        this.creating = new ArrayList<>();
    }

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if(registered.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        registered.put(name, new Arguments<>(ObjectType.CONSTANT, value));
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if(registered.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        registered.put(name, new Arguments<>(ObjectType.FACTORY, creator, parameters));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        if(registered.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        registered.put(name, new Arguments<>(ObjectType.SINGLETON, creator, parameters));
    }

    @Override
    public Object getObject(String name) throws DependencyException {
        Arguments<ObjectType, Object, Object> value = registered.get(name);
        if(value == null)
            throw new DependencyException(new DependencyException("The key was not found in the map."));
        if(creating.contains(name)) throw new DependencyException(new DependencyException("Dependency cycle."));

        switch(value.getType()) {
            case FACTORY:
                creating.add(name);
                return ((Factory)value.getFactoryVal()).create(funAux(value, name));

            case CONSTANT:
                return value.getFactoryVal();

            case SINGLETON:
                creating.add(name);
                if(!singleton.containsKey(name)){
                    Object val = ((Factory)value.getFactoryVal()).create(funAux(value, name));
                    singleton.put(name, val);
                    return val;
                }
                creating.remove(name);
                return singleton.get(name);
        }
        throw new DependencyException(new DependencyException("The ObjectType was neither FACTORY, CONSTANT or SINGLETON."));
    }

    private Object[] funAux(Arguments<ObjectType, Object, Object> value, String name) throws DependencyException {
        String[] values = (String[]) value.getDependencies();

        Object[] params = new Object[values.length];
        int cont = 0;

        for(String x : values){
            params[cont] = this.getObject(x);
            cont++;
        }
        creating.remove(name);
        return params;
    }
}
