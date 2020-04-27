package simple;

import utils.Relation;

import java.util.Map;

public class Container implements Injector {
    private enum ObjectType {CONSTANT, FACTORY, SINGLETON}

    private final Map<String, Relation<ObjectType, Object, Object>> services;

    public Container(Map<String, Relation<ObjectType, Object, Object>> services) {
        this.services = services;
    }

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.CONSTANT, value, null));
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {

    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {

    }

    @Override
    public Object getObject(String name) throws DependencyException {
        return null;
    }
}
